package perso.free.time.userManagement.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import perso.free.time.userManagement.entities.Avis;
import perso.free.time.userManagement.service.AvisService;

import java.util.List;


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
    @GetMapping(path = "/all")
    public ResponseEntity<List<Avis>> allPayments(){

        return new ResponseEntity<>(this.avisService.liste(), HttpStatus.OK);
    }

}
