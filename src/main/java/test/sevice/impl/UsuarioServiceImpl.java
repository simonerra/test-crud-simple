package test.sevice.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import test.dto.UsuarioDTO;
import test.entity.UsuarioEntity;
import test.repository.UsuarioRepository;
import test.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	ModelMapper modelMapper;
	UsuarioRepository usuarioRepository;

	public UsuarioServiceImpl(UsuarioRepository usuarioRepository, ModelMapper modelMapper) {
		this.usuarioRepository = usuarioRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public UsuarioEntity findById(Long id) {
		return usuarioRepository.findById(id).orElse(null);
	}

	@Override
	public List<UsuarioEntity> findAll() {
		return (List<UsuarioEntity>) usuarioRepository.findAll();
	}

	@Override
	public UsuarioEntity save(UsuarioDTO usuarioDTO) {
		UsuarioEntity usuarioEntity = modelMapper.map(usuarioDTO, UsuarioEntity.class);
		return usuarioRepository.save(usuarioEntity);
	}

	@Override
	public UsuarioEntity updateById(Long id, UsuarioDTO usuarioDTO) {
		if (findById(id) != null) {
			usuarioDTO.setId(id);
			return usuarioRepository.save(modelMapper.map(usuarioDTO, UsuarioEntity.class));
		}
		return null;
	}

	@Override
	public UsuarioEntity deleteById(Long id) {

		Optional<UsuarioEntity> optionalUsuarioEntity = usuarioRepository.findById(id);

		if (optionalUsuarioEntity.isPresent()) {
			UsuarioEntity usuarioEntity = optionalUsuarioEntity.get();
			usuarioRepository.deleteById(id);
			return usuarioEntity;
		}

		return null;

	}

	@Override
	public void deleteAll() {
		usuarioRepository.deleteAll();
	}

}
