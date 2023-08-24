package test.service;

import java.util.List;

import test.dto.UsuarioDTO;
import test.entity.UsuarioEntity;

public interface UsuarioService {
	
	UsuarioEntity findById(Long id);
	
	List<UsuarioEntity> findAll();

	UsuarioEntity save(UsuarioDTO usuarioDTO);
	
	UsuarioEntity updateById(Long id, UsuarioDTO usuarioDTO);

	UsuarioEntity deleteById(Long id);
	
	void deleteAll();
	
}
