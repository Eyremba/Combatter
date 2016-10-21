package me.mas.combatter.analyser;

import me.mas.combatter.Combatter;
import me.mas.combatter.util.Message;
import me.mas.combatter.util.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class AnalysisManager
{
    private Combatter combatter;

    public AnalysisManager(Combatter combatter)
    {
        this.combatter = combatter;
    }

    private Map<UUID, Analysis> sysmap = new HashMap<>();

    Set<UUID> getAnalysisList()
    {
        return sysmap.keySet();
    }

    Analysis getAnalysis(Player player)
    {
        return sysmap.get(player.getUniqueId());
    }

    /* */
    private List<String> analysing = new ArrayList<>();

    public void startAnalysis(CommandSender caller, Player target, int sec)
    {
        if (analysing.contains(caller.getName()))
        {
            Messenger.msg(caller, Message.ALREADY_ANALYSING);
            return;
        }

        if (sysmap.containsKey(target.getUniqueId()))
        {
            Messenger.msg(caller, Message.ALREADY_BEING_ANALYSED);
            return;
        }

        Messenger.msg(caller, "&aStarted analysis of &d" + target.getName() + " &afor &d" + sec + "s");

        sysmap.put(target.getUniqueId(), new Analysis());

        String name = caller.getName();
        analysing.add(name);

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                analysing.remove(name);
                msgResults(caller, target.getUniqueId(), sec);
            }
        }.runTaskLater(combatter, 20L * sec);
    }

    private void msgResults(CommandSender caller, UUID target, int sec)
    {
        if (caller == null)
            return;

        Player tp = Bukkit.getPlayer(target);

        if (tp == null)
        {
            Messenger.msg(caller, Message.PLAYER_OFFLINE);
            return;
        }

        Analysis analysis = getAnalysis(tp);
        sysmap.remove(target);

        if (analysis == null)
        {
            Messenger.msg(caller, Message.ANALYSIS_NULL);
            return;
        }

        Messenger.msg(caller, "=== Analysis results for &d" + tp.getName() + " &a===");
        Messenger.msg(caller, "Avg Reach: &d" + analysis.getAvgReach());
        Messenger.msg(caller, "Avg CPS: &d" + analysis.getClicks() / sec);
        Messenger.msg(caller, "Avg Ping: &d" + analysis.getAvgPing());

        Messenger.msg(caller, "TEST Interactions: &d" + analysis.interactions);
        Messenger.msg(caller, "TEST Hits: &d" + analysis.hits);
        Messenger.msg(caller, "TEST Thing: &d" + (analysis.hits / analysis.interactions));
        Messenger.msg(caller, "TEST Accuracy: &d" + analysis.getAccuracy());
    }
}
