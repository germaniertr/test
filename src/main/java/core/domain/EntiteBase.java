package core.domain;

import java.util.Objects;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public abstract class EntiteBase<E extends Entite>
        implements Entite<E> {

    private final Identifiant identifiant;
    private final Long version;

    protected EntiteBase(final Identifiant identifiant,
            final Long version) {
        this.identifiant = identifiant;
        this.version = version;
    }

    @Override
    public Identifiant getIdentifiant() {
        return this.identifiant;
    }

    @Override
    public Long getVersion() {
        return this.version;
    }

    @Override
    public String toString() {
        return "EntiteBase{" + "identifiant=" + identifiant + ", version=" + version + '}';
    }

    @Override
    public int hashCode() {
        int hash = HASH_CODE_SEED_1;
        hash = HASH_CODE_SEED_2 * hash + Objects.hashCode(this.identifiant);
        return hash;
    }
    private static final int HASH_CODE_SEED_2 = 41;
    private static final int HASH_CODE_SEED_1 = 7;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Entite)) {
            return false;
        }
        final Entite<?> other = (Entite<?>) obj;

        return Objects.equals(this.identifiant, other.getIdentifiant());
    }

}
