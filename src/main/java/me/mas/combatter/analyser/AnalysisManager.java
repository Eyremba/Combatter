package me.mas.combatter.analyser;

import me.mas.combatter.Combatter;
import me.mas.combatter.util.Message;
import me.mas.combatter.util.Messenger;
import me.mas.combatter.util.UtilMaths;
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

    public void startAnalysis(final CommandSender caller, final Player target, final int sec)
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

        caller.sendMessage(Messenger.config(Message.STARTED_ANALYSIS, false).replace("%player%", target.getName()).replace("%seconds%", String.valueOf(sec)));

        sysmap.put(target.getUniqueId(), new Analysis());

        final String name = caller.getName();
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

        caller.sendMessage(Messenger.config(Message.ANALYSIS_FINISHED, false).replace("%player%", tp.getName()));

        msg(caller, "Avg reach", analysis.getAvgReach());
        msg(caller, "Lowest reach", analysis.getLowestReach());
        msg(caller, "Highest reach", analysis.getHighestReach());

        msg(caller, "Avg CPS", analysis.getClicks() / sec);
        msg(caller, "Lowest CPS", analysis.getLowestCPS());
        msg(caller, "Highest CPS", analysis.getHighestCPS());

        msg(caller, "Avg ping", analysis.getAvgPing());
        msg(caller, "Lowest ping", analysis.getLowestPing());
        msg(caller, "Highest ping", analysis.getHighestPing());

        msg(caller, "Hit %", analysis.getAccuracy());
    }

    private void msg(CommandSender caller, String data, Object value)
    {
        caller.sendMessage(Messenger.config(Message.RESULT, false).replace("%result%", data).replace("%value%", value.toString()));
    }
}
