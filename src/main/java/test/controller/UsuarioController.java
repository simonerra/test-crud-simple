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
import test.service.UsuarioService;

@RestController
public class UsuarioController {

	private UsuarioService usuarioService;

	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@GetMapping("findById/{id}")
	public ResponseEntity<UsuarioEntity> findById(@PathVariable Long id) {
		UsuarioEntity usuarioEntity = usuarioService.findById(id);
		if (usuarioEntity != null) {
			return new ResponseEntity<>(usuarioEntity, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@GetMapping("findAll")
	public ResponseEntity<List<UsuarioEntity>> findAll() {
		List<UsuarioEntity> listado = usuarioService.findAll();
		if (!listado.isEmpty()) {
			return new ResponseEntity<>(listado, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@PostMapping("save")
	public ResponseEntity<UsuarioEntity> save(@RequestBody UsuarioDTO usuarioDTO) {
		return new ResponseEntity<>(usuarioService.save(usuarioDTO), HttpStatus.OK);
	}

	@PutMapping("updateById/{id}")
	public ResponseEntity<UsuarioEntity> updateById(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
		if (usuarioService.findById(id) != null) {
			return new ResponseEntity<>(usuarioService.updateById(id, usuarioDTO),
					HttpStatus.OK);
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("deleteById/{id}")
	public ResponseEntity<UsuarioEntity> delete(@PathVariable("id") Long id) {
		if (usuarioService.findById(id) != null) {
			return new ResponseEntity<>(usuarioService.deleteById(id), HttpStatus.OK);
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("deleteAll")
	public ResponseEntity<String> delete() {
		usuarioService.deleteAll();
		return ResponseEntity.ok("Todo borrado");
	}

}
