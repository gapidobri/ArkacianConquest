package ga.arkacia.conquest.commands.town;

import ga.arkacia.conquest.commands.ICommand;
import ga.arkacia.conquest.objects.Citizen;
import ga.arkacia.conquest.objects.town.Town;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandTownCreate implements ICommand {
    @Override
    public String getCommand() {
        return "create";
    }

    @Override
    public void run(CommandSender sender, Command command, String label, String[] args) {
        Town town = new Town(args[0]);
        //town.setOwner(Nation.getNation(args[1])); // TODO: Towns without a nation
        town.setOwner(Citizen.getCitizen((OfflinePlayer) sender).getOwnedNations().get(0)); // TODO: Multiple nations?
        switch (Town.addTown(town)) {
            case OK:
                Citizen.getCitizen((OfflinePlayer) sender).ownTown(town);
                sender.sendMessage(ChatColor.GREEN + town.getDisplayName() + " created successfully");
                break;
            case EXISTS:
                sender.sendMessage(ChatColor.RED + town.getDisplayName() + " already exists");
                break;
            case BALANCE:
                sender.sendMessage(ChatColor.RED + "You don't have enough money to create a town");
                break;
        }
    }
}
