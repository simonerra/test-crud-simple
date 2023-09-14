package test.controller;

import data.DataTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import test.entity.UsuarioEntity;
import test.repository.UsuarioRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class UsuarioControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @LocalServerPort
    private int port;

    @Test
    @DirtiesContext
    void findById() {

        //GIVEN
        UsuarioEntity usuarioEntity01 = DataTest.usuarioEntity01;

        //WHEN
        ResponseEntity<UsuarioEntity> responseEntity = testRestTemplate.getForEntity(
                "http://localhost:" + port + "/findById/" + usuarioEntity01.getId(),
                UsuarioEntity.class
        );

        //THEN
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Emilia", responseEntity.getBody().getNombre());
        assertEquals("Peralta", responseEntity.getBody().getApellido());

    }

    @Test
    @DirtiesContext
    void findAll() {

        //WHEN
        ResponseEntity<List<UsuarioEntity>> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/findAll",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        //THEN
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<UsuarioEntity> usuarioEntityList = responseEntity.getBody();
        assertNotNull(usuarioEntityList);
        usuarioEntityList.forEach(usuario -> {
            assertNotNull(usuario.getNombre());
            assertNotNull(usuario.getApellido());
        });

    }

    @Test
    @DirtiesContext
    void save() {

        //GIVEN
        UsuarioEntity usuarioEntity03 = DataTest.usuarioEntity03;
        HttpEntity<UsuarioEntity> requestEntity = new HttpEntity<>(usuarioEntity03);

        //WHEN
        ResponseEntity<UsuarioEntity> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/save",
                HttpMethod.POST,
                requestEntity,
                UsuarioEntity.class
        );

        //THEN
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Simón", responseEntity.getBody().getNombre());

    }

    @Test
    @DirtiesContext
    void updateById() {

        //GIVEN
        UsuarioEntity usuarioEntity01 = DataTest.usuarioEntity01;
        usuarioEntity01.setNombre("Emilia");
        HttpEntity<UsuarioEntity> requestEntity = new HttpEntity<>(usuarioEntity01);

        //WHEN
        ResponseEntity<UsuarioEntity> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/updateById/" + usuarioEntity01.getId(),
                HttpMethod.PUT,
                requestEntity,
                UsuarioEntity.class
        );

        //THEN
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Emilia", responseEntity.getBody().getNombre());

    }

    @Test
    @DirtiesContext
    void deleteById() {

        //GIVEN
        UsuarioEntity usuarioEntity01 = DataTest.usuarioEntity01;

        //WHEN
        testRestTemplate.delete(
                "http://localhost:" + port + "/deleteById/" + usuarioEntity01.getId()
        );

        //THEN
        assertFalse(usuarioRepository.existsById(usuarioEntity01.getId()));

    }

    @Test
    @DirtiesContext
    void deleteAll() {

        //WHEN
        testRestTemplate.delete(
                "http://localhost:" + port + "/deleteAll/"
        );

        //THEN
        assertEquals(Collections.emptyList(), usuarioRepository.findAll());

    }
    
}