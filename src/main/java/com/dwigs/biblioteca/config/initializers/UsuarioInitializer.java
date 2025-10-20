package com.dwigs.biblioteca.config.initializers;

import com.dwigs.biblioteca.model.*;
import com.dwigs.biblioteca.repository.PerfilRepository;
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
            RolRepository rolRepository,
            PerfilRepository perfilRepository
    ){
        return args -> {
            crearAdmin(usuarioRepository, rolRepository, perfilRepository);
            crearBibliotecario(usuarioRepository, rolRepository, perfilRepository);
            Optional<Rol> rolOptional = rolRepository.findByNombre("Cliente");
            if(rolOptional.isEmpty()){
                Rol nuevoRol = new Rol();
                nuevoRol.setNombre("Cliente");
                rolRepository.save(nuevoRol);
            }
        };
    }

    private void crearAdmin(
            UsuarioRepository usuarioRepository,
            RolRepository rolRepository,
            PerfilRepository perfilRepository
    ) throws Exception {
        Optional<Rol> rolAdmin = rolRepository.findByNombre("Admin");
        Rol rol;
        if(rolAdmin.isEmpty()){
            Rol nuevoRol = new Rol();
            nuevoRol.setNombre("Admin");
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
            usuarioRepository.save(admin);

            Perfil perfil = new Perfil();
            perfil.setUsuario(admin);
            perfil.setNombres("Admin");
            perfil.setApellidos("Instrador");
            perfil.setEstadoCivil(EstadoCivil.soltero);
            perfil.setGenero(Genero.masculino);
            perfil.setTelefono("999999999");
            perfil.setTipoDocumento(TipoDocumento.dni);
            perfil.setNumeroDocumento("00000000");
            perfil.setEmailPersonal("admin@biblioteca.com");
            perfilRepository.save(perfil);

            admin.setPerfil(perfil);
            usuarioRepository.save(admin);
        }
    }

    private void crearBibliotecario(
            UsuarioRepository usuarioRepository,
            RolRepository rolRepository,
            PerfilRepository perfilRepository
    ) throws Exception {
        Optional<Rol> rolOptional = rolRepository.findByNombre("Bibliotecario");
        Rol rol;
        if(rolOptional.isEmpty()){
            Rol nuevoRol = new Rol();
            nuevoRol.setNombre("Bibliotecario");
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
            usuarioRepository.save(bibliotecario);

            Perfil perfil = new Perfil();
            perfil.setUsuario(bibliotecario);
            perfil.setNombres("Biblio");
            perfil.setApellidos("Tecario");
            perfil.setEstadoCivil(EstadoCivil.soltero);
            perfil.setGenero(Genero.masculino);
            perfil.setTelefono("999999998");
            perfil.setTipoDocumento(TipoDocumento.dni);
            perfil.setNumeroDocumento("00000001");
            perfil.setEmailPersonal("mantenimiento@biblioteca.com");
            perfilRepository.save(perfil);

            bibliotecario.setPerfil(perfil);
            usuarioRepository.save(bibliotecario);
        }
    }

}
