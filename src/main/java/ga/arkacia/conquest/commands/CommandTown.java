package ga.arkacia.conquest.commands;

import ga.arkacia.conquest.commands.town.CommandTownCreate;
import ga.arkacia.conquest.commands.town.CommandTownList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import static ga.arkacia.conquest.commands.Commands.*;

public class CommandTown implements ICommand {
    @Override
    public String getCommand() {
        return "town";
    }

    public static ICommand[] commands = {
            new CommandTownCreate(),
            new CommandTownList(),
    };

    @Override
    public void run(CommandSender sender, Command command, String label, String[] args) {
        subCommand(commands, sender, command, label, args);
    }
}
