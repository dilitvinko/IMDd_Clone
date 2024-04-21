package com.my.project.imdd_clone.repository;

import com.my.project.imdd_clone.model.Film;
import com.my.project.imdd_clone.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("Select avg(r.points) FROM Rating r where r.film.id = :filmId")
    Double getFilmAverageRating(@Param("filmId") Long filmId);

    Optional<Integer> countRatingsByFilm(Film film);
}
