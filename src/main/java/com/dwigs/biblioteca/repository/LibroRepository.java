package com.dwigs.biblioteca.repository;

import com.dwigs.biblioteca.model.Autor;
import com.dwigs.biblioteca.model.Editorial;
import com.dwigs.biblioteca.model.Idioma;
import com.dwigs.biblioteca.model.Libro;
import com.dwigs.biblioteca.model.converter.GeneroLiterarioAttributeConverter;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class LibroRepository extends InMemoryRepository<Libro> {

    public Libro patch(long id, Map<String, Object> cambios) {
        Libro actual = data.get(id);
        if (actual == null) return null;
        if (cambios.containsKey("titulo")) actual.setTitulo((String)cambios.get("titulo"));
        // if (cambios.containsKey("autor")) actual.setAutor((Autor) cambios.get("autor"));
        // if (cambios.containsKey("editorial")) actual.setEditorial((Editorial) cambios.get("editorial"));
        // if (cambios.containsKey("idioma")) actual.setIdioma((Idioma) cambios.get("idioma"));
        if (cambios.containsKey("ibsm")) actual.setIbsm((String)cambios.get("ibsm"));
        if (cambios.containsKey("paginas")) actual.setPaginas((Integer) cambios.get("paginas"));
        if (cambios.containsKey("publicadoEn")) actual.setPublicadoEn(LocalDateTime.parse((String) cambios.get("publicadoEn")));
        if (cambios.containsKey("generoLiterario")) actual.setGeneroLiterario(new GeneroLiterarioAttributeConverter().convertToEntityAttribute((String) cambios.get("generoLiterario")));
        return actual;
    }
}