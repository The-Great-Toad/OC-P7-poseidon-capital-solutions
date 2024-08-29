package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.data.jpa.repository.JpaRepository;

class RatingServiceTest extends AbstractEntityServiceTest<Rating> {

    @BeforeEach
    void setUp() {
        this.setService(createService(repository));
    }

    @Override
    protected AbstractEntityService<Rating> createService(JpaRepository<Rating, Integer> repository) {
        return new RatingService(repository);
    }

    @Override
    protected Rating createEntity() {
        return Rating.builder()
                .moodysRating(1)
                .sandPRating(1)
                .fitchRating(1)
                .orderNumber(1)
                .build();
    }
}