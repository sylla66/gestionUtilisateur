package perso.free.time.userManagement.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import perso.free.time.userManagement.entities.Avis;
import perso.free.time.userManagement.entities.Utilisateur;
import perso.free.time.userManagement.service.UtilisateurService;

import java.util.List;

@AllArgsConstructor
@RequestMapping("utilisateur")
@RestController
public class UtilisateurController {

    UtilisateurService utilisateurService;


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(path = "/all")
    public ResponseEntity<List<Utilisateur>> allUtilisateurs(){

        return new ResponseEntity<>(this.utilisateurService.liste(), HttpStatus.OK);
    }
}
