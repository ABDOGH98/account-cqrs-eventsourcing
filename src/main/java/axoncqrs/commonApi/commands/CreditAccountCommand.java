package axoncqrs.commonApi.commands;

import lombok.Getter;

public class CreditAccountCommand extends BaseCommand<String>{

    @Getter private double amount;
    @Getter private String currency;

    public CreditAccountCommand(String id, double creditAmount, String currency) {
        super(id);
        this.amount = creditAmount;
        this.currency = currency;
    }
}
