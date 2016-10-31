package be.gerard.grouping.repository;

import be.gerard.grouping.model.GroupingLevelRecord;
import be.gerard.grouping.value.Grouping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.Set;

/**
 * GroupingLevelRepository
 *
 * @author bartgerard
 * @version v0.0.1
 */
public interface GroupingLevelRepository
        extends JpaRepository<GroupingLevelRecord, Grouping.LevelKey>, QueryDslPredicateExecutor<GroupingLevelRecord> {
    Set<GroupingLevelRecord> findAllByStrategy(Grouping.Strategy strategy);
}
