package com.my.project.imdd_clone.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "film_seq")
    @SequenceGenerator(name = "film_seq", allocationSize = 1)
    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "name", nullable = false)
    private String name;
    @Basic
    @Column(name = "release_date")
    private Date releaseDate;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "average_rating")
    private Double averageRating;
    @OneToMany(mappedBy = "film")
    private List<Comment> comments;
    @OneToMany(mappedBy = "film")
    private List<Rating> ratings;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return id == film.id && Objects.equals(name, film.name) && Objects.equals(releaseDate, film.releaseDate) && Objects.equals(description, film.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, releaseDate, description);
    }
}
