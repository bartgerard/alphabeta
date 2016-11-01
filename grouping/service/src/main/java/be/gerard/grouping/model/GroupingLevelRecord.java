package be.gerard.grouping.model;

import be.gerard.grouping.value.Grouping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import static be.gerard.grouping.config.Tables.GROUPING_LEVEL;
import static lombok.AccessLevel.PRIVATE;

/**
 * GroupingLevel
 *
 * @author bartgerard
 * @version v0.0.1
 */
@Entity
@Table(name = GROUPING_LEVEL)
@IdClass(Grouping.Level.class)
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
@Getter
@Setter
@Builder
public class GroupingLevelRecord {
    @Id private Grouping.Strategy strategy;
    @Id private Grouping.Name name;
}
