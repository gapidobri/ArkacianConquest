package ga.arkacia.conquest.objects.nation;

import com.earth2me.essentials.api.UserDoesNotExistException;
import ga.arkacia.conquest.objects.Citizen;
import ga.arkacia.conquest.objects.CreationResponse;
import ga.arkacia.conquest.objects.bank.BankResponse;
import ga.arkacia.conquest.objects.chunk.ClaimedChunk;
import ga.arkacia.conquest.objects.chunk.IChunkOwner;
import ga.arkacia.conquest.objects.town.ITownOwner;
import ga.arkacia.conquest.objects.town.Town;
import org.bukkit.Chunk;

import java.util.ArrayList;
import java.util.List;

import static ga.arkacia.conquest.ArkacianConquest.nationPrice;
import static ga.arkacia.conquest.Functions.idGen;

public class Nation implements INationOwner, ITownOwner, IChunkOwner {
    static List<Nation> nations = new ArrayList<>();

    String id; // All lowercase name of the nation
    String displayName; // Pretty name of the nation
    INationOwner owner;
    List<ClaimedChunk> ownedLand = new ArrayList<>(); // Chunks that are owned by the nation
    List<Nation> ownedNations = new ArrayList<>();
    List<Town> ownedTowns = new ArrayList<>();
    List<Citizen> citizens = new ArrayList<>();
    List<Town> towns = new ArrayList<>();

    public Nation(String displayName) {
        this(displayName, null);
    }

    public Nation(String displayName, INationOwner owner) {
        this.displayName = displayName;
        id = idGen(displayName);
        this.owner = owner;
    }

    @Override
    public void claimChunk(Chunk chunk) {
        ownedLand.add(new ClaimedChunk(chunk, this));
    }

    @Override
    public void ownNation(Nation nation) {
        nation.setOwner(this);
        ownedNations.add(nation);
    }

    @Override
    public int getBalance() throws UserDoesNotExistException {
        // TODO: Nation banks?
        return getOwner().getBalance();
    }

    @Override
    public BankResponse pay(int amount) {
        return getOwner().pay(amount);
    }

    public INationOwner getOwner() {
        return owner;
    }

    @Override
    public void ownTown(Town town) {
        town.setOwner(this);
        ownedTowns.add(town);
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

    public void setOwner(INationOwner owner) {
        this.owner = owner;
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

    public static CreationResponse addNation(Nation nation) {
        for (Nation n : nations) {
            if (n.getId().equals(nation.getId())) return CreationResponse.EXISTS;
        }

        try {
            if (nation.getOwner().getBalance() < nationPrice) return CreationResponse.BALANCE;
        } catch (UserDoesNotExistException e) {
            e.printStackTrace();
        }

        nation.getOwner().pay(nationPrice);

        nations.add(nation);
        return CreationResponse.OK;
    }
}
