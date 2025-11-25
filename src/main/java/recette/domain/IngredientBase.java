package recette.domain;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class IngredientBase implements Ingredient {

    private final Identifiant identifiant;
    private String nom;
    private String detail;

    IngredientBase(final Identifiant identifiant) {
        this.identifiant = identifiant;
    }

    @Override
    public Identifiant getIdentifiant() {
        return this.identifiant;
    }

    @Override
    public void update(final Ingredient ingredient) {
        throw new UnsupportedOperationException("Not supported yet.");
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setRecette(final Recette recette) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
