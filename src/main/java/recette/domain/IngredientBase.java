package recette.domain;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class IngredientBase implements Ingredient {

    IngredientBase(final Identifiant identifiant) {
    }

    @Override
    public Identifiant getIdentifiant() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(final Ingredient ingredient) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getNom() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setNom(final String nom) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getDetail() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setDetail(final String detail) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Recette getRecette() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setRecette(final Recette recette) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
