package pe.edu.vallegrande.agedetector.service;

import pe.edu.vallegrande.agedetector.model.AgeDetector;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AgeDetectorService {

    Flux<AgeDetector> findAll();

    Mono<AgeDetector> findById(Long id);

    Mono<AgeDetector> save(String imageUrl);

    Mono<AgeDetector> update(String imageUrl);

    Mono<AgeDetector> ageDetector(String imageUrl);

}
