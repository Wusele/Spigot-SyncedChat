package me.wusel.javacord.command;

import me.wusel.javacord.SyncedChat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.javacord.api.entity.message.MessageBuilder;

public class AnnouncementCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("syncedchat.announcement"))
            return true;

        // /announcement
        if (args.length >= 1) {
            String s = new String();
            for (int i = 0; i<args.length; i++) {
                s += args[i] + " ";
            }
            Bukkit.broadcastMessage("§c[§lANNOUNCEMENT§r§c] §r§9§l" + s);
            new MessageBuilder()
                    .append("[ANNOUNCEMENT] ")
                    .append(s)
                    .send(SyncedChat.getTextChannel());
        }
        return false;
    }
}
