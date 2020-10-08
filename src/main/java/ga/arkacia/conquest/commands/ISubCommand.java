package ga.arkacia.conquest.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface ISubCommand {
    String getSubCommand();
    String[] getTabComplete();
    void run(CommandSender sender, Command command, String label, String[] args);
}
