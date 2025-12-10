package recette.serialisation.json;

import core.domain.Audit;
import core.domain.Identifiant;
import core.serialisation.json.AuditJson;
import core.serialisation.json.IdentifiantJson;
import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
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
        return this.entite.getNom();
    }

    @Override
    public void setNom(final String nom) {
        this.entite.setNom(nom);
    }

    @Override
    public String getDetail() {
        return this.entite.getDetail();
    }

    @Override
    public void setDetail(final String detail) {
        this.entite.setDetail(detail);
    }

    @Override
    public String getPreparation() {
        return this.entite.getPreparation();
    }

    @Override
    public void setPreparation(final String preparation) {
        this.entite.setPreparation(preparation);
    }

    @Override
    public Integer getNombrePersonnes() {
        return this.entite.getNombrePersonnes();
    }

    @Override
    public void setNombrePersonnes(final Integer nombrePersonnes) {
        this.entite.setNombrePersonnes(nombrePersonnes);
    }

    @Override
    @JsonbTransient
    public List<Composant> getComposants() {
        return this.entite.getComposants();
    }

    @Override
    @JsonbProperty("identifiant")
    @JsonbTypeAdapter(IdentifiantJson.Adapter.class)
    public Identifiant getIdentifiant() {
        return this.entite.getIdentifiant();
    }

    @Override
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
    public void update(final Recette pEntite) {
        this.entite.update(entite);
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
        return "RecetteJson{" + "entite=" + entite + '}';
    }

}
