package recette.domain;

import core.domain.EntiteBase;
import core.domain.Identifiant;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class IngredientBase extends EntiteBase<Ingredient> implements Ingredient {

    private String nom;
    private String detail;
    private Recette recette;

    IngredientBase(final Identifiant identifiant) {
        super(identifiant);
    }

    IngredientBase(final Ingredient entite) {
        super(entite);

        this.nom = entite.getNom();
        this.detail = entite.getDetail();
        this.recette = entite.getRecette();
    }

    @Override
    public void update(final Ingredient ingredient) {
        if (ingredient == null) {
            return;
        }
        this.nom = ingredient.getNom();
        this.detail = ingredient.getDetail();
        this.recette = ingredient.getRecette();
    }

    @Override
    public String getNom() {
        return this.nom;
    }

    @Override
    public void setNom(final String nom) {
        this.nom = nom;
    }

    @Override
    public String getDetail() {
        return this.detail;
    }

    @Override
    public void setDetail(final String detail) {
        this.detail = detail;
    }

    @Override
    public Recette getRecette() {
        return this.recette;
    }

    @Override
    public void setRecette(final Recette recette) {
        this.recette = recette;
    }

    @Override
    public String toString() {
        return "IngredientBase{" + super.toString() + ", nom=" + nom + ", detail=" + detail + '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Ingredient)) {
            return false;
        }
        return super.equals(obj);
    }

}
