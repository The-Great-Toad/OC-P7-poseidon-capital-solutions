package com.nnk.springboot.services.bidList;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BidListServiceImplTest {

    @InjectMocks
    private BidListServiceImpl service;

    @Mock
    private BidListRepository repository;

    @Test
    void getBids() {
        BidList bidList = createBidList();

        when(repository.findAll()).thenReturn(List.of(bidList));

        List<BidList> result = service.getBids();

        assertThat(result)
                .isNotNull()
                .isEqualTo(List.of(bidList));
    }

    @Test
    void getBid_success() {
        BidList bidList = createBidList();

        when(repository.findById(anyInt()))
                .thenReturn(Optional.of(bidList));

        BidList result = service.getBid(2);

        assertThat(result)
                .isNotNull()
                .isEqualTo(bidList);
    }

    @Test
    void getBid_failure() {
        when(repository.findById(anyInt()))
                .thenReturn(Optional.empty());

        BidList result = service.getBid(2);

        assertNull(result);
    }

    @Test
    void save() {
        BidList bidList = createBidList();
        bidList.setId(1);

        when(repository.save(any(BidList.class)))
                .thenReturn(bidList);

        BidList result = service.save(createBidList());

        assertThat(result)
                .isNotNull()
                .isEqualTo(bidList);
        assertThat(result.getId()).isEqualTo(1);
    }

    @Test
    void update() {
        BidList bidList = createBidList();
        bidList.setAccount("Account modified");

        when(repository.save(any(BidList.class)))
                .thenReturn(bidList);

        BidList result = service.update(createBidList());

        assertThat(result)
                .isNotNull()
                .isEqualTo(bidList);
        assertThat(result.getAccount())
                .isEqualTo(bidList.getAccount());
    }

    @Test
    void delete() {
        boolean result = service.delete(createBidList());
        assertTrue(result);
    }

    private BidList createBidList() {
        return BidList.builder()
                .account("bid account")
                .type("bid type")
                .bidQuantity(77d)
                .build();
    }
}