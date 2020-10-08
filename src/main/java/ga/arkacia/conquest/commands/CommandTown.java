package ga.arkacia.conquest.commands;

import ga.arkacia.conquest.commands.town.*;
import ga.arkacia.conquest.commands.town.bank.CommandTownBankBalance;
import ga.arkacia.conquest.commands.town.bank.CommandTownBankDeposit;
import ga.arkacia.conquest.commands.town.bank.CommandTownBankWithdraw;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import static ga.arkacia.conquest.commands.Commands.*;

public class CommandTown implements ICommand {
    @Override
    public String getCommand() {
        return "town";
    }

    public static ISubCommand[] subCommands = {
            new CommandTownCreate(), // town create <name ...>
            new CommandTownList(), // town list
            new CommandTownBankBalance(), // town balance
            new CommandTownBankDeposit(), // town deposit <amount>
            new CommandTownBankWithdraw(), // town withdraw <amount>
            new CommandTownClaim(), // town claim
            new CommandTownRename(), // town rename <name ...>
            new CommandTownInfo() // town info [name]
    };

    @Override
    public void run(CommandSender sender, Command command, String label, String[] args) {
        subCommand(subCommands, sender, command, label, args);
    }

    @Override
    public ISubCommand[] getSubCommands() {
        return subCommands;
    }
}
