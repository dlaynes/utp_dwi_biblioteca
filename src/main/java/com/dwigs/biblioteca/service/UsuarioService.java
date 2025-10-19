package com.dwigs.biblioteca.service;

import com.dwigs.biblioteca.model.Usuario;
import com.dwigs.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired

    private UsuarioRepository usuarioRepository;

    // Preferimos el uso de correo para no obligar al visitante a pensar en un nombre de cuenta,
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("No se encontró el usuario " + username));
    }

    public Usuario getUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Usuario> user = usuarioRepository.findByEmail(auth.getName());
        return user.orElse(null);
    }

    public boolean existeConCorreo(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public Optional<Usuario> buscarPorCorreo(String email){
        return usuarioRepository.findByEmail(email);
    }

    public void guardarUsuario(Usuario usuario){
        usuarioRepository.save(usuario);
    }

    public long contarUsuarios() { return usuarioRepository.count(); }
}
