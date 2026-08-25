package com.vendi.unit.shared;

import com.vendi.shared.exception.GlobalExceptionHandler;
import com.vendi.shared.exception.InsufficientStockException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void unexpectedExceptionsBecomeInternalServerErrorWithoutLeakingTheMessage() {
        ResponseEntity<String> response = handler.handleException(new RuntimeException("secret internals"));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred.", response.getBody());
    }

    @Test
    void insufficientStockBecomesConflict() {
        ResponseEntity<String> response = handler.handleInsufficientStock(new InsufficientStockException("Not enough stock for this product."));

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Not enough stock for this product.", response.getBody());
    }
}
