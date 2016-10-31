package be.gerard.grouping.repository;

import be.gerard.grouping.model.GroupingLevelRecord;
import be.gerard.grouping.model.Level;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * GroupingLevelRepository
 *
 * @author bartgerard
 * @version v0.0.1
 */
public interface GroupingLevelRepository extends JpaRepository<GroupingLevelRecord, Level> {}
