package com.pharmasystem.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pharmasystem.model.Categoria;

import jakarta.validation.Valid;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
	
   public List<Categoria> findAllByNomeContainingIgnoreCase(@Valid @Param("nome")String nome);



}