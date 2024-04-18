package com.tipico.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "condition")
@Setter
@Getter
public class Condition extends AbstractEntity<Long>{
    @Id
    private Long id;

    @Column(length = 100)
    private String name;

    @Column(length = 4000)
    private String expression;

    @ManyToOne
    @JoinColumn(name = "campaign_id", referencedColumnName = "id")
    private Campaign campaign;
}
