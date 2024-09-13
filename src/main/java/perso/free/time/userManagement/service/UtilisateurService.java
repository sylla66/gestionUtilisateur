package perso.free.time.userManagement.service;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;


import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import perso.free.time.userManagement.TypeDeRole;
import perso.free.time.userManagement.entities.Role;
import perso.free.time.userManagement.entities.Utilisateur;
import perso.free.time.userManagement.entities.Validation;
import perso.free.time.userManagement.repository.UtilisateurRopository;


@AllArgsConstructor
@Service
public class UtilisateurService {

    private UtilisateurRopository utilisateurRopository;
    private BCryptPasswordEncoder passwordEncoder;
    private ValidationService validationService;
    public  void inscription(Utilisateur utilisateur){
        this.validateEmail(utilisateur.getEmail());
        Optional<Utilisateur> utilisateurOptional = this.utilisateurRopository.findByEmail(utilisateur.getEmail());
        if(utilisateurOptional.isPresent()){
            throw new RuntimeException("votre mail est déja utilisé");
        }
        String mdpCrypte = this.passwordEncoder.encode(utilisateur.getMdp());
        utilisateur.setMdp(mdpCrypte);
        Role roleUtilisateur = new Role();
        roleUtilisateur.setLibelle(TypeDeRole.USER);
        utilisateur.setRole(roleUtilisateur);
        Utilisateur saveUser = this.utilisateurRopository.save(utilisateur);
        this.validationService.enregistrerUtilisateur(saveUser);
    }
    public void validateEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"; // Regex basique pour les emails
        Pattern pattern = Pattern.compile(emailRegex);

        if (email == null || !pattern.matcher(email).matches()) {
            throw new RuntimeException("Votre email est invalide");
        }
    }

    public void activation(Map<String, String> activation) {
        Validation validation = this.validationService.lireEnfonctionDuCode(activation.get("code"));

        if (Instant.now().isAfter(validation.getExpire())){
            throw new RuntimeException("votre code a expiré");
        }
        Utilisateur utilisateurActiver = this.utilisateurRopository.findById(validation.getUtilisateur().getId()).orElseThrow(()->new RuntimeException("Utilisateur inconnu"));
        utilisateurActiver.setActif(true);
        this.utilisateurRopository.save(utilisateurActiver);

    }
}
