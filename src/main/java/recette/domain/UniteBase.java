package recette.domain;

import core.domain.EntiteBase;
import core.domain.Identifiant;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class UniteBase extends EntiteBase<Unite> implements Unite {

    private String code;

    public UniteBase(final Identifiant identifiant) {
        super(identifiant);
    }

    public UniteBase(final Unite entite) {
        super(entite);
        this.code = entite.getCode();
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

}
