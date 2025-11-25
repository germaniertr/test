package recette.domain;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class IdentifiantBase implements Identifiant {

    private final String uuid;

    IdentifiantBase(final String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getUUID() {
        return this.uuid;
    }

    @Override
    public String toString() {
        return "IdentifiantBase{" + "uuid=" + uuid + '}';
    }
}
