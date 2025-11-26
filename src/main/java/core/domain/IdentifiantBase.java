package core.domain;

import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public final class IdentifiantBase implements Identifiant {

    private final String uuid;

    private IdentifiantBase(final Builder b) {
        this.uuid = b.uuid;
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

    /**
     *
     * @return le builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     *
     */
    public static final class Builder {

        private String uuid;

        private Builder() {
            this.uuid = UUID.randomUUID().toString();
        }

        /**
         *
         * @param identifiant un identifiant
         * @return le builder
         */
        public Builder identifiant(final Identifiant identifiant) {
            if (identifiant == null) {
                throw new IllegalArgumentException("Erreur: l'argument identifiant ne peut pas être null");
            }

            this.uuid = identifiant.getUUID();
            return this;
        }

        /**
         *
         * @param pUuid un uuid
         * @return le builder
         */
        public Builder uuid(final String pUuid) {
            this.uuid = pUuid;
            return this;
        }

        /**
         *
         * @return un identifiant
         */
        public Identifiant build() {
            return new IdentifiantBase(this);
        }
    }

}
