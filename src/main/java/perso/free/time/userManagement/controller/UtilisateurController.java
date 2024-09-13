package perso.free.time.userManagement.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import perso.free.time.userManagement.entities.Utilisateur;
import perso.free.time.userManagement.service.UtilisateurService;

import java.util.Map;


@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class UtilisateurController {

    private UtilisateurService utilisateurService;


    @PostMapping(path = "inscription")
    public void inscription(@RequestBody Utilisateur utilisateur){
        this.utilisateurService.inscription(utilisateur);
        log.info("Inscription");

    }

    @PostMapping(path = "activation")
    public void activation(@RequestBody Map<String, String>  activation){
        this.utilisateurService.activation(activation);
        log.info("Activation");

    }
}
