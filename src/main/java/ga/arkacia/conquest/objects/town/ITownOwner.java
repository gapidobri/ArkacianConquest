package ga.arkacia.conquest.objects.town;

import com.earth2me.essentials.api.UserDoesNotExistException;
import ga.arkacia.conquest.objects.bank.BankResponse;

public interface ITownOwner {
    void ownTown(Town town);
    int getBalance() throws UserDoesNotExistException;
    BankResponse pay(int amount);
}
