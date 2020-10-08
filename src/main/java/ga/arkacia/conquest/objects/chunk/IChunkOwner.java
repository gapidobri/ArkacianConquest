package ga.arkacia.conquest.objects.chunk;

import com.earth2me.essentials.api.UserDoesNotExistException;
import ga.arkacia.conquest.objects.bank.BankResponse;
import org.bukkit.Chunk;

public interface IChunkOwner {
    String getDisplayName();
    int getBalance() throws UserDoesNotExistException;
    BankResponse takeMoney(int amount);
    void giveMoney(int amount);
    void claimChunk(Chunk chunk);
    // void unclaimChunk(Chunk chunk);
}
