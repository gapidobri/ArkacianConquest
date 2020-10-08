package ga.arkacia.conquest.objects.town;

import com.earth2me.essentials.api.UserDoesNotExistException;
import ga.arkacia.conquest.objects.EResponse;
import ga.arkacia.conquest.objects.bank.Bank;
import ga.arkacia.conquest.objects.bank.BankResponse;
import ga.arkacia.conquest.objects.chunk.ClaimedChunk;
import ga.arkacia.conquest.objects.chunk.IChunkOwner;
import ga.arkacia.conquest.objects.citizen.Citizen;
import ga.arkacia.conquest.objects.citizen.ICitizen;
import ga.arkacia.conquest.objects.nation.Nation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

import static ga.arkacia.conquest.ArkacianConquest.townPrice;
import static ga.arkacia.conquest.Functions.idGen;

public class Town implements IChunkOwner, ICitizen, ConfigurationSerializable {
    public static List<Town> towns = new ArrayList<>();

    String id;
    String displayName;
    Nation ownedNation = null;
    Bank bank;
    List<ClaimedChunk> ownedChunks = new ArrayList<>();
    List<Citizen> citizens = new ArrayList<>();

    public Town(String displayName) {
        this.id = idGen(displayName);
        this.displayName = displayName;
        this.bank = new Bank(id);
    }

    public Town() {}

    public static List<Town> getTowns() {
        return towns;
    }

    public static List<String> getTownNames() {
        List<String> names = new ArrayList<>();
        for (Town town : towns) {
            names.add(town.getDisplayName());
        }
        return names;
    }

    public static EResponse addTown(Town town, Citizen payer) {
        for (Town t : towns) {
            if (t.getId().equals(town.getId())) return EResponse.EXISTS;
        }

        try {
            if (payer.getBalance() < townPrice) return EResponse.BALANCE;
        } catch (UserDoesNotExistException e) {
            e.printStackTrace();
        }

        payer.takeMoney(townPrice);

        towns.add(town);
        return EResponse.OK;
    }

    public static Town getTown(String id) {
        for (Town town : towns) {
            if (town.getId().equals(id)) return town;
        }
        return null;
    }

    public static Town deserialize(Map<String, Object> map) {
        Town town = new Town();
        town.id = (String) map.get("id");
        town.displayName = (String) map.get("displayName");
        if (map.containsKey("ownedNationId"))
            town.ownedNation = new Nation((String) map.get("ownedNationId"));
        town.bank = (Bank) map.get("bank");
        town.ownedChunks = (List<ClaimedChunk>) map.get("ownedChunks");
        for (String uuid : (List<String>) map.get("citizens")) {
            System.out.print(uuid + " -> ");
            town.citizens.add(new Citizen(Bukkit.getPlayer(UUID.fromString(uuid))));
            System.out.println(Bukkit.getPlayer(UUID.fromString(uuid)).getDisplayName());
        }
        return town;
    }

    public static void linkData() {
        if (towns == null) return;
        towns = towns.stream().peek(town -> {
            if (town.ownedNation != null) {
                town.ownedNation = Nation.getNations().stream()
                        .filter(n -> n.getId().equals(town.getOwnedNation().getId()))
                        .findFirst()
                        .get();
            }
        }).collect(Collectors.toList());
    }

    public List<ClaimedChunk> getOwnedChunks() {
        return ownedChunks;
    }

    public Nation getOwnedNation() {
        return ownedNation;
    }

    public EResponse rename(String name) {
        for (Town town : towns) {
            if (town.getId().equals(idGen(name))) return EResponse.EXISTS;
        }
        if (bank.rename(idGen(name)) == EResponse.EXISTS) return EResponse.EXISTS;
        displayName = name;
        id = idGen(name);
        return EResponse.OK;
    }

    public List<Citizen> getCitizens() {
        return citizens;
    }

    @Override
    public void claimChunk(Chunk chunk) {
        ownedChunks.add(new ClaimedChunk(chunk));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = ChatColor.translateAlternateColorCodes('&', displayName);
    }

    @Override
    public int getBalance() throws UserDoesNotExistException {
        return bank.getBalance();
    }

    @Override
    public BankResponse takeMoney(int amount) {
        return bank.withdraw(amount);
    }

    @Override
    public void giveMoney(int amount) {
        bank.deposit(amount);
    }

    public Citizen getMayor() {
        for (Citizen citizen : Citizen.getCitizens()) {
            if (citizen.getOwnedTown() == this) return citizen;
        }
        return null;
    }

    public Bank getBank() {
        return bank;
    }

    @Override
    public boolean addCitizen(Citizen citizen) {
        if (citizens.contains(citizen)) return false;
        citizens.add(citizen);
        return true;
    }

    @Override
    public boolean removeCitizen(Citizen citizen) {
        return true;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("displayName", displayName);
        if (ownedNation != null) map.put("ownedNationId", ownedNation.getId());
        map.put("bank", bank);
        map.put("ownedChunks", ownedChunks);
        map.put("citizens", citizens.stream().map(citizen -> citizen.getOfflinePlayer().getUniqueId().toString()).toArray());
        return map;
    }

}
