package core.domain;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public interface Entite<E extends Entite> {

    Identifiant getIdentifiant();

    void update(E entite);
}
