package recette.datasource;

import core.domain.Audit;
import core.domain.Identifiant;
import java.util.List;
import recette.domain.Composant;
import recette.domain.Recette;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class RecetteRef implements Recette {

    private Recette recette;

    /**
     *
     * @param recette
     */
    public RecetteRef(final Recette recette) {
        if (recette != null) {
            this.recette = recette;
        }
    }

    @Override
    public Identifiant getIdentifiant() {
        return this.recette.getIdentifiant();
    }

    @Override
    public int hashCode() {
        return this.recette.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        return this.recette.equals(obj);
    }

    @Override
    public Long getVersion() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Audit getAudit() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getNom() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setNom(final String nom) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getDetail() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDetail(final String detail) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer getNombrePersonnes() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setNombrePersonnes(final Integer nombrePersonnes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getPreparation() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPreparation(final String preparation) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Composant> getComposants() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(final Recette unite) {
        throw new UnsupportedOperationException();
    }

}
