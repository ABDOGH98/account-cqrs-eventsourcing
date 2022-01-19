package axoncqrs.query.services;

import axoncqrs.commonApi.enums.OperationType;
import axoncqrs.commonApi.events.AccountActivatedEvent;
import axoncqrs.commonApi.events.AccountCreatedEvent;
import axoncqrs.commonApi.events.AccountCreditedEvent;
import axoncqrs.commonApi.events.AccountDebitEvent;
import axoncqrs.commonApi.queries.GetAccountByIdQuery;
import axoncqrs.commonApi.queries.GetAllAccountQuery;
import axoncqrs.query.entities.Account;
import axoncqrs.query.entities.Operation;
import axoncqrs.query.repositories.AccountRepository;
import axoncqrs.query.repositories.OperationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class AccountServiceHandler {

    private AccountRepository accountRepository ;
    private OperationRepository operationRepository;

    @EventHandler
    public void on(AccountCreatedEvent event){
        log.info("********************");
        log.info("AccountCreatedEvent received");

        Account account = new Account();
        account.setId(event.getId());
        account.setBalance(event.getInitialBalance());
        account.setCurrency(event.getCurrency());
        account.setAccountStatus(event.getStatus());

        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountActivatedEvent event){
        log.info("***************************");
        log.info("AccountActivatedEvent received");

        Account account = accountRepository.findById(event.getId()).get();
        account.setAccountStatus(event.getStatus());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountDebitEvent event){
        log.info("***************************");
        log.info("AccountDebitEvent received");
        Account account = accountRepository.findById(event.getId()).get();
        Operation operation = new Operation();
        operation.setAmount(event.getAmount());
        operation.setDate(new Date());
        operation.setOperationType(OperationType.DEBIT);
        operation.setAccount(account);
        account.setBalance(account.getBalance()-event.getAmount());

        operationRepository.save(operation);
    }

    @EventHandler
    public void on(AccountCreditedEvent event){
        log.info("***************************");
        log.info("AccountCreditedEvent received");
        Account account = accountRepository.findById(event.getId()).get();
        Operation operation = new Operation();
        operation.setAmount(event.getAmount());
        operation.setDate(new Date());
        operation.setOperationType(OperationType.CREDIT);
        operation.setAccount(account);
        account.setBalance(account.getBalance()+event.getAmount());

        operationRepository.save(operation);
    }

    @QueryHandler
    public List<Account> on(GetAllAccountQuery query){
        return accountRepository.findAll();
    }

    @QueryHandler
    public Account on(GetAccountByIdQuery query){
        return accountRepository.findById(query.getId()).get();
    }
}
