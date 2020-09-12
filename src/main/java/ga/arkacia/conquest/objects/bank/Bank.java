package ga.arkacia.conquest.objects.bank;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    static List<Bank> banks = new ArrayList<>();

    String id;
    int balance = 0;
    int minBalance = 0;

    public Bank(String id) {
        this.id = id;
    }

    public int getBalance() {
        return balance;
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
}
