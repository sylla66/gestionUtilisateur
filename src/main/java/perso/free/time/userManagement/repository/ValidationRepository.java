package perso.free.time.userManagement.repository;

import org.springframework.data.repository.CrudRepository;
import perso.free.time.userManagement.entities.Utilisateur;
import perso.free.time.userManagement.entities.Validation;

import java.time.Instant;
import java.util.Optional;

public interface ValidationRepository extends CrudRepository<Validation, Integer> {
    Optional<Validation> findByCode(String code);
    void deleteAllByExpireBefore(Instant now);

    //Validation findByUtilisateur(Utilisateur utilisateurExistant);
    Optional<Validation> findByUtilisateurEmail(String email);
}
