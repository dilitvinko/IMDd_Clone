package com.my.project.imdd_clone.controller.response;

import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@Builder
public class APIResponse<T> {

    private final OffsetDateTime timestamp = OffsetDateTime.now();
    private final String message;
    private final T data;

}
