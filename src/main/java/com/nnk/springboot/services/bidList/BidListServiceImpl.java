package com.nnk.springboot.services.bidList;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidListServiceImpl implements BidListService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BidListServiceImpl.class);
    private static final String LOG_ID = "[BidListService]";

    private final BidListRepository bidListRepository;

    public BidListServiceImpl(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    @Override
    public List<BidList> getBids() {
        List<BidList> bids = bidListRepository.findAll();
        LOGGER.info("{} - Retrieved {} bid(s)", LOG_ID, bids.size());

        return bids;
    }

    @Override
    public BidList getBid(Integer id) {
        Optional<BidList> bidList = bidListRepository.findById(id);

        if (bidList.isPresent()) {
            LOGGER.info("{} - Retrieved bid: {}", LOG_ID, bidList.get());
            return bidList.get();
        }

        LOGGER.error("{} - No such bid: {}", LOG_ID, id);
        return null;
    }

    @Override
    public BidList save(BidList bidList) {
        LOGGER.info("{} - Creating BidList: {}", LOG_ID, bidList);
        return bidListRepository.save(bidList);
    }

    @Override
    public BidList update(BidList bidList) {
        LOGGER.info("{} - Updating BidList: {}", LOG_ID, bidList);
        return bidListRepository.save(bidList);
    }

    @Override
    public boolean delete(BidList bidList) {
        bidListRepository.delete(bidList);
        LOGGER.info("{} - Deleted BidList: {}", LOG_ID, bidList);
        return true;
    }

}
