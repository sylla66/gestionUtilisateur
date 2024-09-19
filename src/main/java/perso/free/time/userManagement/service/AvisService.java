package perso.free.time.userManagement.service;


import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import perso.free.time.userManagement.entities.Avis;
import perso.free.time.userManagement.entities.Utilisateur;
import perso.free.time.userManagement.repository.AvisRepository;

@AllArgsConstructor
@Service
public class AvisService {

    private final AvisRepository avisRepository;
    public void creer(Avis avis){
        Utilisateur utilisateur = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        avis.setUtilisateur(utilisateur);
        this.avisRepository.save(avis);

    }

}
