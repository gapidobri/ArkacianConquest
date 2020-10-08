package ga.arkacia.conquest.commands.nation;

import ga.arkacia.conquest.commands.ISubCommand;
import ga.arkacia.conquest.objects.citizen.Citizen;
import ga.arkacia.conquest.objects.nation.Nation;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import static ga.arkacia.conquest.Config.lang;
import static ga.arkacia.conquest.Functions.stringFromArray;

public class CommandNationCreate implements ISubCommand {
    @Override
    public String getSubCommand() {
        return "create";
    }

    @Override
    public String[] getTabComplete() {
        return new String[0];
    }

    @Override
    public void run(CommandSender sender, Command command, String label, String[] args) {
        Nation nation = new Nation(ChatColor.translateAlternateColorCodes('&', stringFromArray(args)));
        switch (Nation.addNation(nation)) {
            case OK:
                Citizen.getCitizen((OfflinePlayer) sender).setNationOwner(nation);
                sender.sendMessage(lang("nation.create.ok", nation.getDisplayName()));
                break;
            case EXISTS:
                sender.sendMessage(lang("nation.create.exists", nation.getDisplayName()));
                break;
            case BALANCE:
                sender.sendMessage(lang("nation.create.balance"));
                break;
            case MISSING:
                sender.sendMessage(lang("nation.create.no-town"));
        }
    }
}
