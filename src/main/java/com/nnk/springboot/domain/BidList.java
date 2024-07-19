package com.nnk.springboot.domain;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "BidList")
public class BidList {
    // TODO: Map columns in data table BIDLIST with corresponding java fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer BidListId;
    @Column
    private String account;
    @Column
    private String type;
    @Column
    private Double bidQuantity;
    @Column
    private Double askQuantity;
    @Column
    private Double bid;
    @Column
    private Double ask;
    @Column
    private String benchmark;
    @Column
    private Timestamp bidListDate;
    @Column
    private String commentary;
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
