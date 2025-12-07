package recette.datasource.memory;

import core.domain.Identifiant;
import recette.domain.Ingredient;
import recette.domain.IngredientBase;
import recette.domain.Recette;

/**
 *
 * @author dominique huguenin (dominique.huguenin at rpn.ch)
 */
public class IngredientMemory implements Ingredient {

    private Ingredient entite;
    private Long version;

    public IngredientMemory(final Ingredient entite) {
        if (entite == null) {
            throw new IllegalArgumentException("Erreur: l'argument contact ne peut pas être null");
        }
        this.entite = IngredientBase.builder()
                .ingredient(entite)
                .build();
        this.version = 1L;
        if (entite.getVersion() != null && entite.getVersion() != 0) {
            this.version = entite.getVersion();
        }
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
    public Recette getRecette() {
        return this.entite.getRecette();
    }

    @Override
    public void setRecette(final Recette recette) {
        this.entite.setRecette(recette);
    }

    @Override
    public Identifiant getIdentifiant() {
        return this.entite.getIdentifiant();
    }

    @Override
    public Long getVersion() {
        return this.version;
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

    public void incrementVersion() {
        this.version = this.version + 1;
    }

}
