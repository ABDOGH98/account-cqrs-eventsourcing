package axoncqrs.query.entities;

import axoncqrs.commonApi.enums.OperationType;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Operation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private double amount;
    @Enumerated(EnumType.STRING)
    private OperationType operationType;
    @ManyToOne
    private Account account;
}
