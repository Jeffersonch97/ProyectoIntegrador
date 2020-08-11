package com.project.Veterinary.repository;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.project.Veterinary.entities.Mascota;
public interface MascotaRepo extends CrudRepository<Mascota, Long>{
List<Mascota> findByNombre(String nombre);
}
