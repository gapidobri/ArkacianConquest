package ga.arkacia.conquest.commands.town;

import ga.arkacia.conquest.commands.ISubCommand;
import ga.arkacia.conquest.objects.town.Town;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import static ga.arkacia.conquest.Config.lang;

public class CommandTownList implements ISubCommand {
    @Override
    public String getSubCommand() {
        return "list";
    }

    @Override
    public String[] getTabComplete() {
        return new String[0];
    }

    @Override
    public void run(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(lang("town.list.head"));
        for (Town town : Town.getTowns()) {
            sender.sendMessage(lang("town.list.list", town.getDisplayName(), town.getId()));
        }
    }
}
