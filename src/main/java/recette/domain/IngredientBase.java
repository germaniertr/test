package recette.domain;

import java.util.Objects;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class IngredientBase implements Ingredient {

    private final Identifiant identifiant;
    private String nom;
    private String detail;
    private Recette recette;

    IngredientBase(final Identifiant identifiant) {
        this.identifiant = identifiant;
    }

    @Override
    public Identifiant getIdentifiant() {
        return this.identifiant;
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
        return "IngredientBase{" + "identifiant=" + identifiant + ", nom=" + nom + ", detail=" + detail + '}';
    }

    @Override
    public int hashCode() {
        int hash = HASH;
        hash = HASH2 * hash + Objects.hashCode(this.identifiant);
        return hash;
    }
    private static final int HASH2 = 71;
    private static final int HASH = 7;

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
        final Ingredient other = (Ingredient) obj;
        return Objects.equals(this.identifiant, other.getIdentifiant());
    }

}
