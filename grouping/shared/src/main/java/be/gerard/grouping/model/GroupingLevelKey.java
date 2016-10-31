package be.gerard.grouping.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

/**
 * GroupingLevelKey
 *
 * @author bartgerard
 * @version v0.0.1
 */
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(staticName = "of", access = PUBLIC)
@Getter
public class GroupingLevelKey implements Serializable {
    private Strategy strategy;
    private Level name;
}
