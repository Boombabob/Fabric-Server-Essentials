package com.boombabob.fabricserveressentials;


import draylar.omegaconfig.api.Comment;
import draylar.omegaconfig.api.Config;

import java.util.HashMap;

public class SEConfig implements Config {
    @Override
    public String getName() {
        return "se_config";
    }

    @Override
    public String getModid() {
        return "server-essentials";
    }

    @Comment(value = "If you want to add new tasks through this config, use 24 hour time and only have 1 command per time")
    public HashMap<String, String> scheduledTasks = new HashMap<>();

    @Comment(value = "Information about the server, which users can see with the /info command")
    public String serverInfo = "";

    @Comment(value = """
        Argument for restarting the server, which can be modified for your needs (such as increasing ram)
        %s is replaced with the server jar directory when run, but you can put in this path manually.
        java can be replaced with a specific java installation, but this is usually unnecessary.
        """)
    public String restartArgument = "java -jar %s nogui";

    @Comment(value = """
        Set to true to restart the server automatically when shut down or after (some) crashes,
        But /stop still stops the server, so use /restart instead if you want to restart the server
        """)
    public boolean shouldRestartAutomatically = false;

    @Comment(value = """
        Following values are whether certain commands are enabled or not, by default all are.
        """)
    public boolean broadcastCommandEnabled = true;
    public boolean coordsCommandEnabled = true;
    public boolean flexItemCommandEnabled = true;
    public boolean infoCommandEnabled = true;
    public boolean pingCommandEnabled = true;
    public boolean restartCommandEnabled = true;
    public boolean scheduleCommandEnabled = true;

}
