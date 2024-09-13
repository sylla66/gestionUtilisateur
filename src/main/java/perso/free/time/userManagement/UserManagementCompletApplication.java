package perso.free.time.userManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
//(exclude = {SecurityAutoConfiguration.class})propriété pour désactivé la sécurité
public class UserManagementCompletApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserManagementCompletApplication.class, args);
	}

}
