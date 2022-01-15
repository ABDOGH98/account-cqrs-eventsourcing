package axoncqrs.commonApi.commands;

import lombok.Getter;

public class DebitAccountCommand extends BaseCommand<String>{

    @Getter private double amount;
    @Getter private String currency;

    public DebitAccountCommand(String id, double debitAmount, String currency) {
        super(id);
        this.amount = debitAmount;
        this.currency = currency;
    }
}
