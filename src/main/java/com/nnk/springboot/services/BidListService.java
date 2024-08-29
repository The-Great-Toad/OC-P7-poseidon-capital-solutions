package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class BidListService extends AbstractEntityService<BidList> {

    public BidListService(JpaRepository<BidList, Integer> repository) {
        super("[BidListService]", repository);
    }

}
