package com.dwigs.biblioteca.controller.api.admin;

import com.dwigs.biblioteca.dto.request.ActualizarUsuarioDTO;
import com.dwigs.biblioteca.model.EstadoUsuario;
import com.dwigs.biblioteca.model.Usuario;
import com.dwigs.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/admin/usuarios")
public class UsuariosApiController {
    private UsuarioRepository usuarioRepository;

    @Autowired
    public UsuariosApiController(
            UsuarioRepository usuarioRepository
    ){
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping()
    public List<Usuario> listar(){
        return usuarioRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Usuario> consultar(@PathVariable long id){

        Optional<Usuario> obj = usuarioRepository.findOneById(id);
        return obj.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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

    // TODO: endpoint para averiguar si el correo está siendo usado

    @PostMapping()
    public ResponseEntity<?> crear(@RequestBody ActualizarUsuarioDTO crearDTO){
        Usuario usuario = new Usuario();

        String email = crearDTO.getEmail();
        if(usuarioRepository.existsByEmail(email)){
            return ResponseEntity.badRequest().body("Ya existe un usuario con el correo electrónico indicado");
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        usuario.setNombres(crearDTO.getNombres());
        usuario.setApellidos(crearDTO.getApellidos());
        usuario.setTelefono(crearDTO.getTelefono());
        usuario.setRoles(crearDTO.getRoles());
        usuario.setEmailPersonal(crearDTO.getEmailPersonal());
        usuario.setEmail(email);
        usuario.setTipoDocumento(crearDTO.getTipoDocumento());
        usuario.setGenero(crearDTO.getGenero());
        usuario.setEstadoCivil(crearDTO.getEstadoCivil());
        usuario.setEstadoUsuario(EstadoUsuario.normal);
        usuario.setPassword(passwordEncoder.encode(crearDTO.getPassword()));

        usuarioRepository.save(usuario);

        return ResponseEntity.created(URI.create("/api/admin/usuarios/"+ usuario.getId())).body(usuario);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<?> actualizar(@RequestBody ActualizarUsuarioDTO editarDTO, @PathVariable Long id){
        Usuario usuario = usuarioRepository.findOneById(id).orElseThrow();

        String email = editarDTO.getEmail();
        if(!usuario.getEmail().equals(email) && usuarioRepository.existsByEmail(email)){
            return ResponseEntity.badRequest().body("Ya existe un usuario con el correo electrónico indicado");
        }
        String password = editarDTO.getPassword();
        if(password != null){
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            usuario.setPassword(passwordEncoder.encode(editarDTO.getPassword()));
        }
        usuario.setNombres(editarDTO.getNombres());
        usuario.setApellidos(editarDTO.getApellidos());
        usuario.setTelefono(editarDTO.getTelefono());
        usuario.setRoles(editarDTO.getRoles());
        usuario.setEmailPersonal(editarDTO.getEmailPersonal());
        usuario.setEmail(email);
        usuario.setTipoDocumento(editarDTO.getTipoDocumento());
        usuario.setGenero(editarDTO.getGenero());
        usuario.setEstadoCivil(editarDTO.getEstadoCivil());
        usuario.setEstadoUsuario(editarDTO.getEstadoUsuario());

        return ResponseEntity.ok(usuarioRepository.save(usuario));
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable long id){
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build(); //ResponseEntity.notFound().build();
    }

}
