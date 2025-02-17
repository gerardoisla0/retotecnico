package com.seek.retotecnico.mysql.mapper;

import com.seek.retotecnico.mysql.model.CustomerData;
import com.seek.retotecnico.model.customer.Customer;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SecurityMapperTest {

    CustomerMapper mapper = CustomerMapper.MAPPER;

    @Test
    void shouldMapClientDomainToClientData() {
        Customer cliente = com.seek.retotecnico.model.customer.Customer
                .builder()
                .name("Julio")
                .lastName("Isla")
                .build();

        CustomerData data = mapper.domainToData(cliente);

        assertEquals("Julio", data.getName());
        assertEquals("Isla", data.getLastName());
    }

    @Test
    void shouldGetErrorWithNullSecurity() {

        CustomerData data = mapper.domainToData(null);

        assertNull(data);
    }


}
