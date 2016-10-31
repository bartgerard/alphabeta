package be.gerard.grouping.model;

import be.gerard.grouping.value.Grouping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import static be.gerard.grouping.config.Tables.GROUPING_VIEW;
import static lombok.AccessLevel.PRIVATE;

/**
 * GroupingViewRecord
 *
 * @author bartgerard
 * @version v0.0.1
 */
@Entity
@Table(name = GROUPING_VIEW)
@IdClass(Grouping.ViewKey.class)
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
@Getter
@Builder
public class GroupingViewRecord {
    @Id private Grouping.View view;
    @Id private Grouping.Level level;
}
