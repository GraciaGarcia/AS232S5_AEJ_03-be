package pe.edu.vallegrande.agedetector.service.impl;

import pe.edu.vallegrande.agedetector.model.AgeDetector;
import pe.edu.vallegrande.agedetector.service.AgeDetectorService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import pe.edu.vallegrande.agedetector.repository.AgeDetectorRepository;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AgeDetectorServiceImpl implements AgeDetectorService {

    private final AgeDetectorRepository ageDetectorRepository;

    private final WebClient webClient;

    AgeDetector ageDetector = new AgeDetector();

    LocalDateTime now = LocalDateTime.now();

    @Autowired
    public AgeDetectorServiceImpl(AgeDetectorRepository ageDetectorRepository, WebClient webClient) {
        this.ageDetectorRepository = ageDetectorRepository;
        this.webClient = webClient;
    }

    @Override
    public Flux<AgeDetector> findAll() {
        log.info("Mostrando datos");
        return ageDetectorRepository.findAll();
    }

    @Override
    public Mono<AgeDetector> findById(Long id) {
        log.info("Mostrando datos por ID = " + id);
        return ageDetectorRepository.findById(id);
    }

    @Override
    public Mono<AgeDetector> save(String imageUrl) {
        ageDetector.setCreationDate(now);
        log.info("Registrando = " + ageDetector.toString());
        return ageDetector(imageUrl);
    }

    @Override
    public Mono<AgeDetector> update(String imageUrl) {
        ageDetector.setUpdateDate(now);
        log.info("Actualizando = " + ageDetector.toString());
        return ageDetector(imageUrl);
    }

    public record AgeRequest(String url) {}

    @Override
    public Mono<AgeDetector> ageDetector(String imageUrl) {
        return webClient.post()
                .uri("/age-detection")
                .bodyValue(new AgeRequest(imageUrl))
                .retrieve()
                .bodyToMono(String.class)
                .map(json -> {
                    // Extraemos "age" del primer objeto del array
                    String ageStr = json.replaceAll(".*\"age\":(\\d+).*", "$1");
                    return Integer.parseInt(ageStr);
                })
                .flatMap(age -> {
                    AgeDetector ageDetectorr = new AgeDetector();
                    ageDetectorr.setImageUrl(imageUrl);
                    ageDetectorr.setAge(age);
                    ageDetectorr.setCreationDate(now);
                    ageDetectorr.setUpdateDate(now);
                    log.info("Registrando = " + ageDetectorr.toString());
                    return ageDetectorRepository.save(ageDetectorr);
                });
    }

}
