package test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import resources.data.DataTest;
import test.dto.UsuarioDTO;
import test.entity.UsuarioEntity;
import test.services.impl.UsuarioServicesImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.*;


@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    UsuarioServicesImpl usuarioServices;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }


    @Test
    void findById() throws Exception {

        //GIVEN
        UsuarioEntity usuarioEntity01 = DataTest.usuarioEntity01;

        //WHEN
        when(usuarioServices.findById(usuarioEntity01.getId())).thenReturn(usuarioEntity01);

        //INVOKE REQUESTS
        mvc.perform(get("/findById/" + usuarioEntity01.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                //THEN
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombre").value(usuarioEntity01.getNombre()))
                .andExpect(jsonPath("$.apellido").value(usuarioEntity01.getApellido()))
                .andExpect(jsonPath("$.edad").value(usuarioEntity01.getEdad()))
                .andExpect(jsonPath("$.correo").value(usuarioEntity01.getCorreo()))
                .andExpect(jsonPath("$.telefono").value(usuarioEntity01.getTelefono()));

        //verify
        verify(usuarioServices).findById(usuarioEntity01.getId());

    }

    @Test
    void findByIdNotFound() throws Exception {

        //WHEN
        when(usuarioServices.findById(1L)).thenReturn(null);

        //INVOKE REQUESTS
        mvc.perform(get("/findById/1")
                        .contentType(MediaType.APPLICATION_JSON))
                //THEN
                .andExpect(status().isNotFound());

        //verify
        verify(usuarioServices).findById(1L);

    }

    @Test
    void findAll() throws Exception {

        //GIVEN
        List<UsuarioEntity> usuarioEntityList = Arrays.asList(DataTest.usuarioEntity01, DataTest.usuarioEntity02);

        //WHEN
        when(usuarioServices.findAll()).thenReturn(usuarioEntityList);

        //INVOKE REQUESTS
        mvc.perform(get("/findAll")
                        .contentType(MediaType.APPLICATION_JSON))
                //THEN
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(usuarioEntityList.size()))
                .andExpect(jsonPath("$[0].nombre").value("Emilia"))
                .andExpect(jsonPath("$[0].apellido").value("Peralta"))
                .andExpect(jsonPath("$[0].edad").value(9))
                .andExpect(jsonPath("$[0].correo").value("emi@emi.com"))
                .andExpect(jsonPath("$[0].telefono").value(0))
                .andExpect(jsonPath("$[1].nombre").value("Isidora"))
                .andExpect(jsonPath("$[1].apellido").value("Peralta"))
                .andExpect(jsonPath("$[1].edad").value(24))
                .andExpect(jsonPath("$[1].correo").value("isi@isi.com"))
                .andExpect(jsonPath("$[1].telefono").value(993492324));

        //verify
        verify(usuarioServices).findAll();

    }

    @Test
    void findAllEmptyList() throws Exception {

        //WHEN
        when(usuarioServices.findAll()).thenReturn(Collections.emptyList());

        //INVOKE REQUESTS
        mvc.perform(get("/findAll")
                        .contentType(MediaType.APPLICATION_JSON))
                //THEN
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());

        //verify
        verify(usuarioServices).findAll();

    }

    @Test
    void save() throws Exception {

        //GIVEN
        UsuarioEntity usuarioEntity01 = DataTest.usuarioEntity01;
        UsuarioDTO usuarioDTO01 = DataTest.usuarioDTO01;

        //WHEN
        when(usuarioServices.save(usuarioDTO01)).thenReturn(usuarioEntity01);

        //INVOKE REQUESTS
        mvc.perform(post("/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioDTO01)))
                //THEN
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombre").value(usuarioEntity01.getNombre()))
                .andExpect(jsonPath("$.apellido").value(usuarioEntity01.getApellido()))
                .andExpect(jsonPath("$.edad").value(usuarioEntity01.getEdad()))
                .andExpect(jsonPath("$.correo").value(usuarioEntity01.getCorreo()))
                .andExpect(jsonPath("$.telefono").value(usuarioEntity01.getTelefono()));

        //verify
        verify(usuarioServices).save(usuarioDTO01);

    }

    @Test
    void updateById() throws Exception {

        //GIVEN
        UsuarioEntity usuarioEntity01 = DataTest.usuarioEntity01;
        UsuarioDTO usuarioDTO01 = DataTest.usuarioDTO01;

        //WHEN
        when(usuarioServices.updateById(usuarioDTO01.getId(), usuarioDTO01)).thenReturn(usuarioEntity01);

        //INVOKE REQUESTS
        mvc.perform(put("/updateById/" + usuarioDTO01.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioDTO01)))
                //THEN
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.nombre").value(usuarioEntity01.getNombre()));
        //verify
        verify(usuarioServices).updateById(usuarioDTO01.getId(), usuarioDTO01);

    }

    @Test
    void updateByIdNotFound() throws Exception {

        //GIVEN
        UsuarioDTO usuarioDTO01 = DataTest.usuarioDTO01;

        //WHEN
        when(usuarioServices.updateById(usuarioDTO01.getId(), usuarioDTO01)).thenReturn(null);

        //INVOKE REQUESTS
        mvc.perform(put("/updateById/" + usuarioDTO01.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioDTO01)))
                //THEN
                .andExpect(status().isNotFound());
        //verify
        verify(usuarioServices).updateById(usuarioDTO01.getId(), usuarioDTO01);

    }

    @Test
    void deleteById() throws Exception {

        //GIVEN
        UsuarioEntity usuarioEntity01 = DataTest.usuarioEntity01;
        UsuarioDTO usuarioDTO01 = DataTest.usuarioDTO01;

        //WHEN
        when(usuarioServices.deleteById(usuarioDTO01.getId())).thenReturn(usuarioEntity01);

        //INVOKE REQUESTS
        mvc.perform(delete("/deleteById/" + usuarioDTO01.getId()))
                //THEN
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.nombre").value(usuarioEntity01.getNombre()))
                .andExpect(jsonPath("$.apellido").value(usuarioEntity01.getApellido()));
        //verify
        verify(usuarioServices).deleteById(usuarioDTO01.getId());

    }

    @Test
    void deleteByIdNotFound() throws Exception {

        //GIVEN
        UsuarioDTO usuarioDTO01 = DataTest.usuarioDTO01;

        //WHEN
        when(usuarioServices.deleteById(usuarioDTO01.getId())).thenReturn(null);

        //INVOKE REQUESTS
        mvc.perform(delete("/deleteById/" + usuarioDTO01.getId()))
                //THEN
                .andExpect(status().isNotFound());
        //verify
        verify(usuarioServices).deleteById(usuarioDTO01.getId());

    }

    @Test
    void deleteAll() throws Exception {

        //INVOKE REQUESTS
        mvc.perform(delete("/deleteAll"))
                //THEN
                .andExpect(status().isOk());
        //verify
        verify(usuarioServices).deleteAll();

    }
}