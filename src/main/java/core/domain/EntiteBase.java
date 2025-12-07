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
    private final Audit audit;

    protected EntiteBase(final Identifiant identifiant,
            final Long version,
            final Audit audit) {
        this.identifiant = identifiant;
        this.version = version;
        this.audit = audit;
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
    public Audit getAudit() {
        return audit;
    }

    @Override
    public String toString() {
        return "EntiteBase{" + "identifiant=" + identifiant
                + ", version=" + version
                + ", audit=" + audit + '}';
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
