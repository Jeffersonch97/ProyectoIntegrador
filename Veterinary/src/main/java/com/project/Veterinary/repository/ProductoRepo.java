package com.project.Veterinary.repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.Veterinary.entities.Producto;
public interface ProductoRepo extends CrudRepository<Producto, Long>{
	List<Producto> findByNombre(String nombre);
}
