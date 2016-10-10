package be.gerard.rhozeta.model;

import be.gerard.rhozeta.value.ApplicationKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
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
@Table(name = "application")
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(staticName = "of", access = PRIVATE)
@Getter
@Builder
public class ApplicationRecord {

    @EmbeddedId private ApplicationKey key;

    @OneToMany
    @JoinColumn(name = "application_key", foreignKey = @ForeignKey(name = "fk_group2application"))
    @OrderColumn(name = "group_index")
    private final List<GroupRecord> translations = new ArrayList<>();

}
