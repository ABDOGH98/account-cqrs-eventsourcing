package axoncqrs.query.services;

import axoncqrs.commonApi.events.AccountCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceHandler {

    @EventHandler
    public void on(AccountCreatedEvent event){

    }
}
