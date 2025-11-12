package com.dwigs.biblioteca.controller.api.admin;

import com.dwigs.biblioteca.model.Rol;
import com.dwigs.biblioteca.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/api/admin/roles")
public class RolesApiController {

    private RolRepository rolRepository;

    @Autowired
    public RolesApiController(
            RolRepository repository
    ){
        this.rolRepository = repository;
    }

    @GetMapping()
    public List<Rol> listar(){
        return rolRepository.findAll();
    }
}
