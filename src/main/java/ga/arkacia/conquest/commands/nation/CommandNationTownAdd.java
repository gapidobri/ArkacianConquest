package ga.arkacia.conquest.commands.nation;

import ga.arkacia.conquest.commands.ICommand;
import ga.arkacia.conquest.commands.ISubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandNationTownAdd implements ICommand {
    @Override
    public String getCommand() {
        return "add";
    }

    @Override
    public void run(CommandSender sender, Command command, String label, String[] args) {

    }

    @Override
    public ISubCommand[] getSubCommands() {
        return new ISubCommand[0];
    }
}
