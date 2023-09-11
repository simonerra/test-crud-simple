package test.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import test.dto.UsuarioDTO;
import test.entity.UsuarioEntity;
import test.services.UsuarioServices;

@RestController
public class UsuarioController {

    UsuarioServices usuarioService;

    public UsuarioController(UsuarioServices usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("findById/{id}")
    public ResponseEntity<UsuarioEntity> findById(@PathVariable Long id) {
        UsuarioEntity usuarioEntity = usuarioService.findById(id);
        if (usuarioEntity == null) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(usuarioEntity, HttpStatus.OK);
        }
    }

    @GetMapping("findAll")
    public ResponseEntity<List<UsuarioEntity>> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @PostMapping("save")
    public ResponseEntity<UsuarioEntity> save(@RequestBody UsuarioDTO usuarioDTO) {
        return new ResponseEntity<>(usuarioService.save(usuarioDTO), HttpStatus.OK);
    }

    @PutMapping("updateById/{id}")
    public ResponseEntity<UsuarioEntity> updateById(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioEntity usuarioEntity = usuarioService.updateById(id, usuarioDTO);
        if (usuarioEntity == null) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(usuarioEntity, HttpStatus.OK);
        }
    }

    @DeleteMapping("deleteById/{id}")
    public ResponseEntity<UsuarioEntity> deleteById(@PathVariable("id") Long id) {
        UsuarioEntity usuarioEntity = usuarioService.deleteById(id);
        if (usuarioEntity == null) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(usuarioEntity, HttpStatus.OK);
        }
    }

    @DeleteMapping("deleteAll")
    public void deleteAll() {
        usuarioService.deleteAll();
    }

}