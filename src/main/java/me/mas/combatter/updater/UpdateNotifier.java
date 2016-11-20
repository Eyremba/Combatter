package me.mas.combatter.updater;

import me.mas.combatter.Combatter;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UpdateNotifier implements Listener
{
    private final Combatter combatter;

    public UpdateNotifier(Combatter combatter)
    {
        this.combatter = combatter;
    }

    private String chatMsg;

    public void updateMessage(Object[] info)
    {
        chatMsg = ChatColor.translateAlternateColorCodes('&',
                "&aCombatter> &eA new update is available!" + "\n" +
                        "&aCombatter> New version: &ev" + info[0] + " &a(current: " + combatter.getDescription().getVersion() + ")" + "\n" +
                        "&aCombatter> Features: &e" + info[1]
        );
    }

    @EventHandler
    public void on(PlayerJoinEvent e)
    {
        if (e.getPlayer().isOp() && chatMsg != null)
            e.getPlayer().sendMessage(chatMsg);
    }
}
