package axoncqrs.query.entities;

import axoncqrs.commonApi.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
public class Account {
    @Id
    private String id;
    private double balance;
    private AccountStatus accountStatus;
    private String currency;
    @OneToMany(mappedBy = "account")
    private Collection<Operation> operations ;
}
