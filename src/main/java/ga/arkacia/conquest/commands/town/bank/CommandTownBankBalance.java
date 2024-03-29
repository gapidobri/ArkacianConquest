package ga.arkacia.conquest.commands.town.bank;

import ga.arkacia.conquest.commands.ISubCommand;
import ga.arkacia.conquest.objects.citizen.Citizen;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import static ga.arkacia.conquest.Config.lang;

public class CommandTownBankBalance implements ISubCommand {
    @Override
    public String getSubCommand() {
        return "balance";
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
        sender.sendMessage(
                String.valueOf(Citizen.getCitizen((OfflinePlayer) sender).getOwnedTown()
                        .getBank().getBalanceString()));
    }
}
