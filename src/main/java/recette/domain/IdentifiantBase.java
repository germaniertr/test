package recette.domain;

import java.util.Objects;

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

    @Override
    public int hashCode() {
        int hash = HASH;
        hash = HASH2 * hash + Objects.hashCode(this.uuid);
        return hash;
    }
    private static final int HASH2 = 61;
    private static final int HASH = 7;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Identifiant)) {
            return false;
        }
        final Identifiant other = (Identifiant) obj;
        return Objects.equals(this.uuid, other.getUUID());
    }

}
