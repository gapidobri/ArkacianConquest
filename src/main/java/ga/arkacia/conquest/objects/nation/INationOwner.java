package ga.arkacia.conquest.objects.nation;

import com.earth2me.essentials.api.UserDoesNotExistException;
import ga.arkacia.conquest.objects.bank.BankResponse;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public interface INationOwner extends ConfigurationSerializable {
    void setNationOwner(Nation nation);
    int getBalance() throws UserDoesNotExistException;
    BankResponse takeMoney(int amount);
    void giveMoney(int amount);
    //void setFromID(String id);
}
