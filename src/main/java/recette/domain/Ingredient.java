package recette.domain;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public interface Ingredient {

    String getUUID();

    void update(Ingredient ingredient);

    String getNom();

    void setNom(String nom);

    String getDetail();

    void setDetail(String detail);

    Recette getRecette();

    void setRecette(Recette recette);
}
