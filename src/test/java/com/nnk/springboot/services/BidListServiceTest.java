package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.data.jpa.repository.JpaRepository;


class BidListServiceTest extends AbstractEntityServiceTest<BidList> {

    @BeforeEach
    public void setUp() {
        // Initialisation du service avec le constructeur spécifique du service
        // permettant l'instanciation et l'affichage correct du paramètre LOG_ID.
        this.setService(createService(repository));
    }

    @Override
    protected AbstractEntityService<BidList> createService(JpaRepository<BidList, Integer> repository) {
        return new BidListService(repository);
    }

    @Override
    protected BidList createEntity() {
        return BidList.builder()
                .account("bid account")
                .type("bid type")
                .bidQuantity(77d)
                .build();
    }
}