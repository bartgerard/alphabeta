package be.gerard.grouping.repository;

import be.gerard.grouping.model.GroupingViewRecord;
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
public interface GroupingViewRepository
        extends JpaRepository<GroupingViewRecord, Grouping.ViewKey>, QueryDslPredicateExecutor<GroupingViewRecord> {
    Set<GroupingViewRecord> findAllByView(Grouping.View view);
}
