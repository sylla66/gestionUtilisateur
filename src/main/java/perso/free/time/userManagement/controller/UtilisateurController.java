package perso.free.time.userManagement.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import perso.free.time.userManagement.dto.AuthentificationDto;
import perso.free.time.userManagement.entities.Utilisateur;
import perso.free.time.userManagement.security.JwtService;
import perso.free.time.userManagement.service.UtilisateurService;

import java.util.Map;


@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class UtilisateurController {

    private UtilisateurService utilisateurService;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;



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

    @PostMapping(path = "deconnexion")
    public void deconnexion(){
        this.jwtService.deconnexion();
        log.info("Activation");
    }
    @PostMapping(path = "connexion")
    public Map<String, String> connexion(@RequestBody AuthentificationDto authentificationDto){
        log.info("Connexion");
        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authentificationDto.username(), authentificationDto.password())
        );
        if (authenticate.isAuthenticated()){
            log.info("resultat {}", authenticate.isAuthenticated());
            return this.jwtService.generate(authentificationDto.username());
        }

        return null;
    }
}
