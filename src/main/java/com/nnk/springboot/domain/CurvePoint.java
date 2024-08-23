package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
@Table(name = "curvepoint")
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    @NotNull(message = "must not be null")
    @Digits(integer = 8, fraction = 0)
    private Integer curveId;

    @Column
    private Timestamp asOfDate;

    @Column
    @Min(value = 0L, message = "must be a positive numeric value")
//    @Digits(integer = 8, fraction = 4)
    private Double term;

    @Column
    @Min(value = 0L, message = "must be a positive numeric value")
//    @Digits(integer = 8, fraction = 4)
    private Double value;

    @Column
    private Timestamp creationDate;

}
