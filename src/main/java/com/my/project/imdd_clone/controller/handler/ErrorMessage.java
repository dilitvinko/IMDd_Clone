package com.my.project.imdd_clone.controller.handler;

public record ErrorMessage (int statusCode, String message, String stacktrace) {
}
