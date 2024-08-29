package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class RatingService extends AbstractEntityService<Rating> {


    public RatingService(JpaRepository<Rating, Integer> abstractEntityRepository) {
        super("RatingService", abstractEntityRepository);
    }
}
