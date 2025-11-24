package recette.domain;

import java.util.List;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public interface Recette {

    Identifiant getIdentifiant();

    void update(Recette recette);

    String getNom();

    void setNom(String nom);

    String getDetail();

    void setDetail(String detail);

    String getPreparation();

    void setPreparation(String preparation);

    Integer getNombrePersonnes();

    void setNombrePersonnes(Integer nombrePersonnes);

    List<Composant> getComposants();
}
