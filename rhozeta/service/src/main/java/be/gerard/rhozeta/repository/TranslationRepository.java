package be.gerard.rhozeta.repository;

import be.gerard.rhozeta.model.TranslationRecord;
import be.gerard.rhozeta.value.TranslationKey;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TranslationRepository
 *
 * @author bartgerard
 * @version v0.0.1
 */
public interface TranslationRepository extends JpaRepository<TranslationRecord, TranslationKey> {}
