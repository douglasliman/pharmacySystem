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

import com.pharmasystem.model.Produto;
import com.pharmasystem.repository.ProdutoRepository;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/produto")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	

	@GetMapping("/all")
	public ResponseEntity<List<Produto>> getAll() {
		return ResponseEntity.ok(produtoRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Produto> getProdutoPorId(@PathVariable Long id){
		return produtoRepository.findById(id).map(resposta ->  ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
		
	}
	
	@PostMapping("/create")
	public ResponseEntity<Produto> createProduto(@Valid @RequestBody Produto newProduto){
			return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(newProduto));
	}
	
	@PutMapping("/update")
	public ResponseEntity<Produto> put(@Valid @RequestBody Produto updatedProduto){
		
		Produto produtoUpdated = produtoRepository.save(updatedProduto);
		return produtoRepository.findById(updatedProduto.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.CREATED)
						.body(produtoUpdated))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		
		if (produto.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		produtoRepository.deleteById(id);
	}
	
	@GetMapping("/name/{nome}")
	public ResponseEntity<List<Produto>> getProdutoPorNome(@Valid @PathVariable String nome){
		return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoreCase(nome));
	}
	
}
