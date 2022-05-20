package me.wusel.javacord;

import me.wusel.javacord.command.AnnouncementCommand;
import me.wusel.javacord.listener.AsyncPlayerChatListener;
import me.wusel.javacord.listener.JoinQuitListener;
import me.wusel.javacord.listener.MessageCreateListener;
import me.wusel.wuselutils.discord.DiscordBot;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.UserStatus;

import java.util.concurrent.ExecutionException;

public class SyncedChat extends JavaPlugin {

    private static DiscordBot discordBot;
    private static TextChannel channel;
    private static Server server;

    private String serverId, channelId;

    private static String TOKEN;
    private boolean registered;

    @Override
    public void onEnable() {
        registered = false;

        saveDefaultConfig();
        TOKEN = getConfig().getString("TOKEN");
        serverId = getConfig().getString("SERVERID");
        channelId = getConfig().getString("CHANNELID");

        discordBot = new DiscordBot(this, TOKEN);

        discordBot.start();
        System.out.println("Started discord bot!");

        while (!registered){
            if (discordBot.getApi() != null) {
                registered = true;
                init();
            }
        }





    }

    private void init() {
        Bukkit.getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new JoinQuitListener(), this);
        getCommand("announcement").setExecutor(new AnnouncementCommand());
        discordBot.getApi().addListener(new MessageCreateListener());

        discordBot.getApi().updateStatus(UserStatus.DO_NOT_DISTURB);
        discordBot.getApi().updateActivity(ActivityType.CUSTOM, "Programming..");

        try {
            server = discordBot.getApi().getServerById(serverId).get();
        }catch (Exception e) {
            System.out.println("Invalid Server id");
        }
        try {
            channel = server.getTextChannelById(channelId).get();
        }catch (Exception e) {
            System.out.println("Invalid channel Id");
        }

        System.out.println(channel == null ? "Channel is null" : "Channel is not null");

        discordBot.onConnect(new Runnable() {
            @Override
            public void run() {
                try {
                    onConnectToDiscord(discordBot.getApi());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        if (channel != null) {
            channel.sendMessage("Bot is now online. This Channel and the Minecraft Server's Chat are now synced! Everything you send in here is automatically send to the minecraft chat and vice-versa!");
        }
    }

    private void onConnectToDiscord(DiscordApi api) throws ExecutionException, InterruptedException {
        getLogger().info("Current Status set to: " + api.getStatus().getStatusString());
        getLogger().info("Current activity set to: " + api.getActivity().get().getName());
        getLogger().info("Owner of this bot: " + api.getOwner().get().getDiscriminatedName());


    }

    @Override
    public void onDisable() {
        if (channel != null) {
            channel.sendMessage("Bot is now offline. Chats are not synced anymore");
        }
        System.out.println(channel == null ? "Channel is null" : "Channel is not null");
        discordBot.stop();
    }

    public static void setTextChannel(TextChannel textChannel) {
        if (channel != null) channel = textChannel;
    }

    public static TextChannel getTextChannel() {
        return channel;
    }

    public static DiscordBot getDiscordBot() {
        return discordBot;
    }
}
