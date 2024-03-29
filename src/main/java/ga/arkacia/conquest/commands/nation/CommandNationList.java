package ga.arkacia.conquest.commands.nation;

import ga.arkacia.conquest.commands.ISubCommand;
import ga.arkacia.conquest.objects.citizen.Citizen;
import ga.arkacia.conquest.objects.nation.Nation;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import static ga.arkacia.conquest.Config.lang;
import static ga.arkacia.conquest.gui.NationListGUI.showGUI;

public class CommandNationList implements ISubCommand {
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
        sender.sendMessage(lang("nation.list.head"));
        for (Nation nation : Nation.getNations()) {
            sender.sendMessage(lang("nation.list.list", nation.getDisplayName()));
        }
        showGUI(Citizen.getCitizen((OfflinePlayer) sender));
    }
}
