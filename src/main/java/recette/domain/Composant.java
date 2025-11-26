package recette.domain;

import core.domain.Entite;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public interface Composant extends Entite<Composant> {

    Double getQuantite();

    void setQuantite(Double quantite);

    String getCommentaire();

    void setCommentaire(String commentaire);

    Ingredient getIngredient();

    Unite getUnite();

    void setUnite(Unite unite);

}
