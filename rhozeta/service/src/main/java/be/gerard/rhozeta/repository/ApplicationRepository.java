package be.gerard.rhozeta.repository;

import be.gerard.rhozeta.model.ApplicationRecord;
import be.gerard.rhozeta.value.ApplicationKey;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TranslationRepository
 *
 * @author bartgerard
 * @version v0.0.1
 */
public interface ApplicationRepository extends JpaRepository<ApplicationRecord, ApplicationKey> {}
