package be.gerard.grouping.repository;

import be.gerard.grouping.model.Grouping;
import be.gerard.grouping.model.GroupingLevelRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * GroupingLevelRepository
 *
 * @author bartgerard
 * @version v0.0.1
 */
public interface GroupingLevelRepository
        extends JpaRepository<GroupingLevelRecord, Grouping.Level>, QueryDslPredicateExecutor<GroupingLevelRecord> {}
