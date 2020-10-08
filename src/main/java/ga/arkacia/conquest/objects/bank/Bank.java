package ga.arkacia.conquest.objects.bank;

import ga.arkacia.conquest.objects.EResponse;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static ga.arkacia.conquest.Config.lang;

public class Bank implements ConfigurationSerializable {
    String id;
    int balance = 0;
    int minBalance = 0;

    public Bank(String id) {
        this.id = id;
    }

    public static Bank deserialize(Map<String, Object> map) {
        Bank bank = new Bank((String) map.get("id"));
        bank.balance = (int) map.get("balance");
        bank.minBalance = (int) map.get("minBalance");
        return bank;
    }

    public String getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public String getBalanceString() {
        if (balance == 1) return balance + " " + lang("currency.singular");
        return balance + " " + lang("currency.plural");
    }

    public EResponse rename(String id) {
        this.id = id;
        return EResponse.OK;
    }

    public BankResponse withdraw(int amount) {
        if (balance - amount >= minBalance) {
            balance -= amount;
            return BankResponse.OK;
        }
        return BankResponse.BALANCE;
    }

    public BankResponse deposit(int amount) {
        balance += amount;
        return BankResponse.OK;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("balance", balance);
        map.put("minBalance", minBalance);

        return map;
    }
}
