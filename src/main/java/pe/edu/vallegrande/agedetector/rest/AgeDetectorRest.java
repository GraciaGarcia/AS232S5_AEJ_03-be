package pe.edu.vallegrande.agedetector.rest;

import pe.edu.vallegrande.agedetector.model.AgeDetector;
import pe.edu.vallegrande.agedetector.service.AgeDetectorService;
import pe.edu.vallegrande.agedetector.service.impl.AgeDetectorServiceImpl.AgeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/api/age-detector")
public class AgeDetectorRest {

    private final AgeDetectorService ageDetectorService;

    @Autowired
    public AgeDetectorRest(AgeDetectorService ageDetectorService) {
        this.ageDetectorService = ageDetectorService;
    }

    @GetMapping
    public Flux<AgeDetector> findAll(){
        return ageDetectorService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<AgeDetector> findById(@PathVariable Long id){
        return ageDetectorService.findById(id);
    }

    @PostMapping("/save")
    public Mono<AgeDetector> save(@RequestBody AgeRequest request) {
        return ageDetectorService.ageDetector(request.url());
    }

    @PutMapping("/update/{id}")
    public Mono<AgeDetector> update(@PathVariable Long id, @RequestBody AgeRequest request) {
        return ageDetectorService.update(request.url());
    }

}
