package com.dwigs.biblioteca.controller.api.admin;

import com.dwigs.biblioteca.dto.request.ActualizarUsuarioDTO;
import com.dwigs.biblioteca.dto.request.ExisteEmailDTO;
import com.dwigs.biblioteca.dto.response.UsuarioResponseDTO;
import com.dwigs.biblioteca.model.EstadoUsuario;
import com.dwigs.biblioteca.model.Rol;
import com.dwigs.biblioteca.model.Usuario;
import com.dwigs.biblioteca.repository.RolRepository;
import com.dwigs.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/api/admin/usuarios")
public class UsuariosApiController {
    private UsuarioRepository usuarioRepository;

    private RolRepository rolRepository;

    @Autowired
    public UsuariosApiController(
            UsuarioRepository usuarioRepository,
            RolRepository rolRepository
    ){
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    private Set<String> convertirRolesARolKeys(Set<Rol> roles){
        return roles.stream().map(role-> "ROLE_" + role.getNombre())
                .collect(Collectors.toSet());
    }

    @GetMapping()
    public List<UsuarioResponseDTO> listar(){
        return usuarioRepository.findAll().stream().map(it -> {
            UsuarioResponseDTO usuarioResponseDTO = UsuarioResponseDTO.deUsuario(it);
            usuarioResponseDTO.setRolKeys(convertirRolesARolKeys(it.getRoles()));
            return usuarioResponseDTO;
        }).collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UsuarioResponseDTO> consultar(@PathVariable long id){

        Usuario obj = usuarioRepository.findOneById(id).orElseThrow();
        UsuarioResponseDTO usuarioResponseDTO = UsuarioResponseDTO.deUsuario(obj);
        usuarioResponseDTO.setRolKeys(convertirRolesARolKeys(obj.getRoles()));

        return ResponseEntity.ok(usuarioResponseDTO);
    }

    @PutMapping("/suspender/{id}")
    public ResponseEntity<Void> suspender(@PathVariable long id){
        Optional<Usuario> usuarioOptional = usuarioRepository.findOneById(id);
        if(usuarioOptional.isPresent()){
            Usuario usuario = usuarioOptional.get();
            usuario.setEstadoUsuario(EstadoUsuario.suspendido);
            usuarioRepository.save(usuario);
        }
        return ResponseEntity.noContent().build(); //ResponseEntity.notFound().build();
    }

    // Por temas de seguridad, este endpoint debería estar protegido contra ataques de bots
    // O auizás ser removido del flujo de interacciones del producto
    @PostMapping("/existe")
    public ResponseEntity<Boolean> existe(@RequestBody ExisteEmailDTO emailDTO) {
        String email = emailDTO.getEmail();
        Long id = emailDTO.getId();
        boolean existe = false;
        if(email != null){
            if(id != null){
                existe = usuarioRepository.existsByEmailAndId(email, id);
            } else {
                existe = usuarioRepository.existsByEmail(email);
            }
        }
        return ResponseEntity.ok().body(existe);
    }

    @PostMapping()
    public ResponseEntity<?> crear(@RequestBody ActualizarUsuarioDTO crearDTO){
        Usuario usuario = new Usuario();

        String email = crearDTO.getEmail();
        if(usuarioRepository.existsByEmail(email)){
            return ResponseEntity.badRequest().body("Ya existe un usuario con el correo electrónico indicado");
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = crearDTO.getPassword();
        if(password == null || password.equals("")){
            return ResponseEntity.badRequest().body("Ingrese una contraseña");
        }

        usuario.setNombres(crearDTO.getNombres());
        usuario.setApellidos(crearDTO.getApellidos());
        usuario.setTelefono(crearDTO.getTelefono());
        // String emailPersonal = crearDTO.getEmailPersonal();
        // usuario.setEmailPersonal(emailPersonal != null ? emailPersonal : email);
        usuario.setEmail(email);
        usuario.setNumeroDocumento(crearDTO.getNumeroDocumento());
        usuario.setTipoDocumento(crearDTO.getTipoDocumento());
        usuario.setGenero(crearDTO.getGenero());
        usuario.setEstadoCivil(crearDTO.getEstadoCivil());
        usuario.setEstadoUsuario(EstadoUsuario.normal);
        usuario.setPassword(passwordEncoder.encode(password));

        Set<String> roleNames = crearDTO.getRolKeys().stream().map(key -> key.replace("ROLE_", ""))
                .collect(Collectors.toSet());
        Set<Rol> roles = rolRepository.findByNombreIn(roleNames);
        usuario.setRoles(roles);

        usuarioRepository.save(usuario);

        UsuarioResponseDTO usuarioResponseDTO = UsuarioResponseDTO.deUsuario(usuario);
        usuarioResponseDTO.setRolKeys(convertirRolesARolKeys(usuario.getRoles()));

        return ResponseEntity.created(URI.create("/api/admin/usuarios/"+ usuario.getId())).body(usuarioResponseDTO);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<?> actualizar(@RequestBody ActualizarUsuarioDTO editarDTO, @PathVariable Long id){
        Usuario usuario = usuarioRepository.findOneById(id).orElseThrow();

        String email = editarDTO.getEmail();
        if(!usuario.getEmail().equals(email) && usuarioRepository.existsByEmail(email)){
            return ResponseEntity.badRequest().body("Ya existe un usuario con el correo electrónico indicado");
        }
        String password = editarDTO.getPassword();
        if(password != null && !password.equals("")){
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            usuario.setPassword(passwordEncoder.encode(editarDTO.getPassword()));
        }
        usuario.setNombres(editarDTO.getNombres());
        usuario.setApellidos(editarDTO.getApellidos());
        usuario.setTelefono(editarDTO.getTelefono());
        // String emailPersonal = editarDTO.getEmailPersonal();
        // usuario.setEmailPersonal(emailPersonal != null ? emailPersonal : email);
        usuario.setEmail(email);
        usuario.setTipoDocumento(editarDTO.getTipoDocumento());
        usuario.setNumeroDocumento(editarDTO.getNumeroDocumento());
        usuario.setGenero(editarDTO.getGenero());
        usuario.setEstadoCivil(editarDTO.getEstadoCivil());
        usuario.setEstadoUsuario(editarDTO.getEstadoUsuario());

        Set<String> roleNames = editarDTO.getRolKeys().stream().map(key -> key.replace("ROLE_", ""))
                .collect(Collectors.toSet());
        Set<Rol> roles = rolRepository.findByNombreIn(roleNames);
        usuario.setRoles(roles);

        usuarioRepository.save(usuario);

        UsuarioResponseDTO usuarioResponseDTO = UsuarioResponseDTO.deUsuario(usuario);
        usuarioResponseDTO.setRolKeys(convertirRolesARolKeys(usuario.getRoles()));

        return ResponseEntity.ok(usuarioResponseDTO);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable long id){
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build(); //ResponseEntity.notFound().build();
    }

}
