package com.seek.retotecnico.mysql.mapper;

import com.seek.retotecnico.model.user.User;
import com.seek.retotecnico.mysql.model.UserData;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SecurityMapperTest {

    UserMapper mapper = UserMapper.MAPPER;

    @Test
    void shouldMapClientDomainToClientData() {
        User cliente = User
                .builder()
                .name("Julio")
                .lastName("Isla")
                .build();

        UserData data = mapper.domainToData(cliente);

        assertEquals("Julio", data.getName());
        assertEquals("Isla", data.getLastName());
    }

    @Test
    void shouldGetErrorWithNullSecurity() {

        UserData data = mapper.domainToData(null);

        assertNull(data);
    }


}
