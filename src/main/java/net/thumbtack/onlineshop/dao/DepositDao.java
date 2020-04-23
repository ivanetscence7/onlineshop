package net.thumbtack.onlineshop.dao;

import net.thumbtack.onlineshop.model.Deposit;

public interface DepositDao {
    Deposit putDeposit(int id, Deposit deposit);
}
