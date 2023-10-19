package me.dio.controller;

import jakarta.validation.Valid;
import me.dio.domain.model.User;
import me.dio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Recupera um usuário pelo ID.
     *
     * @param id O ID do usuário a ser recuperado.
     * @return ResponseEntity com o usuário encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Cria um novo usuário.
     *
     * @param userToCreate O usuário a ser criado.
     * @return ResponseEntity com o usuário criado e a localização.
     */
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User userToCreate) {
        User userCreated = userService.create(userToCreate);
        if (userCreated != null) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(userCreated.getId())
                    .toUri();
            return ResponseEntity.created(location).body(userCreated);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
