package ga.arkacia.conquest.commands.nation;

import ga.arkacia.conquest.commands.ISubCommand;
import ga.arkacia.conquest.objects.citizen.Citizen;
import ga.arkacia.conquest.objects.nation.Nation;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import static ga.arkacia.conquest.Functions.stringFromArray;

public class CommandNationRename implements ISubCommand {
    @Override
    public String getSubCommand() {
        return "rename";
    }

    @Override
    public String[] getTabComplete() {
        return (String[]) Nation.getNationNames().toArray();
    }

    @Override
    public void run(CommandSender sender, Command command, String label, String[] args) {
        Citizen.getCitizen((OfflinePlayer) sender).getOwnedNation().setDisplayName(stringFromArray(args));
        sender.sendMessage(ChatColor.GREEN + "Nation has been renamed");
    }
}
