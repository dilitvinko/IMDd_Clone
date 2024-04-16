package com.my.project.imdd_clone.DTO;

import com.my.project.imdd_clone.model.Comment;
import com.my.project.imdd_clone.model.Rating;
import com.my.project.imdd_clone.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserDto {

    private long id;
    private String name;
    private String email;
    private String username;
    private String password;
//    private List<Comment> comments;
//    private List<Rating> ratings;
    private Set<RoleDto> roles;
}
