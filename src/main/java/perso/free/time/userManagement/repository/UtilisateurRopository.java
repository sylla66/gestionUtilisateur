package perso.free.time.userManagement.repository;

import org.springframework.data.repository.CrudRepository;
import perso.free.time.userManagement.entities.Utilisateur;

import java.util.Optional;

public interface UtilisateurRopository extends CrudRepository<Utilisateur, Integer> {
    Optional<Utilisateur> findByEmail(String email);
}
