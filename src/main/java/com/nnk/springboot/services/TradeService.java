package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class TradeService extends AbstractEntityService<Trade> {

    public TradeService(JpaRepository<Trade, Integer> abstractEntityRepository) {
        super("TradeService", abstractEntityRepository);
    }
}
