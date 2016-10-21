package me.mas.combatter.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Messenger
{
    public static void msg(CommandSender player, Message message)
    {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message.getMsg()));
    }

    public static void msg(CommandSender player, String msg)
    {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a" + msg));
    }
}
