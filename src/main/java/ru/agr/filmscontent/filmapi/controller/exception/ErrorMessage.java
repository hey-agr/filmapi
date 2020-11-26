package ru.agr.filmscontent.filmapi.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ErrorMessage {
    private LocalDateTime timestamp;

    private int status;

    private String error;

    private String message;

    private String path;
}
