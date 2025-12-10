package recette.serialisation.json;

import core.domain.Audit;
import core.domain.Identifiant;
import core.serialisation.json.AuditJson;
import core.serialisation.json.IdentifiantJson;
import jakarta.json.bind.adapter.JsonbAdapter;
import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import recette.domain.Ingredient;
import recette.domain.IngredientBase;
import recette.domain.Recette;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class IngredientJson implements Ingredient {

    private Ingredient entite;

    public IngredientJson(final Ingredient entite) {
        if (entite == null) {
            throw new NullPointerException();
        }

        this.entite = entite;
    }

    @JsonbCreator
    public IngredientJson(
            final @JsonbProperty("identifiant")
            @JsonbTypeAdapter(IdentifiantJson.Adapter.class) Identifiant identifiant,
            final @JsonbProperty("version") Long version,
            final @JsonbProperty("audit")
            @JsonbTypeAdapter(AuditJson.Adapter.class) Audit audit) {

        this.entite = IngredientBase.builder()
                .identifiant(identifiant)
                .version(version)
                .audit(audit)
                .build();
    }

    @Override
    @JsonbProperty("nom")
    public String getNom() {
        return this.entite.getNom();
    }

    @Override
    @JsonbProperty("nom")
    public void setNom(final String nom) {
        this.entite.setNom(nom);
    }

    @Override
    @JsonbProperty("detail")
    public String getDetail() {
        return this.entite.getDetail();
    }

    @Override
    @JsonbProperty("detail")
    public void setDetail(final String detail) {
        this.entite.setDetail(detail);
    }

    @Override
    @JsonbTransient
    //@JsonbProperty("recette")
    public Recette getRecette() {
        return this.entite.getRecette();
    }

    @Override
    @JsonbTransient
    //@JsonbProperty("recette")
    public void setRecette(final Recette recette) {
        this.entite.setRecette(recette);
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
    public void update(final Ingredient pEntite) {
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
        return "IngredientJson{" + "entite=" + entite + '}';
    }

    public static class Adapter implements JsonbAdapter<Ingredient, IngredientJson> {

        @Override
        public IngredientJson adaptToJson(final Ingredient base) throws Exception {
            return new IngredientJson(base);
        }

        @Override
        public Ingredient adaptFromJson(final IngredientJson json) throws Exception {
            return json;
        }

    }

}
