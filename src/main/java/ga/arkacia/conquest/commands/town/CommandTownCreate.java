package ga.arkacia.conquest.commands.town;

import ga.arkacia.conquest.commands.ISubCommand;
import ga.arkacia.conquest.objects.citizen.Citizen;
import ga.arkacia.conquest.objects.chunk.ClaimedChunk;
import ga.arkacia.conquest.objects.town.Town;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static ga.arkacia.conquest.Config.lang;
import static ga.arkacia.conquest.Functions.stringFromArray;

public class CommandTownCreate implements ISubCommand {
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
        Citizen citizen = Citizen.getCitizen((OfflinePlayer) sender);
        if (citizen.getOwnedTown() != null) {
            sender.sendMessage("You are already an owner of a town");
            return;
        }
        Town town = new Town(ChatColor.translateAlternateColorCodes('&', stringFromArray(args)));
        town.addCitizen(citizen);
        if (ClaimedChunk.getChunk(((OfflinePlayer) sender).getPlayer().getChunk()) != null) {
            sender.sendMessage(lang("chunk.claim.exists", ClaimedChunk.getOwnerTown(((Player) sender).getChunk()).getDisplayName()));
            return;
        }
        switch (Town.addTown(town, citizen)) {
            case OK:
                citizen.setOwnedTown(town);
                ClaimedChunk.claimChunk(((Player) sender).getChunk(), town, citizen);
                sender.sendMessage(lang("town.create.ok", town.getDisplayName()));
                break;
            case EXISTS:
                sender.sendMessage(lang("town.create.exists", town.getDisplayName()));
                break;
            case BALANCE:
                sender.sendMessage(lang("town.create.balance"));
                break;
        }
    }
}
