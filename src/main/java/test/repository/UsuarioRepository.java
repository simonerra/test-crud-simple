package test.repository;

import org.springframework.data.repository.CrudRepository;

import test.entity.UsuarioEntity;

public interface UsuarioRepository extends CrudRepository<UsuarioEntity, Long> {

}
