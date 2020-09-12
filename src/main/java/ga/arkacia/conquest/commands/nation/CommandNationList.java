package ga.arkacia.conquest.commands.nation;

import ga.arkacia.conquest.commands.ICommand;
import ga.arkacia.conquest.objects.nation.Nation;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandNationList implements ICommand {
    @Override
    public String getCommand() {
        return "list";
    }

    @Override
    public void run(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("List of nations:");
        for (Nation nation : Nation.getNations()) {
            sender.sendMessage(nation.getDisplayName());
        }
    }
}
