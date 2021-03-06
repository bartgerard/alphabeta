package be.gerard.rhozeta.model;

import be.gerard.rhozeta.value.LanguageKey;
import be.gerard.rhozeta.value.TranslationKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

/**
 * Translation
 *
 * @author bartgerard
 * @version v0.0.1
 */
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
@Getter
@Builder
@EqualsAndHashCode
public class Translation {
    private TranslationKey key;
    private LanguageKey language;
    private String value;
}
