package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bid_list")
public class BidList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    @NotBlank(message = "Account is mandatory")
    @NotNull(message = "Account is mandatory")
    private String account;

    @Column
    @NotBlank(message = "Type is mandatory")
    @NotNull(message = "Type is mandatory")
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
