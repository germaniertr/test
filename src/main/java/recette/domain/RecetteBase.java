package recette.domain;

import java.util.List;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class RecetteBase implements Recette {

    @Override
    public Identifiant getIdentifiant() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(final Recette recette) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getNom() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setNom(final String nom) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getDetail() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setDetail(final String detail) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getPreparation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setPreparation(final String preparation) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Integer getNombrePersonnes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setNombrePersonnes(final Integer nombrePersonnes) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Composant> getComposants() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
