package org.inventory.management.server.model.errror;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ErrorRes {
    private final String code;
    private final String message;
}

