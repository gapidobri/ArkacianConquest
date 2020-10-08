package ga.arkacia.conquest.commands.town;

import ga.arkacia.conquest.commands.ISubCommand;
import ga.arkacia.conquest.objects.citizen.Citizen;
import ga.arkacia.conquest.objects.chunk.ClaimedChunk;
import ga.arkacia.conquest.objects.town.Town;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static ga.arkacia.conquest.Config.lang;

public class CommandTownClaim implements ISubCommand {
    @Override
    public String getSubCommand() {
        return "claim";
    }

    @Override
    public String[] getTabComplete() {
        return new String[0];
    }

    @Override
    public void run(CommandSender sender, Command command, String label, String[] args) {
        Citizen citizen = Citizen.getCitizen((OfflinePlayer) sender);
        Town ownedTown = citizen.getOwnedTown();
        switch (ClaimedChunk.claimChunk(citizen.getPlayer().getChunk(), ownedTown, ownedTown)) {
            case OK:
                sender.sendMessage(lang("chunk.claim.ok"));
                break;
            case BALANCE:
                sender.sendMessage(lang("chunk.claim.balance"));
                break;
            case EXISTS:
                sender.sendMessage(lang("chunk.claim.exists", ClaimedChunk.getOwnerTown(((Player) sender).getChunk()).getDisplayName()));
                break;
        }
    }
}
