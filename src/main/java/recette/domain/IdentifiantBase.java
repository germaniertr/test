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
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
