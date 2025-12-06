package core.domain;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public interface Entite<E extends Entite> {

    Identifiant getIdentifiant();

    Long getVersion();

    void update(E entite);
}
