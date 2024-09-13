package perso.free.time.userManagement.repository;

import org.springframework.data.repository.CrudRepository;
import perso.free.time.userManagement.entities.Validation;

import java.util.Optional;

public interface ValidationRepository extends CrudRepository<Validation, Integer> {
    Optional<Validation> getByCode(String code);
}
