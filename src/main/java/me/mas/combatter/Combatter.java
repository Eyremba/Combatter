package me.mas.combatter;

import me.mas.combatter.analyser.AnalysisListener;
import me.mas.combatter.analyser.AnalysisManager;
import me.mas.combatter.cmds.CommandAnalyse;
import me.mas.combatter.cmds.CommandCombatter;
import me.mas.combatter.updater.UpdateManager;
import me.mas.combatter.updater.UpdateNotifier;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Combatter extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        analysisManager = new AnalysisManager(this);

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

    private AnalysisManager analysisManager;
    public AnalysisManager getAnalysisManager()
    {
        return analysisManager;
    }

    private UpdateManager updateManager;
}
