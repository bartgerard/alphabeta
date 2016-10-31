package be.gerard.grouping;

import be.gerard.grouping.model.Grouping;
import be.gerard.grouping.model.GroupingLevelRecord;
import be.gerard.grouping.model.QGroupingLevelRecord;
import be.gerard.grouping.repository.GroupingLevelRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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
        final Grouping.LevelKey key1 = Grouping.LevelKey.of(Grouping.Strategy.of("PC"), Grouping.Level.of("SERVICE_PARTS"));
        final GroupingLevelRecord gl1 = GroupingLevelRecord.builder()
                                                           .key(key1)
                                                           .build();

        groupingLevelRepository.save(gl1);

        final Grouping.LevelKey key2 = Grouping.LevelKey.of(Grouping.Strategy.of("PC"), Grouping.Level.of("SERVICE_PARTS"));
        final GroupingLevelRecord gl2 = groupingLevelRepository.findOne(QGroupingLevelRecord.groupingLevelRecord.key.eq(key2));
        assertThat(gl2, notNullValue());
    }
}
