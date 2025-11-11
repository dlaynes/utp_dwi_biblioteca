package com.dwigs.biblioteca.controller.api;

import com.dwigs.biblioteca.dto.request.LoginRequest;
import com.dwigs.biblioteca.dto.request.RegisterRequest;
import com.dwigs.biblioteca.dto.response.TokenResponse;
import com.dwigs.biblioteca.model.Rol;
import com.dwigs.biblioteca.model.Usuario;
import com.dwigs.biblioteca.repository.RolRepository;
import com.dwigs.biblioteca.repository.UsuarioRepository;
import com.dwigs.biblioteca.security.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequestMapping("/api/auth")
@RestController
public class AuthApiController {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?>  authenticateAndGetToken( @RequestBody LoginRequest request){
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        if(auth.isAuthenticated()){
            return ResponseEntity.ok(new TokenResponse(jwtTokenService.generateToken(request.getEmail()), auth.getAuthorities()));
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado o contraseña inválida");
        }
    }

    @PostMapping(value="/register", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        if(usuarioRepository.findByEmail(request.getEmail()) != null){
            return ResponseEntity.badRequest().body("Ya existe un usuario con el correo electrónico indicado");
        }
        Optional<Rol> rolOptional = rolRepository.findByNombre("Cliente");
        if(!rolOptional.isPresent()){
            return ResponseEntity.internalServerError().body("No se encontró el rol Cliente");
        }

        Usuario u = new Usuario();
        u.setEmail(request.getEmail());
        u.setPassword(passwordEncoder.encode(request.getPassword()));
        Set<Rol> roles = new HashSet<>();
        roles.add(rolOptional.get());
        u.setRoles(roles);
        usuarioRepository.save(u);

        return ResponseEntity.ok("Usuario creado");
    }

}
