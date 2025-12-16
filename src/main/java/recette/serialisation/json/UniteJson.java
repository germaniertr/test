package recette.serialisation.json;

import core.domain.Audit;
import core.domain.Identifiant;
import core.serialisation.json.AuditJson;
import core.serialisation.json.IdentifiantJson;
import jakarta.json.bind.adapter.JsonbAdapter;
import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import java.util.ArrayList;
import java.util.List;
import recette.domain.Unite;
import recette.domain.UniteBase;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class UniteJson implements Unite {

    private final Unite entite;

    public UniteJson(final Unite entite) {
        if (entite == null) {
            throw new NullPointerException();
        }

        this.entite = entite;
    }

    @JsonbCreator
    public UniteJson(
            final @JsonbProperty("identifiant")
            @JsonbTypeAdapter(IdentifiantJson.Adapter.class) Identifiant identifiant,
            final @JsonbProperty("version") Long version,
            final @JsonbProperty("audit")
            @JsonbTypeAdapter(AuditJson.Adapter.class) Audit audit) {

        this.entite = UniteBase.builder()
                .identifiant(identifiant)
                .version(version)
                .audit(audit)
                .build();
    }

    @Override
    @JsonbProperty("code")
    public String getCode() {
        return this.entite.getCode();
    }

    @Override
    @JsonbProperty("code")
    public void setCode(final String code) {
        this.entite.setCode(code);
    }

    @Override
    @JsonbProperty("identifiant")
    @JsonbTypeAdapter(IdentifiantJson.Adapter.class)
    public Identifiant getIdentifiant() {
        return this.entite.getIdentifiant();
    }

    @Override
    @JsonbProperty("version")
    public Long getVersion() {
        return this.entite.getVersion();
    }

    @Override
    @JsonbProperty("audit")
    @JsonbTypeAdapter(AuditJson.Adapter.class)
    public Audit getAudit() {
        return this.entite.getAudit();
    }

    @Override
    public void update(final Unite pEntite) {
        this.entite.update(pEntite);
    }

    @Override
    public int hashCode() {
        return this.entite.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        return this.entite.equals(obj);
    }

    @Override
    public String toString() {
        return "UniteJson{" + "entite=" + entite + '}';
    }

    public static class Adapter implements JsonbAdapter<Unite, UniteJson> {

        @Override
        public UniteJson adaptToJson(final Unite base) throws Exception {
            return new UniteJson(base);
        }

        @Override
        public Unite adaptFromJson(final UniteJson json) throws Exception {
            return json;
        }

    }

    public static List<UniteJson> getInstance(final List<Unite> list) {
        if (list == null) {
            throw new IllegalArgumentException();
        }

        List<UniteJson> entiteJsonList = new ArrayList<>();
        for (Unite p : list) {
            entiteJsonList.add(new UniteJson(p));
        }

        return entiteJsonList;
    }

}
