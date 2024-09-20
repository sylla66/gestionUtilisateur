package perso.free.time.userManagement.repository;

import org.springframework.data.repository.CrudRepository;
import perso.free.time.userManagement.entities.Jwt;

import java.util.Optional;

public interface JwtRepository extends CrudRepository<Jwt,Integer> {
    Optional<Jwt> findByValeur(String value);
}
