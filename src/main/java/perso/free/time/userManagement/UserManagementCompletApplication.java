package perso.free.time.userManagement;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import perso.free.time.userManagement.entities.Role;
import perso.free.time.userManagement.entities.Utilisateur;
import perso.free.time.userManagement.repository.UtilisateurRopository;
import perso.free.time.userManagement.service.ValidationService;


@AllArgsConstructor
@SpringBootApplication
//(exclude = {SecurityAutoConfiguration.class})propriété pour désactivé la sécurité
public class UserManagementCompletApplication implements CommandLineRunner {

	UtilisateurRopository utilisateurRopository;
	PasswordEncoder passwordEncoder;
	ValidationService validationService;
	public static void main(String[] args) {
		SpringApplication.run(UserManagementCompletApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Utilisateur admin = Utilisateur.builder()
				.nom("admin")
				.mdp(passwordEncoder.encode("admin"))
				.email("admin@gmail.com")
				.role(
						Role.builder()
								.libelle(TypeDeRole.ADMIN).build()
				)
				.build();
		Utilisateur manager = Utilisateur.builder()
				.nom("manager")
				.mdp(passwordEncoder.encode("manager"))
				.email("manager@gmail.com")
				.role(
						Role.builder()
								.libelle(TypeDeRole.MANAGER).build()
				)
				.build();
		admin = this.utilisateurRopository.findByEmail("admin@gmail.com").orElse(admin);
				this.utilisateurRopository.save(admin);
				this.validationService.enregistrer(admin);

		manager = this.utilisateurRopository.findByEmail("manager@gmail.com")
				.orElse(manager);
		this.utilisateurRopository.save(manager);
		this.validationService.enregistrer(manager);

	}
}
