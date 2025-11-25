package recette.domain;

import core.domain.Identifiant;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public interface Unite {

    Identifiant getIdentifiant();

    void update(Unite unite);

    String getCode();

    void setCode(String code);

}
