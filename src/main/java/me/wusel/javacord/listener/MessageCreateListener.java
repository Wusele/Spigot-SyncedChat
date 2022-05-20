package me.wusel.javacord.listener;

import me.wusel.javacord.SyncedChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.javacord.api.entity.message.embed.Embed;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.function.Consumer;

public class MessageCreateListener implements org.javacord.api.listener.message.MessageCreateListener {

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        String author = e.getMessageAuthor().getDiscriminatedName();
        String content = e.getMessageContent();

        if (SyncedChat.getTextChannel() == null) SyncedChat.setTextChannel(e.getChannel());

        if (content.startsWith("[Minecraft]") && e.getMessageAuthor().isYourself()) return;
        if (content.startsWith("[ANNOUNCEMENT]") && e.getMessageAuthor().isYourself()) return;

        if (e.getMessage().getEmbeds().size() > 0) {
        }

        Bukkit.broadcastMessage(ChatColor.BLACK + "[" + ChatColor.BLUE + "§lDISCORD§r" + ChatColor.BLACK + "] §r§c" + author + "§r§7: " + content);
    }
}
