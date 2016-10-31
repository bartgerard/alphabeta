package be.gerard.grouping;

import be.gerard.grouping.model.Grouping;
import be.gerard.grouping.model.GroupingLevelRecord;
import be.gerard.grouping.repository.GroupingLevelRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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
        final GroupingLevelRecord groupingLevel = GroupingLevelRecord.builder()
                                                                     .key(Grouping.LevelKey.of(Grouping.Strategy.of("PC"), Grouping.Level.of("SERVICE_PARTS")))
                                                                     .build();

        groupingLevelRepository.save(groupingLevel);
    }
}
