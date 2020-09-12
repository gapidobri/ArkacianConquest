package ga.arkacia.conquest.objects;

import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import ga.arkacia.conquest.objects.bank.BankResponse;
import ga.arkacia.conquest.objects.chunk.ClaimedChunk;
import ga.arkacia.conquest.objects.chunk.IChunkOwner;
import ga.arkacia.conquest.objects.nation.INationOwner;
import ga.arkacia.conquest.objects.nation.Nation;
import ga.arkacia.conquest.objects.town.ITownOwner;
import ga.arkacia.conquest.objects.town.Town;
import net.ess3.api.Economy;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Citizen implements INationOwner, ITownOwner, IChunkOwner {
    static List<Citizen> citizens = new ArrayList<>();

    OfflinePlayer offlinePlayer;
    List<ClaimedChunk> ownedLand = new ArrayList<>();
    List<Nation> ownedNations = new ArrayList<>();
    List<Town> ownedTowns = new ArrayList<>();

    public Citizen (OfflinePlayer player) {
        this.offlinePlayer = player;
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
    public void ownTown(Town town) {
        town.setOwner(this);
        ownedTowns.add(town);
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

    public List<ClaimedChunk> getOwnedLand() {
        return ownedLand;
    }

    public List<Nation> getOwnedNations() {
        return ownedNations;
    }

    public List<Town> getOwnedTowns() {
        return ownedTowns;
    }

    public int getBalance() throws UserDoesNotExistException {
        return Economy.getMoneyExact(offlinePlayer.getName()).intValue();
    }

    @Override
    public BankResponse pay(int amount) {
        try {
            Economy.substract(offlinePlayer.getName(), BigDecimal.valueOf(amount));
            return BankResponse.OK;
        } catch (UserDoesNotExistException | NoLoanPermittedException e) {
            e.printStackTrace();
            return BankResponse.BALANCE;
        }
    }

    public static CreationResponse addCitizen(OfflinePlayer player) {
        for (Citizen citizen : citizens) {
            if (citizen.getOfflinePlayer() == player) return CreationResponse.EXISTS;
        }
        citizens.add(new Citizen(player));
        return CreationResponse.OK;
    }

    public static Citizen getCitizen(OfflinePlayer player) {
        for (Citizen citizen : citizens) {
            if (citizen.getOfflinePlayer() == player) return citizen;
        }
        Citizen citizen = new Citizen(player);
        citizens.add(citizen);
        return citizen;
    }
}
