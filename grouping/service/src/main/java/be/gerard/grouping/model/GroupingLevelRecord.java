package be.gerard.grouping.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import static lombok.AccessLevel.PRIVATE;

/**
 * GroupingLevel
 *
 * @author bartgerard
 * @version v0.0.1
 */
@Entity
@Table(name = "grouping_level")
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
@Getter
@Builder
public class GroupingLevelRecord {
    @EmbeddedId private GroupingLevelKey key;
}
