package com.zion.pomodorozion;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO register(RegisterDTO dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este usuario ya existe");

        }

        String hash = passwordEncoder.encode(dto.getPassword());
        User saved = userRepository.save(new User(dto.getUsername(), hash));

        return new UserDTO(saved.getId(), saved.getUsername());
    }

     public UserDTO changePassword( User user, String oldPassword, String newPassword) {
        user = userRepository.findById(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        if (!passwordEncoder.matches(oldPassword, user.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Contraseña incorrecta");
        } else {
            String hash = passwordEncoder.encode(newPassword);
            user.setPasswordHash(hash);
            userRepository.save(user);
            return new UserDTO(user.getId(), user.getUsername());
        }
    } 
    
}
