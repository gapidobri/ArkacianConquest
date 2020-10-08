package ga.arkacia.conquest.objects.town;

import com.earth2me.essentials.api.UserDoesNotExistException;
import ga.arkacia.conquest.objects.bank.BankResponse;

public interface ITownOwner {
    int getBalance() throws UserDoesNotExistException;
    BankResponse takeMoney(int amount);
    void giveMoney(int amount);
    String getDisplayName();
}
