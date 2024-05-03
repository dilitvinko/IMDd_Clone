package com.my.project.imdd_clone.controller.response;

import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse<T> {

    private OffsetDateTime timestamp = OffsetDateTime.now();
    private String message;
    private T data;

}
