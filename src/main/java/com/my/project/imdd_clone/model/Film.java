package com.my.project.imdd_clone.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Film {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "release_date")
    private Date releaseDate;
    @Basic
    @Column(name = "description")
    private String description;
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
