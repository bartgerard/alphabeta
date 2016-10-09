package be.gerard.rhozeta.value;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import value.BusinessKey;

import static lombok.AccessLevel.PRIVATE;

/**
 * LanguageKey
 *
 * @author bartgerard
 * @version v0.0.1
 */
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE, staticName = "of")
@Getter
@EqualsAndHashCode
public class LanguageKey implements BusinessKey {
    private String key;
}
