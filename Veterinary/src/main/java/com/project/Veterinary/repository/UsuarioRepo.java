package com.project.Veterinary.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.Veterinary.entities.Usuario;

public interface UsuarioRepo extends CrudRepository<Usuario,Long>{

List <Usuario> findByNombre(String nombre);
}
