package be.gerard.rhozeta.model;

import be.gerard.rhozeta.value.ApplicationKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

/**
 * Application
 *
 * @author bartgerard
 * @version v0.0.1
 */
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
@Getter
@Builder
public class Application {
    private ApplicationKey key;
    @NonNull private List<Group> groups;
}
