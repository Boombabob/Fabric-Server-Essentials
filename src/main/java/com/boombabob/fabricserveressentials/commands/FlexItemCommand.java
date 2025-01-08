package com.boombabob.fabricserveressentials.commands;

import com.boombabob.fabricserveressentials.Main;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Collection;
import java.util.List;

import static net.minecraft.command.argument.EntityArgumentType.getPlayers;
import static net.minecraft.command.argument.EntityArgumentType.players;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class FlexItemCommand implements ISECommand{
    public void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        if (Main.CONFIG.flexItemCommandEnabled) {
            final LiteralCommandNode<ServerCommandSource> flexItemCommandNode = dispatcher.register(literal("flexItem"));
            dispatcher.register(literal("flexItem")
                .requires(Permissions.require("%s.flexItem".formatted(Main.MODID)))
                .requires(ServerCommandSource::isExecutedByPlayer)
                .executes(context -> flexItem(
                    context.getSource().getPlayer(),
                    context.getSource().getServer().getPlayerManager().getPlayerList()
                ))
                .then(argument("recipients", players())
                    .executes(context -> flexItem(
                        context.getSource().getPlayer(),
                        getPlayers(context, "recipients")
                    ))
                    .redirect(flexItemCommandNode))
            );
        }
    }

    public static int flexItem(PlayerEntity sourcePlayer, Collection<ServerPlayerEntity> recipientPlayers) {
        ItemStack itemStack = sourcePlayer.getEquippedStack(EquipmentSlot.MAINHAND);
        if (!itemStack.isOf(Items.AIR)) {
            List<Text> itemStackToolip = itemStack.getTooltip(TooltipContext.create(sourcePlayer.getWorld()), sourcePlayer, TooltipType.ADVANCED);
            MutableText itemStackName;
            if (itemStack.getCount() == 1) {
                itemStackName = itemStackToolip.get(0).copy();
            } else {
                itemStackName = Text.literal(itemStack.getCount() + " ")
                        .append(itemStackToolip.get(0))
                            .setStyle(itemStackToolip.get(0).getStyle());
            }

            // Make item info show up when hovered
            itemStackName.setStyle(itemStackName.getStyle().withHoverEvent(
                    new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackContent(itemStack)))
            );
            Text message = sourcePlayer.getDisplayName().copy().append(" has ").append(itemStackName);

            for (ServerPlayerEntity player : recipientPlayers) {
                player.sendMessage(message);
            }
        } else {
            sourcePlayer.sendMessage(Text.literal("You are not holding any items in your main hand!").formatted(Formatting.RED), false);
        }
        return Command.SINGLE_SUCCESS;
    }
}
