package me.mas.combatter;

import me.mas.combatter.analyser.AnalysisListener;
import me.mas.combatter.analyser.AnalysisManager;
import me.mas.combatter.cmds.CommandAnalyse;
import me.mas.combatter.cmds.CommandCombatter;
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
    }

    private AnalysisManager analysisManager;
    public AnalysisManager getAnalysisManager()
    {
        return analysisManager;
    }
}
