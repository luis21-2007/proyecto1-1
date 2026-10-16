package mx.edu.utez.proyecto1c.controller.usuario.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestCrearUsuarioDTO {
    @NotBlank(message = "El username es obligatorio")
    @Size(min =4, max = 20, message =  "El username debe de ser entre 4 y 20 caracteres")
    private String username;

    @NotBlank(message = "El password es obligatorio")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "La contraseña debe tener al menos 8 caracteres, una mayúscula y un número"
    )
    private String password;

    @NotBlank(message = "El rol es obligatorio")
    @Pattern(
            regexp = "^(ADMIN|GESTOR|ESTUDIANTE)$",
            message = "El rol debe ser ADMIN, GESTOR o ESTUDIANTE"
    )
    private String rol;

    private String descripcion;

    //datosd de personas
    @NotBlank(message = "El nombre es obligatorio")
    private String nombres;
    @NotBlank(message = "El apellido es obligatorio")
    private String primerApellido;

    private String segundoApellido;

    @Past
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaNacimiento;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo es invalido")
    private String correo;

    @NotBlank(message = "El curp es obligatorio")
    private String curp;
}


