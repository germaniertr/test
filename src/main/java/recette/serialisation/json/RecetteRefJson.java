package recette.serialisation.json;

import core.domain.Audit;
import core.domain.Identifiant;
import core.serialisation.json.IdentifiantJson;
import jakarta.json.bind.adapter.JsonbAdapter;
import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import java.util.List;
import recette.datasource.RecetteRef;
import recette.domain.Composant;
import recette.domain.Recette;
import recette.domain.RecetteBase;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class RecetteRefJson implements Recette {

    private Recette entite;

    public RecetteRefJson(final Recette recette) {
        if (recette != null) {
            this.entite = new RecetteRef(recette);
        }
    }

    @JsonbCreator
    public RecetteRefJson(
            final @JsonbProperty("identifiant")
            @JsonbTypeAdapter(IdentifiantJson.Adapter.class) Identifiant identifiant) {

        RecetteBase.Builder builder = RecetteBase.builder()
                .identifiant(identifiant);

        this.entite = builder.build();
    }

    @Override
    @JsonbProperty("identifiant")
    @JsonbTypeAdapter(IdentifiantJson.Adapter.class)
    public Identifiant getIdentifiant() {
        return this.entite.getIdentifiant();
    }

    @Override
    public int hashCode() {
        return this.entite.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        return this.entite.equals(obj);
    }

    @Override
    @JsonbTransient
    public Long getVersion() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @JsonbTransient
    public String getNom() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setNom(final String nom) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @JsonbTransient
    public String getDetail() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setDetail(final String detail) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @JsonbTransient
    public String getPreparation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @JsonbTransient
    public void setPreparation(final String preparation) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @JsonbTransient
    public Integer getNombrePersonnes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @JsonbTransient
    public void setNombrePersonnes(final Integer nombrePersonnes) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @JsonbTransient
    public List<Composant> getComposants() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(final Recette pEntite) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @JsonbTransient
    public Audit getAudit() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static class Adapter implements JsonbAdapter<Recette, RecetteRefJson> {

        @Override
        public RecetteRefJson adaptToJson(final Recette base) throws Exception {
            return new RecetteRefJson(base);
        }

        @Override
        public Recette adaptFromJson(final RecetteRefJson json) throws Exception {
            return json;
        }

    }

}
