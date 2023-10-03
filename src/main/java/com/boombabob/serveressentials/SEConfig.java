package com.boombabob.serveressentials;


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
}
