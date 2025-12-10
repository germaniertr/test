package core.serialisation.json;

import core.domain.Audit;
import core.domain.AuditBase;
import jakarta.json.bind.adapter.JsonbAdapter;
import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import java.time.Instant;

/**
 *
 * @author dominique huguenin (dominique.huguenin@rpn.ch)
 */
public class AuditJson implements Audit {

    private final Audit audit;

    public AuditJson(final Audit audit) {
        if (audit == null) {
            throw new NullPointerException();
        }

        this.audit = audit;
    }

    @JsonbCreator
    public AuditJson(
            final @JsonbProperty("dateCreation")
            @JsonbTypeAdapter(InstantAdapter.class) Instant dateCreation,
            final @JsonbProperty("userCreation") String userCreation,
            final @JsonbProperty("dateModification")
            @JsonbTypeAdapter(InstantAdapter.class) Instant dateModification,
            final @JsonbProperty("userModification") String userModification
    ) {
        this.audit = AuditBase.builder()
                .dateCreation(dateCreation)
                .userCreation(userCreation)
                .dateModification(dateModification)
                .userModification(userModification)
                .build();
    }

    @JsonbProperty("dateCreation")
    @JsonbTypeAdapter(InstantAdapter.class)
    @Override
    public Instant getDateCreation() {
        return this.audit.getDateCreation();
    }

    @JsonbProperty("userCreation")
    @Override
    public String getUserCreation() {
        return this.audit.getUserCreation();
    }

    @JsonbProperty("dateModification")
    @JsonbTypeAdapter(InstantAdapter.class)
    @Override
    public Instant getDateModification() {
        return this.audit.getDateModification();
    }

    @JsonbProperty("userModification")
    @Override
    public String getUserModification() {
        return this.audit.getUserModification();
    }

    @Override
    public String toString() {
        return "AuditJson{" + "audit=" + audit + '}';
    }

    @Override
    public int hashCode() {
        return this.audit.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        return this.audit.equals(obj);
    }

    public static class InstantAdapter implements JsonbAdapter<Instant, String> {

        @Override
        public String adaptToJson(final Instant base) throws Exception {
            return base.toString();
        }

        @Override
        public Instant adaptFromJson(final String json) throws Exception {
            return Instant.parse(json);
        }

    }

    public static class Adapter implements JsonbAdapter<Audit, AuditJson> {

        @Override
        public AuditJson adaptToJson(final Audit base) throws Exception {
            return new AuditJson(base);
        }

        @Override
        public Audit adaptFromJson(final AuditJson json) throws Exception {
            return json;
        }

    }

}
