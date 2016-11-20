package me.mas.combatter.cmds;

import me.mas.combatter.Combatter;
import me.mas.combatter.util.Message;
import me.mas.combatter.util.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAnalyse implements CommandExecutor
{
    private final Combatter combatter;

    public CommandAnalyse(Combatter combatter)
    {
        this.combatter = combatter;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!sender.hasPermission("combatter.analyse"))
        {
            Messenger.msg(sender, Message.NO_PERMS);
            return true;
        }

        if (args.length < 1 || args.length > 2)
        {
            Messenger.msg(sender, Message.ANALYSE_ARGS);
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null)
        {
            Messenger.msg(sender, Message.PLAYER_NULL);
            return true;
        }

        int sec = 10;

        if (args.length == 2)
        {
            try
            {
                sec = Integer.parseInt(args[1]);
            }
            catch (NumberFormatException ex)
            {
                Messenger.msg(sender, Message.NUMBER_INVALID);
                return true;
            }
        }

        combatter.getAnalysisManager().startAnalysis(sender, target, sec);
        return true;
    }
}
