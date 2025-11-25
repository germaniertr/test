package recette.domain;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class UniteBase implements Unite {

    public UniteBase(final Identifiant identifiant) {
    }

    @Override
    public Identifiant getIdentifiant() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(final Unite unite) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getCode() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setCode(final String code) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
