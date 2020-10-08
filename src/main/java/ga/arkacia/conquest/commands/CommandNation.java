package ga.arkacia.conquest.commands;

import ga.arkacia.conquest.commands.nation.CommandNationCreate;
import ga.arkacia.conquest.commands.nation.CommandNationList;
import ga.arkacia.conquest.commands.nation.CommandNationRename;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import static ga.arkacia.conquest.Database.updateDB;
import static ga.arkacia.conquest.commands.Commands.subCommand;

public class CommandNation implements ICommand {
    @Override
    public String getCommand() {
        return "nation";
    }

    public static ISubCommand[] commands = {
            new CommandNationCreate(), // nation create <name ...>
            new CommandNationList(), // nation list
            new CommandNationRename(), // nation rename <name ...>
    };

    @Override
    public void run(CommandSender sender, Command command, String label, String[] args) {
        subCommand(commands, sender, command, label, args);
        updateDB();
    }

    @Override
    public ISubCommand[] getSubCommands() {
        return commands;
    }
}
