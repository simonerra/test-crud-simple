package test.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import test.data.DataTest;
import test.dto.UsuarioDTO;
import test.entity.UsuarioEntity;
import test.repository.UsuarioRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServicesImplTest {

    @InjectMocks
    UsuarioServicesImpl usuarioServices;

    @Mock
    UsuarioRepository usuarioRepository;

    @Mock
    ModelMapper modelMapper;

    @Test
    void findById() {

        //GIVEN
        List<UsuarioEntity> dataTest = Arrays.asList(DataTest.usuarioEntity01, DataTest.usuarioEntity02);

        //WHEN
        when(usuarioRepository.findById(dataTest.get(0).getId())).thenReturn(dataTest.stream().findFirst());
        when(usuarioRepository.findById(dataTest.get(1).getId())).thenReturn(dataTest.stream()
                .filter(usuarioEntity -> usuarioEntity.getId().equals(2L))
                .findFirst()
        );

        //INVOKE METHODS
        UsuarioEntity usuarioEntity01 = usuarioServices.findById(dataTest.get(0).getId());
        UsuarioEntity usuarioEntity02 = usuarioServices.findById(dataTest.get(1).getId());

        //THEN
        //usuario01
        assertNotNull(usuarioEntity01);
        assertEquals("Emilia", usuarioEntity01.getNombre());
        assertEquals("Peralta", usuarioEntity01.getApellido());
        assertEquals(9, usuarioEntity01.getEdad());
        assertEquals("emi@emi.com", usuarioEntity01.getCorreo());
        assertEquals(0, usuarioEntity01.getTelefono());
        //usuario02
        assertNotNull(usuarioEntity02);
        assertEquals("Isidora", usuarioEntity02.getNombre());
        assertEquals("Peralta", usuarioEntity02.getApellido());
        assertEquals(24, usuarioEntity02.getEdad());
        assertEquals("isi@isi.com", usuarioEntity02.getCorreo());
        assertEquals(993492324, usuarioEntity02.getTelefono());

        //verify
        verify(usuarioRepository).findById(dataTest.get(0).getId());
        verify(usuarioRepository).findById(dataTest.get(1).getId());

    }

    @Test
    void findAll() {

        //GIVEN
        List<UsuarioEntity> dataTest = Arrays.asList(DataTest.usuarioEntity01, DataTest.usuarioEntity02);

        //WHEN
        when(usuarioRepository.findAll()).thenReturn(dataTest);

        //INVOKE METHODS
        List<UsuarioEntity> usuarioEntityList = usuarioServices.findAll();

        //THEN
        //usuarios
        assertNotEquals(Collections.emptyList(), usuarioEntityList);
        assertEquals(2, usuarioEntityList.size());

        //verify
        verify(usuarioRepository).findAll();

    }

    @Test
    void save() {

        //GIVEN
        UsuarioEntity usuarioEntity01 = DataTest.usuarioEntity01;
        UsuarioDTO usuarioDTO01 = DataTest.usuarioDTO01;

        //WHEN
        when(modelMapper.map(usuarioDTO01, UsuarioEntity.class)).thenReturn(usuarioEntity01);
        when(usuarioRepository.save(usuarioEntity01)).thenReturn(usuarioEntity01);

        //INVOKE METHODS
        UsuarioEntity usuarioEntity01Saved = usuarioServices.save(usuarioDTO01);

        //THEN
        //usuario
        assertNotNull(usuarioEntity01Saved);
        assertEquals("Emilia", usuarioEntity01Saved.getNombre());
        assertEquals("Peralta", usuarioEntity01Saved.getApellido());
        assertEquals(9, usuarioEntity01Saved.getEdad());
        assertEquals("emi@emi.com", usuarioEntity01Saved.getCorreo());
        assertEquals(0, usuarioEntity01Saved.getTelefono());
        //verify
        verify(usuarioRepository).save(usuarioEntity01);

    }

    @Test
    void updateById() {

        //GIVEN
        UsuarioEntity usuarioEntity01 = DataTest.usuarioEntity01;
        UsuarioDTO usuarioDTO01 = DataTest.usuarioDTO01;

        //WHEN
        when(usuarioRepository.findById(usuarioDTO01.getId())).thenReturn(Optional.of(usuarioEntity01));
        when(modelMapper.map(usuarioDTO01, UsuarioEntity.class)).thenReturn(usuarioEntity01);
        when(usuarioRepository.save(usuarioEntity01)).thenReturn(usuarioEntity01);

        //INVOKE METHODS
        UsuarioEntity usuarioEntity01Saved = usuarioServices.updateById(usuarioDTO01.getId(), usuarioDTO01);

        //THEN
        //usuario
        assertNotNull(usuarioEntity01Saved);
        assertEquals("Emilia", usuarioEntity01Saved.getNombre());
        assertEquals("Peralta", usuarioEntity01Saved.getApellido());
        assertEquals(9, usuarioEntity01Saved.getEdad());
        assertEquals("emi@emi.com", usuarioEntity01Saved.getCorreo());
        assertEquals(0, usuarioEntity01Saved.getTelefono());
        //verify
        verify(usuarioRepository).save(usuarioEntity01);
        verify(usuarioRepository).findById(usuarioDTO01.getId());

    }

    @Test
    void deleteById() {

        //GIVEN
        UsuarioEntity usuarioEntity01 = DataTest.usuarioEntity01;

        //WHEN
        when(usuarioRepository.findById(usuarioEntity01.getId())).thenReturn(Optional.of(usuarioEntity01));

        //INVOKE METHODS
        UsuarioEntity usuarioEntity01Deleted = usuarioServices.deleteById(usuarioEntity01.getId());

        //THEN
        //usuario
        assertNotNull(usuarioEntity01Deleted);
        assertEquals("Emilia", usuarioEntity01Deleted.getNombre());
        assertEquals("Peralta", usuarioEntity01Deleted.getApellido());
        assertEquals(9, usuarioEntity01Deleted.getEdad());
        assertEquals("emi@emi.com", usuarioEntity01Deleted.getCorreo());
        assertEquals(0, usuarioEntity01Deleted.getTelefono());
        //verify
        verify(usuarioRepository).deleteById(usuarioEntity01.getId());

    }

    @Test
    void deleteAll() {
        //INVOKE METHODS
        usuarioServices.deleteAll();
        //THEN
        verify(usuarioRepository).deleteAll();

    }
}