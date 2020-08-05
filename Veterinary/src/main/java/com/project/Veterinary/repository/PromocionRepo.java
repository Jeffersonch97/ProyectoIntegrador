package com.project.Veterinary.repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.project.Veterinary.entities.Promocion;

public interface PromocionRepo extends CrudRepository <Promocion, Long>{
	List<Promocion> findByNombre(String nombre);

}
