package recette.domain;

import core.domain.EntiteBase;
import core.domain.Identifiant;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class ComposantBase extends EntiteBase<Composant> implements Composant {

    private final Ingredient ingredient;
    private Double quantite;
    private String commentaire;
    private Unite unite;

    public ComposantBase(final Identifiant identifiant,
            final Ingredient ingredient) {
        super(identifiant);
        this.ingredient = ingredient;

    }

    ComposantBase(final Composant entite) {
        super(entite);
        this.ingredient = entite.getIngredient();
        this.commentaire = entite.getCommentaire();
        this.quantite = entite.getQuantite();
        this.unite = entite.getUnite();

    }

    @Override
    public void update(final Composant composant) {
        if (composant == null) {
            return;
        }
        this.quantite = composant.getQuantite();
        this.commentaire = composant.getCommentaire();
        this.unite = composant.getUnite();
    }

    @Override
    public Double getQuantite() {
        return this.quantite;
    }

    @Override
    public void setQuantite(final Double quantite) {
        this.quantite = quantite;
    }

    @Override
    public String getCommentaire() {
        return this.commentaire;
    }

    @Override
    public void setCommentaire(final String commentaire) {
        this.commentaire = commentaire;
    }

    @Override
    public Ingredient getIngredient() {
        return this.ingredient;
    }

    @Override
    public Unite getUnite() {
        return this.unite;
    }

    @Override
    public void setUnite(final Unite unite) {
        this.unite = unite;
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
        if (!(obj instanceof Composant)) {
            return false;
        }
        final Composant other = (Composant) obj;
        return super.equals(obj);
    }

}
