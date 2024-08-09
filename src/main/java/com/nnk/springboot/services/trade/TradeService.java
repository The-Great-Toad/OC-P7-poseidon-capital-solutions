package com.nnk.springboot.services.trade;

import com.nnk.springboot.domain.Trade;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TradeService {

    List<Trade> getTrades();

    Trade getTrade(Integer id);

    Trade save(Trade trade);

    Trade update(Trade trade);

    boolean delete(Trade trade);
}
