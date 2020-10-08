package ga.arkacia.conquest.commands.town;

import ga.arkacia.conquest.commands.ISubCommand;
import ga.arkacia.conquest.objects.citizen.Citizen;
import ga.arkacia.conquest.objects.town.Town;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import static ga.arkacia.conquest.Config.lang;
import static ga.arkacia.conquest.Functions.stringFromArray;

public class CommandTownRename implements ISubCommand {
    @Override
    public String getSubCommand() {
        return "rename";
    }

    @Override
    public String[] getTabComplete() {
        return (String[]) Town.getTownNames().toArray();
    }

    @Override
    public void run(CommandSender sender, Command command, String label, String[] args) {
        Town town = Citizen.getCitizen((OfflinePlayer) sender).getOwnedTown();
        String oldName = town.getDisplayName();
        switch (town.rename(stringFromArray(args))) {
            case EXISTS:
                sender.sendMessage(lang("town.rename.exists", oldName));
                break;
            case OK:
                sender.sendMessage(lang("town.rename.ok", oldName, town.getDisplayName()));
                break;
        }

    }
}
