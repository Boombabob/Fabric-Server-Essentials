package com.boombabob.fabricserveressentials;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class SEConfig {
    public static String path = "seconfig";
    // Funny business to get YACL working
    public static ConfigClassHandler<SEConfig> HANDLER = ConfigClassHandler.createBuilder(SEConfig.class)
            .id(Identifier.of(Main.MODID, path))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve(path.concat(".json5")))
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry(comment = """
        If you want to add new tasks through this config, use 24 hour time and only have 1 command per time.
        If you are not sure what that looks like, try adding a task via the /schedule command and copying that format.
        """)
    public HashMap<String, String> scheduledTasks = new HashMap<>();

    @SerialEntry(comment = "Information about the server, which users can see with the /info command")
    public String serverInfo = "";

    @SerialEntry(comment = """
        Argument for restarting the server, which can be modified for your needs (such as increasing ram)
        %s is replaced with the server jar directory when run, but you can put in this path manually.
        java can be replaced with a specific java installation, but this is usually unnecessary.
        """)
    public String restartArgument = "java -jar %s nogui";

    @SerialEntry(comment = """
        Set to true to restart the server automatically when shut down or after (some) crashes,
        But /stop still stops the server, so use /restart instead if you want to restart the server
        """)
    public boolean shouldRestartAutomatically = false;

    @SerialEntry(comment = "Following values are whether certain commands are enabled or not, by default all are.")
    public boolean broadcastCommandEnabled = true;
    @SerialEntry
    public boolean coordsCommandEnabled = true;
    @SerialEntry
    public boolean flexItemCommandEnabled = true;
    @SerialEntry
    public boolean infoCommandEnabled = true;
    @SerialEntry
    public boolean pingCommandEnabled = true;
    @SerialEntry
    public boolean restartCommandEnabled = true;
    @SerialEntry
    public boolean scheduleCommandEnabled = true;

}
