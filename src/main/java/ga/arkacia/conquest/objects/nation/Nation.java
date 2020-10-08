package ga.arkacia.conquest.objects.nation;

import com.earth2me.essentials.api.UserDoesNotExistException;
import ga.arkacia.conquest.objects.citizen.Citizen;
import ga.arkacia.conquest.objects.EResponse;
import ga.arkacia.conquest.objects.bank.BankResponse;
import ga.arkacia.conquest.objects.town.ITownOwner;
import ga.arkacia.conquest.objects.town.Town;
import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ga.arkacia.conquest.ArkacianConquest.nationPrice;
import static ga.arkacia.conquest.Database.updateDB;
import static ga.arkacia.conquest.Functions.idGen;

public class Nation implements ConfigurationSerializable, ITownOwner {
    public static List<Nation> nations = new ArrayList<>();

    String id; // All lowercase name of the nation
    String displayName; // Pretty name of the nation

    public Nation() {}

    public Nation(String displayName) {
        this.displayName = displayName;
        id = idGen(displayName);
    }

    public static List<Nation> getNations() {
        return nations;
    }

    public static Nation getNation(String name) {
        for (Nation nation : nations) {
            if (nation.getDisplayName().equals(name) || nation.getId().equals(name)) return nation;
        }
        return null;
    }

    public static List<String> getNationNames() {
        List<String> names = new ArrayList<>();
        for (Nation nation : nations) {
            names.add(nation.getDisplayName());
        }
        return names;
    }

    public static EResponse addNation(Nation nation) {
        for (Nation n : nations) {
            if (n.getId().equals(nation.getId())) return EResponse.EXISTS;
        }

        if (nation.getOwner() == null) return EResponse.MISSING;

        try {
            if (nation.getOwner().getBalance() < nationPrice) return EResponse.BALANCE;
        } catch (UserDoesNotExistException e) {
            e.printStackTrace();
        }

        nation.getOwner().takeMoney(nationPrice);
        nations.add(nation);
        updateDB();
        return EResponse.OK;
    }

    @Override
    public int getBalance() throws UserDoesNotExistException {
        return getOwner().getBalance();
    }

    @Override
    public BankResponse takeMoney(int amount) {
        return getOwner().takeMoney(amount);
    }

    @Override
    public void giveMoney(int amount) {
        getOwner().giveMoney(amount);
    }

    public Citizen getOwner() {
        for (Citizen citizen : Citizen.getCitizens()) {
            if (citizen.getOwnedTown().getOwnedNation() == this) return citizen;
        }
        return null;
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
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("displayName", displayName);

        return map;
    }

    public static Nation deserialize(Map<String, Object> map) {
        Nation nation = new Nation();
        nation.id = (String) map.get("id");
        nation.displayName = (String) map.get("displayName");

        return nation;
    }
}
