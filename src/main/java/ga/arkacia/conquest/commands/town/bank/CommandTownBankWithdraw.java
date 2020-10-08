package ga.arkacia.conquest.commands.town.bank;

import ga.arkacia.conquest.commands.ISubCommand;
import ga.arkacia.conquest.objects.citizen.Citizen;
import ga.arkacia.conquest.objects.bank.Bank;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import static ga.arkacia.conquest.Config.lang;

public class CommandTownBankWithdraw implements ISubCommand {
    @Override
    public String getSubCommand() {
        return "withdraw";
    }

    @Override
    public String[] getTabComplete() {
        return new String[0];
    }

    @Override
    public void run(CommandSender sender, Command command, String label, String[] args) {
        if (Citizen.getCitizen((OfflinePlayer) sender).getTown() == null) {
            sender.sendMessage(lang("bank.no-town"));
            return;
        }
        int amount = Integer.parseInt(args[0]);
        Citizen citizen = Citizen.getCitizen((OfflinePlayer) sender);
        Bank bank = citizen.getOwnedTown().getBank();
        if (bank.getBalance() < amount) {
            sender.sendMessage(ChatColor.RED + "Bank doesn't have enough money");
            return;
        }
        bank.withdraw(amount);
        citizen.giveMoney(amount);
    }
}
