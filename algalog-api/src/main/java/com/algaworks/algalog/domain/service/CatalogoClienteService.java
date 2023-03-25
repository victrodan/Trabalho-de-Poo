package com.algaworks.algalog.domain.service;
 import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.algaworks.algalog.domain.exception.NegocioException;
import com.algaworks.algalog.domain.model.Cliente;
import com.algaworks.algalog.domain.repository.ClienteRepository;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
public class CatalogoClienteService {
 private ClienteRepository clienteRepository;
 public Cliente buscar(Long clienteid) {
	 return  clienteRepository.findById(clienteid)
			  .orElseThrow(()-> new NegocioException("Cliente nao encontrado"));
 }
 @Transactional
 public Cliente salvar(Cliente cliente) {
	 boolean emailEmUso = clienteRepository.findByEmail( cliente.getEmail())
			 .stream()
			 .anyMatch(clienteExistente ->!clienteExistente.equals(cliente));
	 if(emailEmUso) {
		 throw new NegocioException("Ja existe um cliente cadastrado com esse email");
		 
	 }
	 return clienteRepository.save(cliente);
	 
 }
 @Transactional
 public void excluir(Long clienteid) {
	 clienteRepository.deleteById(clienteid);
 }
 }
