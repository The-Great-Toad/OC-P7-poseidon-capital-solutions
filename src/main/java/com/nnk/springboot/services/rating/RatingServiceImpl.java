package com.nnk.springboot.services.rating;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RatingServiceImpl.class);
    private static final String LOG_ID = "[RatingService]";

    private final RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public List<Rating> getRatings() {
        return ratingRepository.findAll();
    }

    @Override
    public Rating getRating(Integer id) {
        Optional<Rating> optionalRating = ratingRepository.findById(id);

        if (optionalRating.isPresent()) {
            LOGGER.info("{} - Retrieved rating: {}", LOG_ID, optionalRating.get());
            return optionalRating.get();
        }

        LOGGER.error("{} - Rating with ID: {}, not found", LOG_ID, id);
        return null;
    }

    @Override
    public Rating save(Rating rating) {
        LOGGER.info("{} - Creating rating: {}", LOG_ID, rating);
        return ratingRepository.save(rating);
    }

    @Override
    public Rating update(Rating rating) {
        LOGGER.info("{} - Updating rating: {}", LOG_ID, rating);
        return ratingRepository.save(rating);
    }

    @Override
    public boolean delete(Rating rating) {
        ratingRepository.delete(rating);
        LOGGER.info("{} - Deleted rating: {}", LOG_ID, rating);
        return true;
    }
}
