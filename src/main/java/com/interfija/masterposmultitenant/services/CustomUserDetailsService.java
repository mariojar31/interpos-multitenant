package com.interfija.masterposmultitenant.services;

import com.interfija.masterposmultitenant.model.UserPrincipal;
import com.interfija.masterposmultitenant.repository.UserRepository;
import com.interfija.masterposmultitenant.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar el usuario en la base de datos
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Crear y retornar UserPrincipal
        return new UserPrincipal(
                user.getUsername(),
                user.getPassword(),
                user.getTenantId(),
                Collections.emptyList(), // Aquí puedes agregar los roles/authorities del usuario
                user.isEnabled()
        );
    }
}