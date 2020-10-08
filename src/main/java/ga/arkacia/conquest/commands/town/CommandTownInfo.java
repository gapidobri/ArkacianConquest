package ga.arkacia.conquest.commands.town;

import ga.arkacia.conquest.commands.ISubCommand;
import ga.arkacia.conquest.objects.citizen.Citizen;
import ga.arkacia.conquest.objects.town.Town;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandTownInfo implements ISubCommand {

    @Override
    public String getSubCommand() {
        return "info";
    }

    @Override
    public String[] getTabComplete() {
        return (String[]) Town.getTownNames().toArray();
    }

    @Override
    public void run(CommandSender sender, Command command, String label, String[] args) {
        Town town = null;
        switch (args.length) {
            case 0:
                town = Citizen.getCitizen((OfflinePlayer) sender).getTown();
                if (town == null) {
                    sender.sendMessage("You are not in a town");
                    return;
                }
                break;
            case 1:
                town = Town.getTown(args[0]);
                break;
        }

        if (town == null) {
            sender.sendMessage("This town doesn't exist");
            return;
        }

        sender.sendMessage("Name: " + town.getDisplayName());
        sender.sendMessage("Id: " + town.getId());
        sender.sendMessage("Owner: " + town.getMayor().getDisplayName());
    }
}
