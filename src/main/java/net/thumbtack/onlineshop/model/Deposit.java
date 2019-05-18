package net.thumbtack.onlineshop.model;

import java.util.Objects;

public class Deposit {

    private int clientId;
    private int amount;

    public Deposit() {
    }

    public Deposit(int id, int amount) {
        this.clientId = id;
        this.amount = amount;
    }
    public Deposit(int amount) {
        this.clientId = 0;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deposit deposit = (Deposit) o;
        return clientId == deposit.clientId &&
                amount == deposit.amount;
    }

    @Override
    public int hashCode() {

        return Objects.hash(clientId, amount);
    }

    public int getDeposit() {
        return amount;
    }

}
