package be.gerard.grouping.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

import static be.gerard.grouping.config.Tables.GROUPING_LINK;
import static javax.persistence.CascadeType.ALL;
import static lombok.AccessLevel.PRIVATE;

/**
 * GroupingLink
 *
 * @author bartgerard
 * @version v0.0.1
 */
@Entity
@Table(name = GROUPING_LINK)
@IdClass(GroupingLinkRecord.Key.class)
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
@Getter
@Builder
public class GroupingLinkRecord {
    @Id
    @ManyToOne(cascade = ALL)
    @JoinColumns({
            @JoinColumn(name = "from_strategy", referencedColumnName = "strategy"),
            @JoinColumn(name = "from_name", referencedColumnName = "name")
    })
    private GroupingLevelRecord from;

    @Id
    @ManyToOne(cascade = ALL)
    @JoinColumns({
            @JoinColumn(name = "to_strategy", referencedColumnName = "strategy"),
            @JoinColumn(name = "to_name", referencedColumnName = "name")
    })
    private GroupingLevelRecord to;

    public static class Key implements Serializable {
        private GroupingLevelRecord from;
        private GroupingLevelRecord to;
    }
}
