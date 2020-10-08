package ga.arkacia.conquest.objects.citizen;

import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import ga.arkacia.conquest.objects.EResponse;
import ga.arkacia.conquest.objects.bank.BankResponse;
import ga.arkacia.conquest.objects.chunk.ClaimedChunk;
import ga.arkacia.conquest.objects.chunk.IChunkOwner;
import ga.arkacia.conquest.objects.nation.INationOwner;
import ga.arkacia.conquest.objects.nation.Nation;
import ga.arkacia.conquest.objects.town.ITownOwner;
import ga.arkacia.conquest.objects.town.Town;
import net.ess3.api.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static ga.arkacia.conquest.Database.updateDB;

public class Citizen implements INationOwner, ITownOwner, IChunkOwner, ConfigurationSerializable {
    public static List<Citizen> citizens = new ArrayList<>();

    OfflinePlayer offlinePlayer;
    Town ownedTown;
    Town helperTown;
    List<ClaimedChunk> ownedChunks = new ArrayList<>();

    public Citizen(OfflinePlayer player) {
        this.offlinePlayer = player;
    }

    public static List<Citizen> getCitizens() {
        return citizens;
    }

    public static EResponse addCitizen(OfflinePlayer player) {
        for (Citizen citizen : citizens) {
            if (citizen.getOfflinePlayer() == player) return EResponse.EXISTS;
        }
        citizens.add(new Citizen(player));
        updateDB();
        return EResponse.OK;
    }

    public static Citizen getCitizen(OfflinePlayer player) {
        for (Citizen citizen : citizens) {
            if (citizen.getOfflinePlayer() == player) return citizen;
        }
        Citizen citizen = new Citizen(player);
        citizens.add(citizen);
        return citizen;
    }

    public static Citizen deserialize(Map<String, Object> map) {
        Citizen citizen = new Citizen(Bukkit.getPlayer(UUID.fromString(String.valueOf(map.get("id")))));
        if (map.containsKey("ownedTownId")) {
            Town town = new Town();
            town.setId((String) map.get("ownedTownId"));
            citizen.ownedTown = town;
        }
        if (map.containsKey("helperTownId")) {
            Town town = new Town();
            town.setId((String) map.get("helperTownId"));
            citizen.helperTown = town;
        }
        citizen.ownedChunks = (List<ClaimedChunk>) map.get("ownedChunks");
        return citizen;
    }

    public static void linkData() {
        if (citizens == null) return;
        citizens = citizens.stream().peek(citizen -> {
            if (citizen.ownedTown != null) {
                citizen.ownedTown = Town.getTowns().stream()
                        .filter(t -> t.getId().equals(citizen.getOwnedTown().getId()))
                        .findFirst()
                        .get();
            }
            if (citizen.helperTown != null) {
                citizen.helperTown = Town.getTowns().stream()
                        .filter(t -> t.getId().equals(citizen.getHelperTown().getId()))
                        .findFirst()
                        .get();
            }
        }).collect(Collectors.toList());
    }

    public List<ClaimedChunk> getOwnedChunks() {
        return ownedChunks;
    }

    public Town getOwnedTown() {
        return ownedTown;
    }

    public void setOwnedTown(Town ownedTown) {
        this.ownedTown = ownedTown;
    }

    public Nation getOwnedNation() {
        return getOwnedTown().getOwnedNation();
    }

    public Town getTown() {
        for (Town town : Town.getTowns()) {
            for (Citizen citizen : town.getCitizens()) {
                if (citizen.getOfflinePlayer() == offlinePlayer) {
                    return town;
                }
            }
        }
        return null;
    }

    public OfflinePlayer getOfflinePlayer() {
        return offlinePlayer;
    }

    public void setOfflinePlayer(OfflinePlayer offlinePlayer) {
        this.offlinePlayer = offlinePlayer;
    }

    public Player getPlayer() {
        return offlinePlayer.getPlayer();
    }

    @Override
    public String getDisplayName() {
        return offlinePlayer.getName();
    }

    @Override
    public void setNationOwner(Nation nation) {

    }

    public int getBalance() throws UserDoesNotExistException {
        return Economy.getMoneyExact(offlinePlayer.getName()).intValue();
    }

    @Override
    public BankResponse takeMoney(int amount) {
        try {
            Economy.substract(offlinePlayer.getName(), BigDecimal.valueOf(amount));
            return BankResponse.OK;
        } catch (UserDoesNotExistException | NoLoanPermittedException e) {
            e.printStackTrace();
            return BankResponse.BALANCE;
        }
    }

    @Override
    public void giveMoney(int amount) {
        try {
            Economy.add(offlinePlayer.getName(), BigDecimal.valueOf(amount));
        } catch (UserDoesNotExistException | NoLoanPermittedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void claimChunk(Chunk chunk) {
        ownedChunks.add(new ClaimedChunk(chunk));
    }

    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", offlinePlayer.getUniqueId().toString());
        if (ownedTown != null)
            map.put("ownedTownId", ownedTown.getId());
        if (helperTown != null)
            map.put("helperTownId", helperTown.getId());
        map.put("ownedChunks", ownedChunks);

        return map;
    }

    private Town getHelperTown() {
        return helperTown;
    }
}
