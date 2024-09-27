package perso.free.time.userManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import perso.free.time.userManagement.TypeDeRole;
import perso.free.time.userManagement.entities.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByLibelle(TypeDeRole libelle);
}