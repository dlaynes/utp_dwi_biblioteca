package com.dwigs.biblioteca.config.initializers;

import com.dwigs.biblioteca.model.*;

import com.dwigs.biblioteca.repository.RolRepository;
import com.dwigs.biblioteca.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Configuration
public class UsuarioInitializer {

    @Bean
    public CommandLineRunner initUsuarios(
            UsuarioRepository usuarioRepository,
            RolRepository rolRepository
    ){
        return args -> {
            crearAdmin(usuarioRepository, rolRepository);
            crearBibliotecario(usuarioRepository, rolRepository);
            Optional<Rol> rolOptional = rolRepository.findByNombre("CLIENTE");
            if(rolOptional.isEmpty()){
                Rol nuevoRol = new Rol();
                nuevoRol.setNombre("CLIENTE");
                rolRepository.save(nuevoRol);
            }
        };
    }

    private void crearAdmin(
            UsuarioRepository usuarioRepository,
            RolRepository rolRepository
    ) {
        Optional<Rol> rolAdmin = rolRepository.findByNombre("ADMIN");
        Rol rol;
        if(rolAdmin.isEmpty()){
            Rol nuevoRol = new Rol();
            nuevoRol.setNombre("ADMIN");
            rol = rolRepository.save(nuevoRol);
        } else {
            rol = rolAdmin.get();
        }
        if (!usuarioRepository.existsByEmail("admin@biblioteca.com")){
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            Usuario admin = new Usuario();
            admin.setPassword(passwordEncoder.encode("d0ntUs3Th1sPa$s"));
            admin.setEstadoUsuario(EstadoUsuario.normal);
            Set<Rol> roles = new HashSet<>();
            roles.add(rol);
            admin.setRoles(roles);
            admin.setEmail("admin@biblioteca.com");
            admin.setNombres("Admin");
            admin.setApellidos("Instrador");
            admin.setEstadoCivil(EstadoCivil.soltero);
            admin.setGenero(Genero.masculino);
            admin.setTelefono("999999999");
            admin.setTipoDocumento(TipoDocumento.dni);
            admin.setNumeroDocumento("00000000");
            admin.setEmailPersonal("admin@biblioteca.com");

            usuarioRepository.save(admin);
        }
    }

    private void crearBibliotecario(
            UsuarioRepository usuarioRepository,
            RolRepository rolRepository
    ) {
        Optional<Rol> rolOptional = rolRepository.findByNombre("BIBLIOTECARIO");
        Rol rol;
        if(rolOptional.isEmpty()){
            Rol nuevoRol = new Rol();
            nuevoRol.setNombre("BIBLIOTECARIO");
            rol = rolRepository.save(nuevoRol);
        } else {
            rol = rolOptional.get();
        }
        if (!usuarioRepository.existsByEmail("mantenimiento@biblioteca.com")){
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            Usuario bibliotecario = new Usuario();
            bibliotecario.setPassword(passwordEncoder.encode("d0ntUs3Th1sPa$s"));
            bibliotecario.setEstadoUsuario(EstadoUsuario.normal);
            Set<Rol> roles = new HashSet<>();
            roles.add(rol);
            bibliotecario.setRoles(roles);
            bibliotecario.setEmail("mantenimiento@biblioteca.com");
            bibliotecario.setNombres("Biblio");
            bibliotecario.setApellidos("Tecario");
            bibliotecario.setEstadoCivil(EstadoCivil.soltero);
            bibliotecario.setGenero(Genero.masculino);
            bibliotecario.setTelefono("999999998");
            bibliotecario.setTipoDocumento(TipoDocumento.dni);
            bibliotecario.setNumeroDocumento("00000001");
            bibliotecario.setEmailPersonal("mantenimiento@biblioteca.com");
            usuarioRepository.save(bibliotecario);

        }
    }

}
