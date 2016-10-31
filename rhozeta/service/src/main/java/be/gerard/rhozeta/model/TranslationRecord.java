package be.gerard.rhozeta.model;

import be.gerard.rhozeta.value.LanguageKey;
import be.gerard.rhozeta.value.TranslationKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

/**
 * TranslationRecord
 *
 * @author bartgerard
 * @version v0.0.1
 */
@Entity
@Table(name = "translation")
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = PUBLIC, staticName = "of")
@Getter
@Builder
public class TranslationRecord {

    @EmbeddedId private TranslationKey key;

    @ElementCollection
    @CollectionTable(name = "translation_value", joinColumns = @JoinColumn(name = "translation_key"), foreignKey = @ForeignKey(name = "fk_value2translation"))
    @MapKeyColumn(name = "language_key")
    @Column(name = "be/gerard/common/value")
    private final Map<LanguageKey, String> values = new HashMap<>();

}
