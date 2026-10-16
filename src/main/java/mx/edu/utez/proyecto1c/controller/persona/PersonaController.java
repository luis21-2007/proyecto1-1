package mx.edu.utez.proyecto1c.controller.persona;

import jakarta.validation.Valid;
import mx.edu.utez.proyecto1c.controller.persona.dto.RequestActualizarPersonaDTO;
import mx.edu.utez.proyecto1c.model.persona.Persona;
import mx.edu.utez.proyecto1c.service.persona.PersonaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persona")
@CrossOrigin({"*"})
public class PersonaController {

    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping
    public ResponseEntity<List<Persona>> getAllPersonas() {
        return ResponseEntity
                .status(200)
                .body(this.personaService.getAllPersonas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> getPersonaById(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(this.personaService.getPersonaById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> updatePersona(
            @PathVariable Long id,
            @RequestBody @Valid RequestActualizarPersonaDTO payload) {
        return ResponseEntity
                .status(200)
                .body(this.personaService.updatePersona(id, payload));
    }
}