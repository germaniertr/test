package recette.domain;

import core.domain.EntiteBase;
import core.domain.Identifiant;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public final class UniteBase extends EntiteBase<Unite> implements Unite {

    private String code;

    private UniteBase(final Builder b) {
        super(b.identifiant, b.version);
        this.code = b.code;
    }

    @Override
    public void update(final Unite unite) {
        if (unite == null) {
            return;
        }
        this.code = unite.getCode();
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public void setCode(final String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "UniteBase{" + super.toString() + ", code=" + code + '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Unite)) {
            return false;
        }
        return super.equals(obj);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Identifiant identifiant = null;
        private Long version = 0L;
        private String code = null;

        protected Builder() {
        }

        public Builder unite(final Unite pUnite) {
            if (pUnite == null) {
                throw new IllegalArgumentException("Erreur: l'argument unité ne peut pas être null");
            }

            this.identifiant = pUnite.getIdentifiant();
            this.version = pUnite.getVersion();
            this.code = pUnite.getCode();

            return this;
        }

        public Builder identifiant(final Identifiant pIdentifiant) {
            this.identifiant = pIdentifiant;
            return this;
        }

        public Builder version(final Long pVersion) {
            this.version = pVersion;
            return this;
        }

        public Builder code(final String pCode) {
            this.code = pCode;
            return this;
        }

        public Unite build() {
            return new UniteBase(this);

        }
    }
}
