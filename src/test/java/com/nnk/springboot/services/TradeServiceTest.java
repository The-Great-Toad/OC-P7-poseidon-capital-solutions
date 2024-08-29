package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.data.jpa.repository.JpaRepository;

class TradeServiceTest extends AbstractEntityServiceTest<Trade> {

    @BeforeEach
    void setUp() {
        this.setService(createService(repository));
    }

    @Override
    protected AbstractEntityService<Trade> createService(JpaRepository<Trade, Integer> repository) {
        return new TradeService(repository);
    }

    @Override
    protected Trade createEntity() {
        return Trade.builder()
                .account("account")
                .type("type")
                .buyQuantity(10d)
                .build();
    }
}