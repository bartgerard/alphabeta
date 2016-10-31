package be.gerard.grouping;

import be.gerard.grouping.model.GroupingLevelRecord;
import be.gerard.grouping.model.QGroupingLevelRecord;
import be.gerard.grouping.repository.GroupingLevelRepository;
import be.gerard.grouping.value.Grouping;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * GroupingRepositoryTest
 *
 * @author bartgerard
 * @version v0.0.1
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class GroupingRepositoryTest {

    @Autowired private GroupingLevelRepository groupingLevelRepository;

    @Test
    public void test() {
        final GroupingLevelRecord gl1 = GroupingLevelRecord.builder()
                                                           .strategy(Grouping.Strategy.of("PC"))
                                                           .level(Grouping.Level.of("SERVICE_PARTS"))
                                                           .build();
        groupingLevelRepository.save(gl1);

        final GroupingLevelRecord gl2 = groupingLevelRepository.findOne(QGroupingLevelRecord.groupingLevelRecord.strategy.eq(Grouping.Strategy.of("PC"))
                                                                                                                         .and(QGroupingLevelRecord.groupingLevelRecord.level.eq(Grouping.Level.of("SERVICE_PARTS"))));
        assertThat(gl2, notNullValue());

        final GroupingLevelRecord gl3 = groupingLevelRepository.findOne(Grouping.LevelKey.of(Grouping.Strategy.of("PC"), Grouping.Level.of("SERVICE_PARTS")));
        assertThat(gl3, notNullValue());

        final Set<GroupingLevelRecord> gl4 = groupingLevelRepository.findAllByStrategy(Grouping.Strategy.of("PC"));
        assertThat(gl4, not(empty()));
    }
}
