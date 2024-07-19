package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trade")
public class Trade {
    // TODO: Map columns in data table TRADE with corresponding java fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer tradeId;
    @Column
    private String account;
    @Column
    private String type;
    @Column
    private Double buyQuantity;
    @Column
    private Double sellQuantity;
    @Column
    private Double buyPrice;
    @Column
    private Double sellPrice;
    @Column
    private String benchmark;
    @Column
    private Timestamp tradeDate;
    @Column
    private String security;
    @Column
    private String status;
    @Column
    private String trader;
    @Column
    private String book;
    @Column
    private String creationName;
    @Column
    private Timestamp creationDate;
    @Column
    private String revisionName;
    @Column
    private Timestamp revisionDate;
    @Column
    private String dealName;
    @Column
    private String dealType;
    @Column
    private String sourceListId;
    @Column
    private String side;

}
