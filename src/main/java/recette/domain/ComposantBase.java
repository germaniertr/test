package recette.domain;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class ComposantBase implements Composant {

    public ComposantBase(final Identifiant identifiant,
            final Ingredient ingredient) {
    }

    @Override
    public Identifiant getIdentifiant() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(final Composant recette) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Double getQuantite() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setQuantite(final Double quantite) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getCommentaire() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setCommentaire(final String commentaire) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Ingredient getIngredient() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Unite getUnite() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setUnite(final Unite unite) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
