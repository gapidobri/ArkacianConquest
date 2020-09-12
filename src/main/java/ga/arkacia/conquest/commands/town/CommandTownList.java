package ga.arkacia.conquest.commands.town;

import ga.arkacia.conquest.commands.ICommand;
import ga.arkacia.conquest.objects.town.Town;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandTownList implements ICommand {
    @Override
    public String getCommand() {
        return "list";
    }

    @Override
    public void run(CommandSender sender, Command command, String label, String[] args) {
        for (Town town : Town.getTowns()) {
            sender.sendMessage(town.getDisplayName());
        }
    }
}
