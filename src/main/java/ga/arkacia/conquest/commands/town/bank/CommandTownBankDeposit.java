package ga.arkacia.conquest.commands.town.bank;

import com.earth2me.essentials.api.UserDoesNotExistException;
import ga.arkacia.conquest.commands.ISubCommand;
import ga.arkacia.conquest.objects.citizen.Citizen;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import static ga.arkacia.conquest.Config.lang;

public class CommandTownBankDeposit implements ISubCommand {

    @Override
    public String getSubCommand() {
        return "deposit";
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
        try {
            if (citizen.getBalance() < amount) {
                sender.sendMessage(ChatColor.RED + "You don't have enough money");
                return;
            }
            citizen.takeMoney(amount);
            citizen.getOwnedTown().getBank().deposit(amount);
            sender.sendMessage(ChatColor.GREEN + "Deposited " + amount + " drakes to bank");
        } catch (UserDoesNotExistException e) {
            e.printStackTrace();
        }


    }
}
