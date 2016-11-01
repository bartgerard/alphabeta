package be.gerard.grouping.model;

import be.gerard.grouping.value.Grouping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.io.Serializable;

import static be.gerard.grouping.config.Tables.GROUPING_VIEW;
import static javax.persistence.CascadeType.ALL;
import static lombok.AccessLevel.PRIVATE;

/**
 * GroupingViewRecord
 *
 * @author bartgerard
 * @version v0.0.1
 */
@Entity
@Table(name = GROUPING_VIEW)
@IdClass(GroupingViewRecord.Key.class)
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
@Getter
@Builder
public class GroupingViewRecord {
    @Id private Grouping.View view;

    @Id
    @ManyToOne(cascade = ALL)
    @JoinColumns(foreignKey = @ForeignKey(name = "fk_view2level"), value = {
            @JoinColumn(name = "strategy", referencedColumnName = "strategy"),
            @JoinColumn(name = "name", referencedColumnName = "name")
    })
    private GroupingLevelRecord level;

    public static class Key implements Serializable {
        private Grouping.View view;
        private GroupingLevelRecord level;
    }
}
