package com.seek.retotecnico.model.enums;

import com.seek.retotecnico.model.util.enums.Operation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OperationTest {

    @Test
    void shouldReturnNameOperationOnOperation() {

        assertEquals("createCustomer", Operation.CREATE_CUSTOMER.getName());
        assertEquals("generateToken", Operation.GENERATE_TOKEN.getName());
        assertEquals("getCustomMetrics", Operation.GET_CUSTOM_METRICS.getName());
        assertEquals("listCustomers", Operation.LIST_CUSTOMER.getName());

    }

    @Test
    void shouldReturnSuccessOnFindByNameWheOperationIsCreateUser() {

        String expected = "createCustomer";
        Operation actual = Operation.findByName(expected);

        assertNotNull(actual);
        assertEquals(expected, actual.getName());
    }

    @Test
    void shouldReturnIllegalArgumentExceptionOnFindByNameWhenOperationNotFound() {

        String operation = "create User Error";

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> Operation.findByName(operation)
        );

        assertNotNull(exception);
        assertEquals(IllegalArgumentException.class.getSimpleName(), exception.getClass().getSimpleName());
        assertEquals(String.format("La operación %s no se encuentra registrada", operation), exception.getMessage());
    }
}
