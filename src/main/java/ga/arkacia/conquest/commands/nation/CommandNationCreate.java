package ga.arkacia.conquest.commands.nation;

import ga.arkacia.conquest.commands.ICommand;
import ga.arkacia.conquest.objects.Citizen;
import ga.arkacia.conquest.objects.nation.Nation;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandNationCreate implements ICommand {
    @Override
    public String getCommand() {
        return "create";
    }

    @Override
    public void run(CommandSender sender, Command command, String label, String[] args) {
        Nation nation = new Nation(args[0]);
        nation.setOwner(Citizen.getCitizen((OfflinePlayer) sender));
        switch (Nation.addNation(nation)) {
            case OK:
                Citizen.getCitizen((OfflinePlayer) sender).ownNation(nation);
                sender.sendMessage(ChatColor.GREEN + nation.getDisplayName() + " created successfully");
                break;
            case EXISTS:
                sender.sendMessage(ChatColor.RED + nation.getDisplayName() + " already exists");
                break;
            case BALANCE:
                sender.sendMessage(ChatColor.RED + "You don't have enough money to create a nation");
                break;
        }
    }
}
