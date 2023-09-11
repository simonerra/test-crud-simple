package test.data;

import test.dto.UsuarioDTO;
import test.entity.UsuarioEntity;

public class DataTest {
    public static final UsuarioEntity usuarioEntity01 = new UsuarioEntity(1L, "Emilia", "Peralta", 9, "emi@emi.com", 0);
    public static final UsuarioDTO usuarioDTO01 = new UsuarioDTO(1L, "Emilia", "Peralta", 9, "emi@emi.com", 0);
    public static final UsuarioEntity usuarioEntity02 = new UsuarioEntity(2L, "Isidora", "Peralta", 24, "isi@isi.com", 993492324);
    public static final UsuarioDTO usuarioDTO02 = new UsuarioDTO(2L, "Isidora", "Peralta", 24, "isi@isi.com", 993492324);
}
