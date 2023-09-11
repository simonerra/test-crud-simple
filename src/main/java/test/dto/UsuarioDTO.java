package test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
	private Long id;
	private String nombre;
	private String apellido;
	private int edad;
	private String correo;
	private int telefono;

}
