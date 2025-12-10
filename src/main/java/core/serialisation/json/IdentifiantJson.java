package core.serialisation.json;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import core.domain.Identifiant;
import core.domain.IdentifiantBase;
import jakarta.json.bind.adapter.JsonbAdapter;

/**
 *
 * @author dominique huguenin (dominique.huguenin@rpn.ch)
 */
public class IdentifiantJson implements Identifiant {

    private final Identifiant identifiant;

    public IdentifiantJson(final Identifiant identifiant) {
        if (identifiant == null) {
            throw new NullPointerException();
        }
        this.identifiant = identifiant;
    }

    @JsonbCreator
    public IdentifiantJson(final @JsonbProperty("uuid") String uuid) {
        this.identifiant = IdentifiantBase.builder()
                .uuid(uuid)
                .build();
    }

    @JsonbProperty("uuid")
    @Override
    public String getUUID() {
        return this.identifiant.getUUID();
    }

    @Override
    public int hashCode() {
        return this.identifiant.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        return this.identifiant.equals(obj);
    }

    @Override
    public String toString() {
        return "IdentifiantJson{" + "identifiant=" + identifiant + '}';
    }

    public static class Adapter implements JsonbAdapter<Identifiant, IdentifiantJson> {

        @Override
        public IdentifiantJson adaptToJson(final Identifiant base) throws Exception {
            return new IdentifiantJson(base);
        }

        @Override
        public Identifiant adaptFromJson(final IdentifiantJson json) throws Exception {
            return json;
        }

    }
}
