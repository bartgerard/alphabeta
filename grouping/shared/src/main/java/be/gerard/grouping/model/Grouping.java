package be.gerard.grouping.model;

import be.gerard.common.value.BusinessKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

/**
 * Grouping
 *
 * @author bartgerard
 * @version v0.0.1
 */
@NoArgsConstructor(access = PRIVATE)
public final class Grouping {

    @NoArgsConstructor(access = PRIVATE)
    @AllArgsConstructor(access = PUBLIC, staticName = "of")
    @Getter
    public static class Level implements BusinessKey {
        private String level;
    }

    @NoArgsConstructor(access = PRIVATE)
    @AllArgsConstructor(access = PUBLIC, staticName = "of")
    @Getter
    public static class Strategy implements BusinessKey {
        private String strategy;
    }

    @NoArgsConstructor(access = PRIVATE)
    @AllArgsConstructor(access = PUBLIC, staticName = "of")
    @Getter
    public static class View implements BusinessKey {
        private String view;
    }

    @NoArgsConstructor(access = PRIVATE)
    @AllArgsConstructor(access = PUBLIC, staticName = "of")
    @Getter
    public static class LevelKey implements BusinessKey {
        private Grouping.Strategy strategy;
        private Grouping.Level name;
    }

}
