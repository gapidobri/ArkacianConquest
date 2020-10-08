package ga.arkacia.conquest.objects.chunk;

import com.earth2me.essentials.api.UserDoesNotExistException;
import ga.arkacia.conquest.objects.EResponse;
import ga.arkacia.conquest.objects.citizen.Citizen;
import ga.arkacia.conquest.objects.town.Town;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.awt.geom.Arc2D;
import java.util.*;

import static ga.arkacia.conquest.ArkacianConquest.chunkPrice;
import static ga.arkacia.conquest.Database.updateDB;

public class ClaimedChunk implements ConfigurationSerializable {
    public static List<ClaimedChunk> claimedChunks = new ArrayList<>();

    Chunk chunk;

    public ClaimedChunk(Chunk chunk) {
        this.chunk = chunk;
    }

    public static List<ClaimedChunk> getClaimedChunks() {
        return claimedChunks;
    }

    public static ClaimedChunk getChunk(Chunk chunk) {
        for (ClaimedChunk cc : claimedChunks) {
            if (cc.chunk == chunk) return cc;
        }
        return null;
    }

    /*
    public static EResponse claimChunk(ClaimedChunk claimedChunk) {
        return claimChunk(claimedChunk, claimedChunk.getOwnerTown(), claimedChunk);
    }

     */

    public static EResponse claimChunk(Chunk chunk, IChunkOwner owner, IChunkOwner payer) {
        if (getChunk(chunk) != null) {
            return EResponse.EXISTS;
        }
        try {
            if (payer.getBalance() < chunkPrice) return EResponse.BALANCE;
        } catch (UserDoesNotExistException e) {
            e.printStackTrace();
        }
        payer.takeMoney(chunkPrice);
        owner.claimChunk(chunk);
        claimedChunks.add(new ClaimedChunk(chunk));
        updateDB();
        return EResponse.OK;
    }

    public static IChunkOwner getOwnerTown(Chunk chunk) {
        return new ClaimedChunk(chunk).getOwnerTown();
    }

    public static ClaimedChunk deserialize(Map<String, Object> map) {
        return new ClaimedChunk(
                Bukkit.getWorld(UUID.fromString(map.get("world").toString())).getChunkAt((int) map.get("x"), (int) map.get("z"))
        );
    }

    public Chunk getChunk() {
        return chunk;
    }

    public Town getOwnerTown() {
        for (Town town : Town.getTowns()) {
            for (ClaimedChunk cc : town.getOwnedChunks()) {
                if (cc.getChunk() == getChunk()) {
                    return town;
                }
            }
        }
        return null;
    }

    public Citizen getOwnerCitizen() {
        for (Citizen citizen : Citizen.getCitizens()) {
            if (citizen.getOwnedChunks().contains(this)) return citizen;
        }
        return null;
    }

    public void unclaimChunk() {
        claimedChunks.remove(this);
        updateDB();
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("world", chunk.getWorld().getUID().toString());
        map.put("x", chunk.getX());
        map.put("z", chunk.getZ());
        return map;
    }

}
