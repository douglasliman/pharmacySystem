package com.pharmasystem.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.pharmasystem.model.Categoria;
import com.pharmasystem.repository.CategoriaRepository;

import jakarta.validation.Valid;


@RestController 
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {
	
	@Autowired
	private  CategoriaRepository categoriaRepository;
	
	
	@GetMapping("/all")
	public ResponseEntity<List<Categoria>> getAll() {
		return ResponseEntity.ok(categoriaRepository.findAll());
	}
	

	@GetMapping("/{id}")
	public ResponseEntity<Categoria> getCategoriaPorId(@PathVariable Long id){
		return categoriaRepository.findById(id).map(resposta ->  ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping("/create")
	public ResponseEntity<Categoria> createCategoria(@Valid @RequestBody Categoria newCategoria){
			Categoria CategoriaNovo  = categoriaRepository.save(newCategoria);	
			return ResponseEntity.status(HttpStatus.CREATED).body(CategoriaNovo);
	}
	
	@PutMapping("/update")
	public ResponseEntity<Categoria> put(@Valid @RequestBody Categoria updatedCategoria){
		
		Categoria CategoriaUpdated = categoriaRepository.save(updatedCategoria);
		return categoriaRepository.findById(updatedCategoria.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.CREATED)
						.body(CategoriaUpdated))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Categoria> Categoria = categoriaRepository.findById(id);
		
		if (Categoria.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		categoriaRepository.deleteById(id);
	}	
	
	@GetMapping("/name/{nome}")
	public ResponseEntity<List<Categoria>> getProdutoPorNome(@Valid @PathVariable String nome){
	    return ResponseEntity.ok(categoriaRepository.findAllByNomeContainingIgnoreCase(nome));
	}
	
}
