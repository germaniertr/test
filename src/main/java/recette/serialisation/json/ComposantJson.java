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
import java.util.Arrays;
import java.util.List;
import recette.domain.Composant;
import recette.domain.ComposantBase;
import recette.domain.Ingredient;
import recette.domain.Unite;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class ComposantJson implements Composant {

    private Composant entite;

    public ComposantJson(final Composant entite) {
        if (entite == null) {
            throw new NullPointerException();
        }

        this.entite = entite;
    }

    @JsonbCreator
    public ComposantJson(
            final @JsonbProperty("identifiant")
            @JsonbTypeAdapter(IdentifiantJson.Adapter.class) Identifiant identifiant,
            final @JsonbProperty("version") Long version,
            final @JsonbProperty("audit")
            @JsonbTypeAdapter(AuditJson.Adapter.class) Audit audit,
            final @JsonbProperty("ingredient")
            @JsonbTypeAdapter(IngredientJson.Adapter.class) Ingredient ingredient) {

        this.entite = ComposantBase.builder()
                .identifiant(identifiant)
                .version(version)
                .audit(audit)
                .ingredient(ingredient)
                .build();
    }

    @Override
    @JsonbProperty("quantite")
    public Double getQuantite() {
        return this.entite.getQuantite();
    }

    @Override
    @JsonbProperty("quantite")
    public void setQuantite(final Double quantite) {
        this.entite.setQuantite(quantite);
    }

    @Override
    @JsonbProperty("commentaire")
    public String getCommentaire() {
        return this.entite.getCommentaire();
    }

    @Override
    @JsonbProperty("commentaire")
    public void setCommentaire(final String commentaire) {
        this.entite.setCommentaire(commentaire);
    }

    @Override
    @JsonbProperty("ingredient")
    @JsonbTypeAdapter(IngredientJson.Adapter.class)
    public Ingredient getIngredient() {
        return this.entite.getIngredient();
    }

    @Override
    @JsonbProperty("unite")
    @JsonbTypeAdapter(UniteJson.Adapter.class)
    public Unite getUnite() {
        return this.entite.getUnite();
    }

    @Override
    @JsonbProperty("unite")
    @JsonbTypeAdapter(UniteJson.Adapter.class)
    public void setUnite(final Unite unite) {
        this.entite.setUnite(unite);
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
    public void update(final Composant pEntite) {
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
        return "ComposantJson{" + "entite=" + entite + '}';
    }

    public static class ListAdapter implements JsonbAdapter<List<Composant>, ComposantJson[]> {

        @Override
        public ComposantJson[] adaptToJson(final List<Composant> base) throws Exception {
            List<ComposantJson> list = new ArrayList<>();
            for (Composant e : base) {
                list.add(new ComposantJson(e));
            }
            return list.toArray(ComposantJson[]::new);
        }

        @Override
        public List<Composant> adaptFromJson(final ComposantJson[] json) throws Exception {
            return Arrays.asList(json);
        }

    }

}
