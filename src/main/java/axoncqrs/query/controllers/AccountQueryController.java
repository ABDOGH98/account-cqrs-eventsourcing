package axoncqrs.query.controllers;

import axoncqrs.commonApi.queries.GetAccountByIdQuery;
import axoncqrs.commonApi.queries.GetAllAccountQuery;
import axoncqrs.query.entities.Account;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/query/accounts")
@AllArgsConstructor
@NoArgsConstructor
public class AccountQueryController {
    private QueryGateway queryGateway ;

    @GetMapping("/allaccounts")
    public List<Account> accountList(){
        List<Account> response =queryGateway.query(new GetAllAccountQuery(), ResponseTypes.multipleInstancesOf(Account.class)).join();
        return response;
    }

    @GetMapping("/byid/{id}")
    public Account accountList(@PathVariable String id){
        Account response =queryGateway.query(new GetAccountByIdQuery(id), ResponseTypes.instanceOf(Account.class)).join();
        return response;
    }
}
