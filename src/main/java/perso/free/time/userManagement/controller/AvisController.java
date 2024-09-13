package perso.free.time.userManagement.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import perso.free.time.userManagement.entities.Avis;
import perso.free.time.userManagement.service.AvisService;


@AllArgsConstructor
@RequestMapping("avis")
@RestController
public class AvisController {
    private final AvisService avisService;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void creer(@RequestBody Avis avis){
        this.avisService.creer(avis);
    }

}
