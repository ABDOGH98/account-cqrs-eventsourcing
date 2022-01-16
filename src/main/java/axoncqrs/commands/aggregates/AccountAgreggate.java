package axoncqrs.commands.aggregates;

import axoncqrs.commonApi.commands.CreateAccountCommand;
import axoncqrs.commonApi.commands.CreditAccountCommand;
import axoncqrs.commonApi.commands.DebitAccountCommand;
import axoncqrs.commonApi.enums.AccountStatus;
import axoncqrs.commonApi.events.AccountActivatedEvent;
import axoncqrs.commonApi.events.AccountCreatedEvent;
import axoncqrs.commonApi.events.AccountCreditedEvent;
import axoncqrs.commonApi.events.AccountDebitEvent;
import axoncqrs.commonApi.exceptions.AmountNegativeException;
import axoncqrs.commonApi.exceptions.BalanceNotSufficientException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class AccountAgreggate {

    @AggregateIdentifier
    private String accountId;
    private double balance;
    private String currency;
    private AccountStatus status;

    public AccountAgreggate(){

    }

    @CommandHandler
    public AccountAgreggate(CreateAccountCommand createAccountCommand){
        if(createAccountCommand.getInitialBalance()<0)throw new RuntimeException("impossible");

        AggregateLifecycle.apply(new AccountCreatedEvent(
                createAccountCommand.getId(),
                createAccountCommand.getInitialBalance(),
                createAccountCommand.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        this.accountId = event.getId();
        this.balance = event.getInitialBalance();
        this.status = AccountStatus.CREATED;
        this.currency = event.getCurrency();

        AggregateLifecycle.apply(new AccountActivatedEvent(
                event.getId(),
                AccountStatus.ACTIVATED
        ));
    }

    @CommandHandler
    public void on(CreditAccountCommand command){
        if(command.getAmount()<0) throw new AmountNegativeException("Amount should not be negative");
        AggregateLifecycle.apply(new AccountCreditedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent event){
        this.balance += event.getAmount();
    }

    @CommandHandler
    public void on(DebitAccountCommand command){
        if(command.getAmount()<0) throw new AmountNegativeException("Amount should not be negative");

        if (this.balance<command.getAmount()) throw new BalanceNotSufficientException("Blanace Not Sufficient Exception");

        AggregateLifecycle.apply(new AccountDebitEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountDebitEvent event){
        this.balance -= event.getAmount();
    }

}
