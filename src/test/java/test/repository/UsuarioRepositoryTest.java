package test.repository;

import data.DataTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import test.entity.UsuarioEntity;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Test
    void findById() {

        //GIVEN
        UsuarioEntity usuarioEntity01 = DataTest.usuarioEntity01;
        UsuarioEntity usuarioEntity02 = DataTest.usuarioEntity02;

        //WHEN
        Optional<UsuarioEntity> usuario01 = usuarioRepository.findById(usuarioEntity01.getId());
        Optional<UsuarioEntity> usuario02 = usuarioRepository.findById(usuarioEntity02.getId());

        //THEN
        //usuario01
        assertTrue(usuario01.isPresent());
        assertEquals("Emilia", usuario01.get().getNombre());
        assertEquals("Peralta", usuario01.get().getApellido());
        //usuario02
        assertTrue(usuario02.isPresent());
        assertEquals("Isidora", usuario02.get().getNombre());
        assertEquals("Peralta", usuario02.get().getApellido());

    }

    @Test
    void findByIdNotFound() {

        //GIVEN
        Long nonExistentId = 999L;

        //WHEN
        Optional<UsuarioEntity> usuario = usuarioRepository.findById(nonExistentId);

        //THEN
        assertTrue(usuario.isEmpty());

    }

    @Test
    void save() {

        //GIVEN
        UsuarioEntity usuarioEntity01 = DataTest.usuarioEntity01;

        //WHEN
        UsuarioEntity usuarioEntity01Saved = usuarioRepository.save(usuarioEntity01);

        //THEN
        assertNotNull(usuarioEntity01Saved);
        assertEquals("Emilia", usuarioEntity01Saved.getNombre());
        assertEquals("Peralta", usuarioEntity01Saved.getApellido());

    }

    @Test
    void deleteById() {

        //GIVEN
        UsuarioEntity usuarioEntity01 = DataTest.usuarioEntity01;

        //WHEN
        usuarioRepository.deleteById(usuarioEntity01.getId());

        //THEN
        assertFalse(usuarioRepository.existsById(usuarioEntity01.getId()));

    }

    @Test
    void deleteAll() {

        //WHEN
        usuarioRepository.deleteAll();

        //THEN
        assertEquals(Collections.emptyList(), usuarioRepository.findAll());

    }

}