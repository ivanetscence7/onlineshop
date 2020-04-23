package net.thumbtack.onlineshop.dto.request;

import javax.validation.constraints.PositiveOrZero;

public class DepositDtoRequest {

    @PositiveOrZero(message = "{user.deposit.PositiveOrZero.message}")
    private int deposit;

    public DepositDtoRequest() {
    }

    public DepositDtoRequest(int deposit) {
        this.deposit = deposit;
    }

    public int getDeposit() {
        return deposit;
    }

}
