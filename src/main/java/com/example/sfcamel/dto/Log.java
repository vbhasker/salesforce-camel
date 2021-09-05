package com.example.sfcamel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Log {
    private String quiddity;
    private String requestId;
    private String message;
    private String severity;
}
