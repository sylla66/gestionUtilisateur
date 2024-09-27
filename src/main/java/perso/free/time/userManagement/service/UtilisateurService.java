package perso.free.time.userManagement.service;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;


import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import perso.free.time.userManagement.TypeDeRole;
import perso.free.time.userManagement.entities.Role;
import perso.free.time.userManagement.entities.Utilisateur;
import perso.free.time.userManagement.entities.Validation;
import perso.free.time.userManagement.repository.RoleRepository;
import perso.free.time.userManagement.repository.UtilisateurRopository;
import perso.free.time.userManagement.repository.ValidationRepository;


@AllArgsConstructor
@Service
public class UtilisateurService  implements UserDetailsService {

    private UtilisateurRopository utilisateurRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private ValidationService validationService;
    private ValidationRepository validationRepository;
    private RoleRepository roleRepository;
    public void inscription(Utilisateur utilisateur) {

        if(!utilisateur.getEmail().contains("@")) {
            throw  new RuntimeException("Votre mail invalide");
        }
        if(!utilisateur.getEmail().contains(".")) {
            throw  new RuntimeException("Votre mail invalide");
        }

        Optional<Utilisateur> utilisateurOptional = this.utilisateurRepository.findByEmail(utilisateur.getEmail());
        if(utilisateurOptional.isPresent()) {
            throw new RuntimeException("Votre mail est déjà utilisé");
        }
        String mdpCrypte = this.passwordEncoder.encode(utilisateur.getMdp());
        utilisateur.setMdp(mdpCrypte);

        // Vérification et création du rôle
        if (utilisateur.getRole() != null && utilisateur.getRole().getLibelle() != null) {
            try {
                // Convertir directement le libellé en TypeDeRole sans toUpperCase()
                TypeDeRole typeDeRole = utilisateur.getRole().getLibelle();

                // Créer un nouvel objet Role basé sur l'énumération
                Role roleUtilisateur = new Role();
                roleUtilisateur.setLibelle(typeDeRole);
                utilisateur.setRole(roleUtilisateur);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Rôle invalide");
            }
        } else {
            throw new RuntimeException("Le rôle est requis");
        }

        utilisateur = this.utilisateurRepository.save(utilisateur);
        this.validationService.enregistrer(utilisateur);
    }

    public void activation(Map<String, String> activation) {
        Validation validation = this.validationService.lireEnFonctionDuCode(activation.get("code"));
        if(Instant.now().isAfter(validation.getExpire())){
            throw  new RuntimeException("Votre code a expiré");
        }
        Utilisateur utilisateurActiver = this.utilisateurRepository.findById(validation.getUtilisateur().getId()).orElseThrow(() -> new RuntimeException("Utilisateur inconnu"));
        utilisateurActiver.setActif(true);
        validationRepository.save(validation);
        this.utilisateurRepository.save(utilisateurActiver);
    }

    @Override
    public Utilisateur loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.utilisateurRepository
                .findByEmail(username)
                .orElseThrow(() -> new  UsernameNotFoundException("Aucun utilisateur ne corespond à cet identifiant"));
    }

    public void modifierMotDePasse(Map<String, String> parametres) {
        Utilisateur utilisateur = this.loadUserByUsername(parametres.get("email"));
        this.validationService.enregistrer(utilisateur);
    }

    public void nouveauMotDePasse(Map<String, String> parametres) {
        Utilisateur utilisateur = this.loadUserByUsername(parametres.get("email"));
        final Validation validation = validationService.lireEnFonctionDuCode(parametres.get("code"));
        if(validation.getUtilisateur().getEmail().equals(utilisateur.getEmail())) {
            String mdpCrypte = this.passwordEncoder.encode(parametres.get("password"));
            utilisateur.setMdp(mdpCrypte);
            this.utilisateurRepository.save(utilisateur);
        }
    }

    public List<Utilisateur> liste() {

        final Iterable<Utilisateur> utilisateurIterable = this.utilisateurRepository.findAll();
        ArrayList<Utilisateur> utilisateurs = new ArrayList();
        for (Utilisateur utilisateur: utilisateurIterable) {
            utilisateurs.add(utilisateur);
            
        }
        return utilisateurs;
    }
}
