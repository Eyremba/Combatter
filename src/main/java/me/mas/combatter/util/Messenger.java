package me.mas.combatter.util;

import me.mas.combatter.Combatter;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class Messenger
{
    private static Map<Message, String> messages = new HashMap<>();

    public Messenger(Combatter combatter)
    {
        for (Message msg : Message.values())
        {
            messages.put(msg, combatter.getConfig().getString(msg.toString()));
        }
    }

    private static String getMsg(Message message)
    {
        return messages.get(message);
    }

    public static String translate(String string)
    {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static final String PREFIX = ChatColor.translateAlternateColorCodes('&', "&aCombatter > &d");

    public static String config(Message message)
    {
        return config(message, true);
    }

    public static String config(Message message, boolean pre)
    {
        return (pre ? PREFIX : "") + translate(getMsg(message));
    }

    public static void msg(CommandSender player, Message message)
    {
        player.sendMessage(config(message));
    }
}
