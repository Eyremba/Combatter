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
import org.bukkit.plugin.java.JavaPlugin;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.UUID;

public class Combatter extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        saveDefaultConfig();

        analysisManager = new AnalysisManager(this);
        messenger = new Messenger(this);

        Metrics metrics = new Metrics(this);

        metrics.addCustomChart(new Metrics.SimplePie("used_version")
        {
            @Override
            public String getValue()
            {
                return getDescription().getVersion();
            }
        });

        metrics.addCustomChart(new Metrics.SingleLineChart("player_count")
        {
            @Override
            public int getValue()
            {
                return Bukkit.getOnlinePlayers().size();
            }
        });

        getCommand("analyse").setExecutor(new CommandAnalyse(this));
        getCommand("combatter").setExecutor(new CommandCombatter(this));

        Bukkit.getPluginManager().registerEvents(new AnalysisListener(this), this);

        updateManager = new UpdateManager(this);

        Object[] updates = updateManager.getLatestUpdate();
        if (updates != null && updates.length == 2)
        {
            Bukkit.getPluginManager().registerEvents(new UpdateNotifier(this, updates), this);
        }
    }

    @Override
    public void onDisable()
    {
        saveConfig();
    }

    private Messenger messenger;

    private AnalysisManager analysisManager;
    public AnalysisManager getAnalysisManager()
    {
        return analysisManager;
    }

    private UpdateManager updateManager;
}
