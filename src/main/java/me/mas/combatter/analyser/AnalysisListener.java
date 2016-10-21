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
import org.bukkit.scheduler.BukkitRunnable;

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

    @EventHandler
    public void on(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();
        Analysis analysis = combatter.getAnalysisManager().getAnalysis(player);

        if (analysis == null)
            return;

        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)
        {
            analysis.addClick();

            if (e.getPlayer().getNearbyEntities(5, 5, 5).size() > 0)
                analysis.addInteraction();
        }
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
                        combatter.getAnalysisManager().getAnalysis(player).addPing(((CraftPlayer) player).getHandle().ping);
                }
            }
        }.runTaskTimer(combatter, 0L, 20L);
    }
}
