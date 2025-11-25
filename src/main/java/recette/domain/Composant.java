package recette.domain;

import core.domain.Identifiant;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public interface Composant {

    Identifiant getIdentifiant();

    void update(Composant recette);

    Double getQuantite();

    void setQuantite(Double quantite);

    String getCommentaire();

    void setCommentaire(String commentaire);

    Ingredient getIngredient();

    Unite getUnite();

    void setUnite(Unite unite);

}
