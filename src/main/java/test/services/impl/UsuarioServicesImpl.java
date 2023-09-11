package test.services.impl;

import java.util.Collections;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import test.dto.UsuarioDTO;
import test.entity.UsuarioEntity;
import test.repository.UsuarioRepository;
import test.services.UsuarioServices;

@Service
public class UsuarioServicesImpl implements UsuarioServices {

    ModelMapper modelMapper;
    UsuarioRepository usuarioRepository;

    public UsuarioServicesImpl(UsuarioRepository usuarioRepository, ModelMapper modelMapper) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UsuarioEntity findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public List<UsuarioEntity> findAll() {
        List<UsuarioEntity> usuarioEntityList = (List<UsuarioEntity>) usuarioRepository.findAll();
        if (usuarioEntityList.isEmpty()) {
            return Collections.emptyList();
        } else {
            return usuarioEntityList;
        }
    }

    @Override
    public UsuarioEntity save(UsuarioDTO usuarioDTO) {
        UsuarioEntity usuarioEntity = modelMapper.map(usuarioDTO, UsuarioEntity.class);
        return usuarioRepository.save(usuarioEntity);
    }

    @Override
    public UsuarioEntity updateById(Long id, UsuarioDTO usuarioDTO) {
        if (findById(id) == null) {
            return null;
        }
        usuarioDTO.setId(id);
        return save(usuarioDTO);
    }

    @Override
    public UsuarioEntity deleteById(Long id) {
        UsuarioEntity usuario = findById(id);
        if (usuario == null) {
            return null;
        } else {
            usuarioRepository.deleteById(id);
            return usuario;
        }
    }

    @Override
    public void deleteAll() {
        usuarioRepository.deleteAll();
    }

}