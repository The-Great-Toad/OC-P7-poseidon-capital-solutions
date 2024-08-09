package com.nnk.springboot.services.trade;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TradeServiceImpl implements TradeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TradeServiceImpl.class);
    private static final String LOG_ID = "[TradeService]";

    private final TradeRepository tradeRepository;

    public TradeServiceImpl(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Override
    public List<Trade> getTrades() {
        return tradeRepository.findAll();
    }

    @Override
    public Trade getTrade(Integer id) {
        Optional<Trade> optionalTrade = tradeRepository.findById(id);

        if (optionalTrade.isPresent()) {
            LOGGER.info("{} - Retrieved trade: {}", LOG_ID, optionalTrade.get());
            return optionalTrade.get();
        }

        LOGGER.error("{} - Trade with ID: {}, not found", LOG_ID, id);
        return null;
    }

    @Override
    public Trade save(Trade trade) {
        LOGGER.info("{} - Creating trade: {}", LOG_ID, trade);
        return tradeRepository.save(trade);
    }

    @Override
    public Trade update(Trade trade) {
        LOGGER.info("{} - Updating trade: {}", LOG_ID, trade);
        return tradeRepository.save(trade);
    }

    @Override
    public boolean delete(Trade trade) {
        tradeRepository.delete(trade);
        LOGGER.info("{} - Deleted trade: {}", LOG_ID, trade);
        return true;
    }
}
