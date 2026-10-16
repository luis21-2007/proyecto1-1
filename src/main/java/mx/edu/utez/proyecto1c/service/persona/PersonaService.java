package mx.edu.utez.proyecto1c.service.persona;

import jakarta.transaction.Transactional;
import mx.edu.utez.proyecto1c.controller.persona.dto.RequestActualizarPersonaDTO;
import mx.edu.utez.proyecto1c.exception.customExceptions.BadRequestException;
import mx.edu.utez.proyecto1c.model.persona.Persona;
import mx.edu.utez.proyecto1c.model.repository.persona.PersonaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaService {

    private final PersonaRepository personaRepository;

    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    public List<Persona> getAllPersonas() {
        return personaRepository.findAll();
    }

    public Persona getPersonaById(Long id) {
        return personaRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Persona no encontrada con el ID: " + id));
    }

    @Transactional
    public Persona updatePersona(Long id, RequestActualizarPersonaDTO payload) {
        Persona personaExistente = getPersonaById(id);

        // Actualización de campos
        personaExistente.setNombres(payload.getNombres());
        personaExistente.setPrimerApellido(payload.getPrimerApellido());
        personaExistente.setSegundoApellido(payload.getSegundoApellido());
        personaExistente.setFechaNacimiento(payload.getFechaNacimiento());
        personaExistente.setCorreo(payload.getCorreo());
        personaExistente.setCurp(payload.getCurp());

        return personaRepository.save(personaExistente);
    }
}
