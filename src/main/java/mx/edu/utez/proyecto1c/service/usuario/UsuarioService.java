package mx.edu.utez.proyecto1c.service.usuario;


import jakarta.transaction.Transactional;
import mx.edu.utez.proyecto1c.controller.usuario.dto.RequestActualizarUsuarioDTO;
import mx.edu.utez.proyecto1c.controller.usuario.dto.RequestCrearUsuarioDTO;
import mx.edu.utez.proyecto1c.exception.customExceptions.BadRequestException;
import mx.edu.utez.proyecto1c.model.persona.Persona;
import mx.edu.utez.proyecto1c.model.repository.persona.PersonaRepository;
import mx.edu.utez.proyecto1c.model.repository.usuario.UsuarioRepository;
import mx.edu.utez.proyecto1c.model.usuario.Roles;
import mx.edu.utez.proyecto1c.model.usuario.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    //Inyeccion de dependencias
    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;
    public UsuarioService(UsuarioRepository usuarioRepository,PersonaRepository personaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.personaRepository = personaRepository;
    }
//  Servicios
    public List<Usuario> getAllUsers() {
     return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario createUser(RequestCrearUsuarioDTO payload){
        //validar si el username ya existe
        if (usuarioRepository.findByUsername(payload.getUsername()).isPresent()){
            throw new BadRequestException("ESE usuario ya esta en uso");
        }

        //registrar Persona
        Persona newPersona = new Persona();
        newPersona.setNombres(payload.getNombres());
        newPersona.setPrimerApellido(payload.getPrimerApellido());
        newPersona.setSegundoApellido(payload.getSegundoApellido());
        newPersona.setFechaNacimiento(payload.getFechaNacimiento());
        newPersona.setCorreo(payload.getCorreo());
        newPersona.setCurp(payload.getCurp());

        //Creando registro de persona y obtener su id como registro existente como el id
        newPersona=personaRepository.save(newPersona);


        //Crear intancia de usuario y llenarla en la base de datos a la info de payload
        Usuario newUser = new Usuario();
        newUser.setUsername(payload.getUsername());
        newUser.setPassword(payload.getPassword());
        newUser.setDescripcion(payload.getDescripcion());
        newUser.setEnable(true);

        newUser.setRol(
                Roles.valueOf(payload.getRol())
        );

        //relacionar la persona registrada con este nuevo usuario
        newUser.setPersona(newPersona);

        return usuarioRepository.save(newUser);
    }

    public Usuario getUserById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Usuario no encontrado con el ID: " + id));
    }

    @Transactional
    public Usuario changeStatus(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Usuario no encontrado con el ID: " + id));

        usuario.setEnable(!usuario.isEnable());

        return usuarioRepository.save(usuario);
    }
    @Transactional
    public Usuario updateUser(Long id, RequestActualizarUsuarioDTO payload) {

        Usuario usuarioExistente = getUserById(id);

        if (!usuarioExistente.getUsername().equals(payload.getUsername()) &&
                usuarioRepository.findByUsername(payload.getUsername()).isPresent()) {
            throw new BadRequestException("El username ya está en uso por otro usuario");
        }

        usuarioExistente.setUsername(payload.getUsername());
        usuarioExistente.setDescripcion(payload.getDescripcion());
        
        return usuarioRepository.save(usuarioExistente);
    }

}

