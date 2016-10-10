package be.gerard.rhozeta.repository;

import be.gerard.rhozeta.model.GroupRecord;
import be.gerard.rhozeta.value.GroupKey;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TranslationRepository
 *
 * @author bartgerard
 * @version v0.0.1
 */
public interface GroupRepository extends JpaRepository<GroupRecord, GroupKey> {}
