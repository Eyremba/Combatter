package me.mas.combatter.updater;

import me.mas.combatter.Combatter;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UpdateNotifier implements Listener
{
    public UpdateNotifier(Combatter combatter, Object[] updates)
    {
        chatMsg = ChatColor.translateAlternateColorCodes('&',
                "&aCombatter> &eA new update is available!" + "\n" +
                "&aCombatter> New version: &ev" + updates[0] + " &a(current: " + combatter.getDescription().getVersion() + ")" + "\n" +
                "&aCombatter> Features: &e" + updates[1]
        );
    }

    private final String chatMsg;

    @EventHandler
    public void on(PlayerJoinEvent e)
    {
        if (e.getPlayer().isOp())
            e.getPlayer().sendMessage(chatMsg);
    }
}
