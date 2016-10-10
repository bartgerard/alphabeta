package be.gerard.rhozeta.model;

import be.gerard.rhozeta.value.GroupKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

/**
 * GroupRecord
 *
 * @author bartgerard
 * @version v0.0.1
 */
@Entity
@Table(name = "group")
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(staticName = "of", access = PRIVATE)
@Getter
@Builder
public class GroupRecord {

    @EmbeddedId private GroupKey key;

    @OneToMany
    @JoinColumn(name = "group_key", foreignKey = @ForeignKey(name = "fk_translation2group"))
    private final List<TranslationRecord> translations = new ArrayList<>();

}
