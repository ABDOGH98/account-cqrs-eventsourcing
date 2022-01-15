package axoncqrs.commonApi.events;

import lombok.Getter;

public class AccountDebitEvent extends BaseEvent<String>{

    @Getter private double amount;
    @Getter private String currency;


    public AccountDebitEvent(String id, double amount, String currency) {
        super(id);
        this.amount = amount;
        this.currency = currency;
    }
}
