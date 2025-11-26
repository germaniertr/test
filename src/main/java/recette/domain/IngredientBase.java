package recette.domain;

import core.domain.EntiteBase;
import core.domain.Identifiant;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public final class IngredientBase extends EntiteBase<Ingredient> implements Ingredient {

    private String nom;
    private String detail;
    private Recette recette;

    private IngredientBase(final Builder b) {
        super(b.identifiant);

        this.nom = b.nom;
        this.detail = b.detail;
        this.recette = b.recette;
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Identifiant identifiant = null;
        private String nom;
        private String detail;
        private Recette recette;

        protected Builder() {
        }

        public Builder ingredient(final Ingredient pIngredient) {
            if (pIngredient == null) {
                throw new IllegalArgumentException("Erreur: l'argument ingrédient ne peut pas être null");
            }

            this.identifiant = pIngredient.getIdentifiant();
            this.nom = pIngredient.getNom();
            this.detail = pIngredient.getDetail();
            this.recette = pIngredient.getRecette();

            return this;
        }

        public Builder identifiant(final Identifiant pIdentifiant) {
            this.identifiant = pIdentifiant;
            return this;
        }

        public Ingredient build() {
            return new IngredientBase(this);
        }

        public Builder nom(final String pNom) {
            this.nom = pNom;
            return this;
        }

        public Builder detail(final String pDetail) {
            this.detail = pDetail;
            return this;
        }

        public Builder recette(final Recette pRecette) {
            this.recette = pRecette;
            return this;
        }
    }
}
