package me.mas.combatter.cmds;

import me.mas.combatter.util.Messenger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandCombatter implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        Messenger.msg(sender, "&aCombatter by &dItsMas_ &aversion &d1.0.0");
        return true;
    }
}
