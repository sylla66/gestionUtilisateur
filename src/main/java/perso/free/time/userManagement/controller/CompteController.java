package perso.free.time.userManagement.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import perso.free.time.userManagement.dto.AuthentificationDTO;
import perso.free.time.userManagement.entities.Utilisateur;
import perso.free.time.userManagement.security.JwtService;
import perso.free.time.userManagement.service.UtilisateurService;

import java.util.Map;


@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class CompteController {


    private AuthenticationManager authenticationManager;
    private UtilisateurService utilisateurService;
    private JwtService jwtService;

    @PostMapping(path = "inscription")
    public void inscription(@RequestBody Utilisateur utilisateur) {
        log.info("Inscription");
        this.utilisateurService.inscription(utilisateur);
    }


    @PostMapping(path = "activation")
    public void activation(@RequestBody Map<String, String> activation) {
        this.utilisateurService.activation(activation);
    }
    @PostMapping(path = "modifier-mot-de-passe")
    public void modifierMotDePasse(@RequestBody Map<String, String> activation) {
        this.utilisateurService.modifierMotDePasse(activation);
    }

    @PostMapping(path = "nouveau-mot-de-passe")
    public void nouveauMotDePasse(@RequestBody Map<String, String> activation) {
        this.utilisateurService.nouveauMotDePasse(activation);
    }



    @PostMapping(path = "refresh-token")
    public @ResponseBody Map<String, String> refreshToken(@RequestBody Map<String, String> refreshTokenRequest) {
        return this.jwtService.refreshToken(refreshTokenRequest);
    }

    @PostMapping(path = "deconnexion")
    public void deconnexion() {
        this.jwtService.deconnexion();
    }
    @PostMapping(path = "connexion")
    public Map<String, String> connexion(@RequestBody AuthentificationDTO authentificationDTO) {
        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authentificationDTO.username(), authentificationDTO.password())
        );

        if(authenticate.isAuthenticated()) {
            return this.jwtService.generate(authentificationDTO.username());
        }
        return null;
    }
}
