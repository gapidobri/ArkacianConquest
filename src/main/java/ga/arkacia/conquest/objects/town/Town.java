package ga.arkacia.conquest.objects.town;

import com.earth2me.essentials.api.UserDoesNotExistException;
import ga.arkacia.conquest.objects.bank.Bank;
import ga.arkacia.conquest.objects.CreationResponse;
import ga.arkacia.conquest.objects.chunk.IChunkOwner;
import ga.arkacia.conquest.objects.chunk.ClaimedChunk;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;

import static ga.arkacia.conquest.ArkacianConquest.townPrice;
import static ga.arkacia.conquest.Functions.idGen;

public class Town implements IChunkOwner {
    static List<Town> towns = new ArrayList<>();

    String id;
    String displayName;
    ITownOwner owner;
    List<ClaimedChunk> ownedLand;
    Bank bank;
    List<OfflinePlayer> players;

    public Town(String displayName) {
        this(displayName, null);
    }

    public Town(String displayName, ITownOwner owner) {
        this.id = idGen(displayName);
        this.displayName = displayName;
        this.owner = owner;
        this.ownedLand = new ArrayList<>();
        this.bank = new Bank(id);
        this.players = new ArrayList<>();
    }

    @Override
    public void claimChunk(Chunk chunk) {
        ownedLand.add(new ClaimedChunk(chunk, this));
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
        this.displayName = displayName;
    }

    public ITownOwner getOwner() {
        return owner;
    }

    public void setOwner(ITownOwner owner) {
        this.owner = owner;
    }

    public Bank getBank() {
        return bank;
    }

    public static List<Town> getTowns() {
        return towns;
    }

    public static CreationResponse addTown(Town town) {
        for (Town t : towns) {
            if (t.getId().equals(town.getId())) {
                return CreationResponse.EXISTS;
            }
        }

        try {
            if (town.getOwner().getBalance() < townPrice) return CreationResponse.BALANCE;
        } catch (UserDoesNotExistException e) {
            e.printStackTrace();
        }

        town.getOwner().pay(townPrice);

        towns.add(town);
        return CreationResponse.OK;
    }
}
