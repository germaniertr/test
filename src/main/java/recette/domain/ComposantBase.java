package recette.domain;

import core.domain.EntiteBase;
import core.domain.Identifiant;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public final class ComposantBase extends EntiteBase<Composant> implements Composant {

    private final Ingredient ingredient;
    private Double quantite;
    private String commentaire;
    private Unite unite;

    private ComposantBase(final Builder b) {
        super(b.identifiant, b.version);
        this.ingredient = b.ingredient;
        this.commentaire = b.commentaire;
        this.quantite = b.quantite;
        this.unite = b.unite;
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Identifiant identifiant = null;
        private Long version = 0L;
        private Ingredient ingredient;
        private String commentaire;
        private Double quantite;
        private Unite unite;

        protected Builder() {
        }

        public Builder composant(final Composant pComposant) {
            if (pComposant == null) {
                throw new IllegalArgumentException("Erreur: l'argument composant ne peut pas être null");
            }
            this.identifiant = pComposant.getIdentifiant();
            this.version = pComposant.getVersion();
            this.ingredient = pComposant.getIngredient();
            this.commentaire = pComposant.getCommentaire();
            this.quantite = pComposant.getQuantite();
            this.unite = pComposant.getUnite();

            return this;
        }

        public Builder identifiant(final Identifiant pIdentifiant) {
            this.identifiant = pIdentifiant;
            return this;
        }

        public Builder version(final Long pVersion) {
            this.version = pVersion;
            return this;
        }

        public Builder ingredient(final Ingredient pIngredient) {
            this.ingredient = pIngredient;
            return this;
        }

        public Builder commentaire(final String pCommentaire) {
            this.commentaire = pCommentaire;
            return this;
        }

        public Builder quantite(final Double pQuantite) {
            this.quantite = pQuantite;
            return this;
        }

        public Builder unite(final Unite pUnite) {
            this.unite = pUnite;
            return this;
        }

        public Composant build() {
            return new ComposantBase(this);
        }

    }
}
