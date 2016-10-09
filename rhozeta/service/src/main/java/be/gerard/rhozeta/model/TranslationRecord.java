package be.gerard.rhozeta.model;

import be.gerard.rhozeta.value.TranslationKey;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TranslationRecord
 *
 * @author bartgerard
 * @version v0.0.1
 */
@Entity
@Table(name = "translation")
public class TranslationRecord {

    @EmbeddedId
    private TranslationKey key;


}
