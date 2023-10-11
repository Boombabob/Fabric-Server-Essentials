# Fabric-Server-Essentials
**Essential features for any Minecraft server, including command scheduling and server restarting!**
Listed below are all the features of the mod currently


## Command scheduling
![in game screenshot of command scheduling](https://cdn.modrinth.com/data/v5D5j0kg/images/fbbd2862e26b343e956bd7079656df4a5255a49c.png)

**/scheduleCommand list** to list scheduled commands

**/scheduleCommand remove <hour> <minute>** to remove scheduled commands

**/scheduleCommand add <hour> <minute> <command>** to add scheduled commands

The schedule command allows you to schedule certain commands to happen at any time in the day, allowing you to schedule things like restarting the server overnight. Please note that the remove command does not apply until after restarting the server, as it is not possible to cancel actions after they have been scheduled. In the future, I could add a reset command to reschedule everything, which would prevent you from having to restart. The scheduled commands are also saved in the config, so the schedule can be edited there. **Any command works with this, even ones from other mods.**

## Server restarting
**/restart**

**/restart on shutdown true/false**

This restarts the Minecraft server, but there is also an option to make the server restart whenever it stops (apart from using the /stop command), preventing the server being down unintendedly. This can also be edited in the config

## Info command
**/info**

This command allows players to see information about the server. To change the information displayed, edit it in the config and use \n for new lines.


## Coords command
**/coords or /c** to send to everyone

**/coords <recipients> or /c <recipients>** to send to specific players

players on the server can type /c or /coords to send their coords to everyone on the server, or they can instead specify the players they want to send their coords to with /coords <Recipients>

## Broadcast command
![in game screenshot of broadcast command](https://cdn.modrinth.com/data/v5D5j0kg/images/dfc1a2d45af544367aa09e1f497c59ef87b922c8.png)

**/broadcast <color> <message>**

This command allows you to broadcast messages to everyone online on the server, which is useful for warning them about things such as server restarts. When paired with the schedule command, it is quite useful.


## Ping command
**/ping**

This command allows players to see their ping with the minecraft server.

---
I am not planning to add too many features, but that is mostly because there is nothing else that I feel like is needed. If you find any bugs or want any new features, feel free to open an issue on the github page and I will work on it when I can.
