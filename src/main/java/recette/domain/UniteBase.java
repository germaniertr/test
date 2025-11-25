package recette.domain;

import java.util.Objects;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class UniteBase implements Unite {

    private String code;
    private final Identifiant identifiant;

    public UniteBase(final Identifiant identifiant) {
        this.identifiant = identifiant;
    }

    @Override
    public Identifiant getIdentifiant() {
        return this.identifiant;
    }

    @Override
    public void update(final Unite unite) {
        throw new UnsupportedOperationException("Not supported yet.");
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
        return "UniteBase{" + "code=" + code + ", identifiant=" + identifiant + '}';
    }

    @Override
    public int hashCode() {
        int hash = HASH;
        hash = HASH2 * hash + Objects.hashCode(this.identifiant);
        return hash;
    }
    private static final int HASH2 = 37;
    private static final int HASH = 7;

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
        final Unite other = (Unite) obj;
        return Objects.equals(this.identifiant, other.getIdentifiant());
    }

}
