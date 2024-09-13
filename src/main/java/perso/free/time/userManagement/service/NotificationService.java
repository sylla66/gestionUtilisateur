package perso.free.time.userManagement.service;


import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import perso.free.time.userManagement.entities.Validation;


@AllArgsConstructor
@Service
public class NotificationService {

    JavaMailSender javaMailSender;

    public void envoyer(Validation validation){

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("no-reply@gmail.com");
        mailMessage.setTo(validation.getUtilisateur().getEmail());
        mailMessage.setSubject("Votre code d'activation");
        String text = String.format("Bonjour %s, <br/> votre code  d'activation est %s, le code expire dans 5 minutes. <br/> " +
                "Cordialement, ",validation.getUtilisateur().getNom(),
                validation.getCode());

        mailMessage.setText(text);
        javaMailSender.send(mailMessage);

    }
}
