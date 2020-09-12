package ga.arkacia.conquest.commands;

import ga.arkacia.conquest.commands.nation.CommandNationCreate;
import ga.arkacia.conquest.commands.nation.CommandNationList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import static ga.arkacia.conquest.commands.Commands.subCommand;

public class CommandNation implements ICommand {
    @Override
    public String getCommand() {
        return "nation";
    }

    public static ICommand[] commands = {
            new CommandNationCreate(),
            new CommandNationList(),
    };

    @Override
    public void run(CommandSender sender, Command command, String label, String[] args) {
        subCommand(commands, sender, command, label, args);
    }
}
