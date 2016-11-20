package me.mas.combatter;

import me.mas.combatter.analyser.AnalysisListener;
import me.mas.combatter.analyser.AnalysisManager;
import me.mas.combatter.cmds.CommandAnalyse;
import me.mas.combatter.cmds.CommandCombatter;
import me.mas.combatter.metrics.Metrics;
import me.mas.combatter.updater.UpdateManager;
import me.mas.combatter.updater.UpdateNotifier;
import me.mas.combatter.util.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginBase;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class Combatter extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        saveDefaultConfig();

        analysisManager = new AnalysisManager(this);

        new Messenger(this);

        new Metrics(this);

        getCommand("analyse").setExecutor(new CommandAnalyse(this));
        getCommand("combatter").setExecutor(new CommandCombatter(this));

        Bukkit.getPluginManager().registerEvents(new AnalysisListener(this), this);

        final UpdateManager updateManager = new UpdateManager(this);
        final UpdateNotifier updateNotifier = new UpdateNotifier(this);

        Bukkit.getPluginManager().registerEvents(updateNotifier, this);

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                Object[] updates = updateManager.getLatestUpdate();

                if (updates != null && updates.length == 2)
                    updateNotifier.updateMessage(updates);
            }
        }.runTaskTimer(this, 20L * 60L, 20L * 60L * 20L);
    }

    private AnalysisManager analysisManager;
    public AnalysisManager getAnalysisManager()
    {
        return analysisManager;
    }
}
