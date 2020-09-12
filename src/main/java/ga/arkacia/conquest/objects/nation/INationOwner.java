package ga.arkacia.conquest.objects.nation;

import com.earth2me.essentials.api.UserDoesNotExistException;
import ga.arkacia.conquest.objects.bank.BankResponse;

public interface INationOwner {
    void ownNation(Nation nation);
    int getBalance() throws UserDoesNotExistException;
    BankResponse pay(int amount);
}
