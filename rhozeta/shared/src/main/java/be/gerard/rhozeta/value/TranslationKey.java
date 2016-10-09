package be.gerard.rhozeta.value;

import lombok.Getter;
import lombok.NoArgsConstructor;
import value.BusinessKey;

import static lombok.AccessLevel.PRIVATE;

/**
 * TranslationKey
 *
 * @author bartgerard
 * @version v0.0.1
 */
@NoArgsConstructor(staticName = "of", access = PRIVATE)
@Getter
public class TranslationKey implements BusinessKey {
    private String key;
}
