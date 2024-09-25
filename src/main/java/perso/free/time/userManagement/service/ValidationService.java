package perso.free.time.userManagement.service;


import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import perso.free.time.userManagement.entities.Utilisateur;
import perso.free.time.userManagement.entities.Validation;
import perso.free.time.userManagement.repository.UtilisateurRopository;
import perso.free.time.userManagement.repository.ValidationRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.Random;

import static java.time.temporal.ChronoUnit.MINUTES;

@Transactional
@Slf4j
@AllArgsConstructor
@Service
public class ValidationService {

    private ValidationRepository validationRepository;
    private NotificationService notificationService;
    private UtilisateurRopository utilisateurRopository;

    public void enregistrer(Utilisateur utilisateur) {

        // Chercher directement la validation associée à l'utilisateur par son email
        Optional<Validation> validationOpt = validationRepository.findByUtilisateurEmail(utilisateur.getEmail());

        Validation validation;
        if (validationOpt.isPresent()) {
            // Si la validation existe, la récupérer
            validation = validationOpt.get();
        } else {
            // Si aucune validation n'est trouvée, cela signifie que l'utilisateur n'existe pas
            throw new RuntimeException("Impossible de modifier le mot de passe d'un utilisateur non existant.");
        }

        // Mettre à jour ou initialiser les champs de la validation
        Instant creation = Instant.now();
        validation.setCreation(creation);
        validation.setActivation(creation);

        Instant expiration = creation.plus(10, MINUTES);
        validation.setExpire(expiration);

        Random random = new Random();
        int randomInteger = random.nextInt(999999);
        String code = String.format("%06d", randomInteger);
        validation.setCode(code);
        this.validationRepository.save(validation);
        this.notificationService.envoyer(validation);
    }

    public Validation lireEnFonctionDuCode(String code) {
        return this.validationRepository.findByCode(code).orElseThrow(() -> new RuntimeException("Votre code est invalide"));
    }


    @Scheduled(cron = "*/30 * * * * *")
    public void nettoyerTable() {
        final Instant now = Instant.now();
        log.info("Suppression des token à {}", now);
        this.validationRepository.deleteAllByExpireBefore(now);
    }
}
