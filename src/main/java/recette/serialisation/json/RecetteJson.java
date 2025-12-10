package recette.serialisation.json;

import core.domain.Audit;
import core.domain.Identifiant;
import core.serialisation.json.AuditJson;
import core.serialisation.json.IdentifiantJson;
import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import java.util.List;
import recette.domain.Composant;
import recette.domain.Recette;
import recette.domain.RecetteBase;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class RecetteJson implements Recette {

    private Recette entite;

    public RecetteJson(final Recette entite) {
        if (entite == null) {
            throw new NullPointerException();
        }

        this.entite = entite;
    }

    @JsonbCreator
    public RecetteJson(
            final @JsonbProperty("identifiant")
            @JsonbTypeAdapter(IdentifiantJson.Adapter.class) Identifiant identifiant,
            final @JsonbProperty("version") Long version,
            final @JsonbProperty("audit")
            @JsonbTypeAdapter(AuditJson.Adapter.class) Audit audit) {

        this.entite = RecetteBase.builder()
                .identifiant(identifiant)
                .version(version)
                .audit(audit)
                .build();
    }

    @Override
    public String getNom() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setNom(final String nom) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getDetail() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setDetail(final String detail) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getPreparation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setPreparation(final String preparation) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Integer getNombrePersonnes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setNombrePersonnes(final Integer nombrePersonnes) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Composant> getComposants() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Identifiant getIdentifiant() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Long getVersion() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Audit getAudit() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(final Recette pEntite) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
