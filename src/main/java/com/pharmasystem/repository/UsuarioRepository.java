package com.pharmasystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.pharmasystem.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
    public Optional<Usuario> findByUsuario(@Param("usuario") String usuario);
}
