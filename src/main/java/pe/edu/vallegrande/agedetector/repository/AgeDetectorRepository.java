package pe.edu.vallegrande.agedetector.repository;

import pe.edu.vallegrande.agedetector.model.AgeDetector;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgeDetectorRepository extends ReactiveCrudRepository<AgeDetector, Long> {

}
