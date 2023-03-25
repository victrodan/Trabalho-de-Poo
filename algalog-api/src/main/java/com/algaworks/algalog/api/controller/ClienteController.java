package com.algaworks.algalog.api.controller;

import java.util.List;


import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algalog.domain.model.Cliente;
import com.algaworks.algalog.domain.repository.ClienteRepository;
import com.algaworks.algalog.domain.service.CatalogoClienteService;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	
	private ClienteRepository clienteRepository;
	private CatalogoClienteService catalogoClienteService;
	@GetMapping()
  public List<Cliente> listar() {
		return clienteRepository.findAll();
  }
	@GetMapping("{clienteid}")
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteid) {
		Optional<Cliente> cliente = clienteRepository.findById(clienteid);
		if (cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}
		 return ResponseEntity.notFound().build();
	}
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar(@Valid@RequestBody Cliente cliente) {
		return catalogoClienteService.salvar(cliente);
				}
	@PutMapping("{clienteid}")
	public ResponseEntity<Cliente>atualizar(@Valid@PathVariable Long clienteid,@RequestBody Cliente cliente){
		if(!clienteRepository.existsById(clienteid)) {
			return ResponseEntity.notFound().build();
		}
		cliente.setId(clienteid);
		cliente=catalogoClienteService.salvar(cliente);
		return ResponseEntity.ok(cliente);
		
	}
	@DeleteMapping("/{clienteid}")
	public ResponseEntity<Void> remover(@PathVariable Long clienteid){
		if(!clienteRepository.existsById(clienteid)) {
			return ResponseEntity.notFound().build();
		}
		catalogoClienteService.excluir(clienteid);
		return ResponseEntity.noContent().build();
	}
}
