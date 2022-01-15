package axoncqrs.commonApi.events;

import lombok.Getter;

public class AccountCreditedEvent extends BaseEvent<String>{
    @Getter private double amount ;
    @Getter private String current ;

    public AccountCreditedEvent(String id, double amount, String current) {
        super(id);
        this.amount = amount;
        this.current = current;
    }
}
