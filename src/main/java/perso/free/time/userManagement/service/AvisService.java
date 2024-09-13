package perso.free.time.userManagement.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import perso.free.time.userManagement.entities.Avis;
import perso.free.time.userManagement.repository.AvisRepository;

@AllArgsConstructor
@Service
public class AvisService {

    private final AvisRepository avisRepository;
    public void creer(Avis avis){
        this.avisRepository.save(avis);

    }

}
