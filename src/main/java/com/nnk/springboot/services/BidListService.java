package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;

import java.util.List;

public interface BidListService {

    List<BidList> getBids();

    BidList getBid(Integer id);

    BidList save(BidList bidList);

    BidList update(BidList bidList);

    boolean delete(BidList bidList);
}
