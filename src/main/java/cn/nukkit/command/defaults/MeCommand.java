package cn.nukkit.command.defaults;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.locale.TranslationContainer;
import cn.nukkit.player.Player;
import cn.nukkit.utils.TextFormat;

/**
 * Created on 2015/11/12 by xtypr.
 * Package cn.nukkit.command.defaults in project Nukkit .
 */
public class MeCommand extends VanillaCommand {

    public MeCommand(String name) {
        super(name, "commands.me.description", "commands.me.usage");
        this.setPermission("nukkit.command.me");
        this.commandParameters.clear();
        this.commandParameters.add(new CommandParameter[]{
                new CommandParameter("action ...", CommandParamType.RAWTEXT, false)
        });
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(new TranslationContainer("commands.generic.usage", this.usageMessage));

            return false;
        }

        String name;
        if (sender instanceof Player) {
            name = ((Player) sender).getDisplayName();
        } else {
            name = sender.getName();
        }

        String msg = "";
        for (String arg : args) {
            msg += arg + " ";
        }

        if (msg.length() > 0) {
            msg = msg.substring(0, msg.length() - 1);
        }

        sender.getServer().broadcastMessage(new TranslationContainer("chat.type.emote", name, TextFormat.WHITE + msg));

        return true;
    }
}
