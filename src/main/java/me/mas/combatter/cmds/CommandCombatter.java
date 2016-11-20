package me.mas.combatter.cmds;

import me.mas.combatter.Combatter;
import me.mas.combatter.util.Messenger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandCombatter implements CommandExecutor
{
    private final Combatter combatter;

    public CommandCombatter(Combatter combatter)
    {
        this.combatter = combatter;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        sender.sendMessage(Messenger.translate("&aCombatter by &dItsMas_ &aversion &d" + combatter.getDescription().getVersion()));
        return true;
    }
}
