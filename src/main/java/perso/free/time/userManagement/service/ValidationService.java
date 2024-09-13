package perso.free.time.userManagement.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import perso.free.time.userManagement.entities.Utilisateur;
import perso.free.time.userManagement.entities.Validation;
import perso.free.time.userManagement.repository.ValidationRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@AllArgsConstructor
@Service
public class ValidationService {

    private ValidationRepository validationRepository;
    private NotificationService notificationService;

    public   void enregistrerUtilisateur(Utilisateur utilisateur){
        Validation validation = new Validation();
        validation.setUtilisateur(utilisateur);
        Instant creation = Instant.now();
        Instant expiration = creation.plus(5, ChronoUnit.MINUTES);
        Random random = new Random();
        int randomInteger = random.nextInt(999999);
        String code = String.format("%06d", randomInteger);
        validation.setCreation(creation);
        validation.setExpire(expiration);
        validation.setCode(code);
        this.validationRepository.save(validation);
        this.notificationService.envoyer(validation);
    }

    public Validation lireEnfonctionDuCode(String code){
        return  this.validationRepository.getByCode(code).orElseThrow(()->new RuntimeException("votre code est invalide"));


    }
}
