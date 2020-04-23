package net.thumbtack.onlineshop.model;

import java.util.Objects;

public class Deposit {

    private int id;
    private int amount;
    private Integer version;

    public Deposit() {
    }

    public Deposit(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public Deposit(int id, int amount, Integer version) {
        this(id, amount);
        this.version = version;
    }

    public Deposit(int amount) {
        this.id = 0;
        this.amount = amount;
    }

    public Deposit(int amount, Integer version) {
        this.amount = amount;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deposit deposit = (Deposit) o;
        return getId() == deposit.getId() &&
                getAmount() == deposit.getAmount() &&
                Objects.equals(getVersion(), deposit.getVersion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAmount(), getVersion());
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "clientId=" + id +
                ", amount=" + amount +
                ", version=" + version +
                '}';
    }
}
