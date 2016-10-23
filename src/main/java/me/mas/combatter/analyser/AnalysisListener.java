package me.mas.combatter.analyser;

import me.mas.combatter.Combatter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AnalysisListener implements Listener
{
    private Combatter combatter;

    public AnalysisListener(Combatter combatter)
    {
        this.combatter = combatter;

        ping();
    }

    @EventHandler
    public void on(EntityDamageByEntityEvent e)
    {
        if (e.getDamager() instanceof Player)
        {
            Player player = (Player) e.getDamager();
            Analysis analysis = combatter.getAnalysisManager().getAnalysis(player);

            if (analysis == null)
                return;

            analysis.addHit();

            /* Reach */
            Location attacker = player.getLocation().clone();
            attacker.setY(0);

            Location victim = e.getEntity().getLocation().clone();
            victim.setY(0);

            analysis.addReach(attacker.distance(victim));
        }
    }

    private Map<UUID, Integer> clicks = new HashMap<>();

    private Integer getClicks(Player player)
    {
        return clicks.get(player.getUniqueId());
    }

    private void incrementClicks(Player player)
    {
        clicks.put(player.getUniqueId(), getClicks(player) == null ? 1 : getClicks(player) + 1);
    }

    @EventHandler
    public void on(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();
        Analysis analysis = combatter.getAnalysisManager().getAnalysis(player);

        if (analysis == null)
            return;

        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)
        {
            incrementClicks(player);

            if (e.getPlayer().getNearbyEntities(5, 5, 5).size() > 0)
                analysis.addInteraction();
        }
    }

    @EventHandler
    public void on(PlayerQuitEvent e)
    {
        remove(e.getPlayer());
    }

    @EventHandler
    public void on(PlayerKickEvent e)
    {
        remove(e.getPlayer());
    }

    private void remove(Player player)
    {
        clicks.remove(player.getUniqueId());
    }

    private void ping()
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                for (UUID id : combatter.getAnalysisManager().getAnalysisList())
                {
                    Player player = Bukkit.getPlayer(id);

                    if (player != null)
                    {
                        Analysis analysis = combatter.getAnalysisManager().getAnalysis(player);

                        analysis.addPing(((CraftPlayer) player).getHandle().ping);

                        if (clicks.containsKey(player.getUniqueId()))
                            analysis.addClicks(clicks.get(player.getUniqueId()));
                        else
                            analysis.addClicks(0);
                    }
                }
            }
        }.runTaskTimer(combatter, 0L, 20L);
    }
}
