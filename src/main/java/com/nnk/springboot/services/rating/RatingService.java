package com.nnk.springboot.services.rating;

import com.nnk.springboot.domain.Rating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RatingService {

    List<Rating> getRatings();

    Rating getRating(Integer id);

    Rating save(Rating rating);

    Rating update(Rating rating);

    boolean delete(Rating rating);
}
