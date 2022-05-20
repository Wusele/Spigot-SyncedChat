package me.wusel.javacord.listener;

import me.wusel.javacord.SyncedChat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.MessageDecoration;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import javax.annotation.Priority;
import java.awt.*;

public class AsyncPlayerChatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (SyncedChat.getDiscordBot() == null) return;
        DiscordApi api = SyncedChat.getDiscordBot().getApi();
        TextChannel channel = SyncedChat.getTextChannel();

        if (channel == null) {
            return;
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                new MessageBuilder()
                        .append("[Minecraft] ")
                        .append(p.getName(), MessageDecoration.BOLD)
                        .append(": ")
                        .append(e.getMessage()).send(channel);
            }
        }).start();

    }
}
