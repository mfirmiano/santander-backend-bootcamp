package me.dio.service.impl;

import me.dio.domain.model.User;
import me.dio.domain.repository.UserRepository;
import me.dio.service.UserService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Encontra um usuário pelo ID.
     *
     * @param id O ID do usuário a ser encontrado.
     * @return O usuário encontrado.
     * @throws NoSuchElementException se o usuário não for encontrado.
     */
    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found with ID: " + id));
    }

    /**
     * Cria um novo usuário.
     *
     * @param userToCreate O usuário a ser criado.
     * @return O usuário criado.
     * @throws IllegalArgumentException se o número da conta já existir.
     */
    @Override
    public User create(User userToCreate) {
        if (userRepository.existsByAccountNumber(userToCreate.getAccount().getNumber())) {
            throw new IllegalArgumentException("User with the same account number already exists.");
        }
        // Adicione validações adicionais se necessário, como garantir que 'userToCreate' não seja nulo.
        return userRepository.save(userToCreate);
    }
}
