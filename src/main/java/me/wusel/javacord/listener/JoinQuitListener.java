package me.wusel.javacord.listener;

import me.wusel.javacord.SyncedChat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.MessageDecoration;

public class JoinQuitListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (SyncedChat.getDiscordBot() == null) return;
        DiscordApi api = SyncedChat.getDiscordBot().getApi();
        TextChannel channel = SyncedChat.getTextChannel();



        new Thread(new Runnable() {
            @Override
            public void run() {
                new MessageBuilder()
                        .append("[Minecraft] ")
                        .append(p.getName() + " has joined the Server!")
                        .send(channel);
            }
        }).start();

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (SyncedChat.getDiscordBot() == null) return;
        DiscordApi api = SyncedChat.getDiscordBot().getApi();
        TextChannel channel = SyncedChat.getTextChannel();

        new Thread(new Runnable() {
            @Override
            public void run() {
                new MessageBuilder()
                        .append("[Minecraft] ")
                        .append(p.getName() + " has left the Server!")
                        .send(channel);
            }
        }).start();
    }
}
