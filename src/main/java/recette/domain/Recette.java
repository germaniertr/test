package recette.domain;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public interface Recette {

    String getUUID();

    void update(Recette recette);

    String getNom();

    void setNom(String nom);

    String getDetail();

    void setDetail(String detail);

    String getPreparation();

    void setPreparation(String preparation);

    Integer getNombrePersonnes();

    void setNombrePersonnes(Integer nombrePersonnes);

}
