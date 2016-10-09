package be.gerard.rhozeta.model;

import be.gerard.rhozeta.value.GroupKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

/**
 * TranslationGroup
 *
 * @author bartgerard
 * @version v0.0.1
 */
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
@Getter
@Builder
public class Group {
    private GroupKey key;
    @NonNull private Set<Translation> translations;
}
