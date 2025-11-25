package recette.domain;

import core.domain.Identifiant;
import java.util.Objects;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class ComposantBase implements Composant {

    private final Identifiant identifiant;
    private final Ingredient ingredient;
    private Double quantite;
    private String commentaire;
    private Unite unite;

    public ComposantBase(final Identifiant identifiant,
            final Ingredient ingredient) {
        this.identifiant = identifiant;
        this.ingredient = ingredient;

    }

    @Override
    public Identifiant getIdentifiant() {
        return this.identifiant;
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
        int hash = HASH;
        hash = HASH2 * hash + Objects.hashCode(this.identifiant);
        return hash;
    }
    private static final int HASH2 = 41;
    private static final int HASH = 7;

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
        return Objects.equals(this.identifiant, other.getIdentifiant());
    }

}
