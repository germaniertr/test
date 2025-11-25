package recette.domain;

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
    public void update(final Composant recette) {
        throw new UnsupportedOperationException("Not supported yet.");
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

}
