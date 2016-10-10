package be.gerard.rhozeta;

import be.gerard.rhozeta.model.TranslationRecord;
import be.gerard.rhozeta.repository.TranslationRepository;
import be.gerard.rhozeta.value.LanguageKey;
import be.gerard.rhozeta.value.TranslationKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * WhenRetrievingTranslations
 *
 * @author bartgerard
 * @version v0.0.1
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class WhenRetrievingTranslations {

    @Autowired private TranslationRepository translationRepository;

    @Test
    public void test() {
        TranslationRecord translation = TranslationRecord.of(TranslationKey.of("test"));
        translation.getValues().put(LanguageKey.of("en"), "test");
        translation.getValues().put(LanguageKey.of("nl"), "test");

        translationRepository.save(translation);

    }
}
