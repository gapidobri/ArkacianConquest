package ga.arkacia.conquest.gui;

import ga.arkacia.conquest.objects.chunk.ClaimedChunk;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class RegionTitle implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (e.getFrom().getChunk() == e.getTo().getChunk() ||
                ClaimedChunk.getOwnerTown(e.getFrom().getChunk()) == ClaimedChunk.getOwnerTown(e.getTo().getChunk())) return;

        ClaimedChunk cc = ClaimedChunk.getChunk(e.getTo().getChunk());
        e.getPlayer().sendTitle(
                cc != null ? ChatColor.translateAlternateColorCodes('&', cc.getOwnerTown().getDisplayName()) : "Wilderness",
                "", 0, 70, 0);
    }
}
