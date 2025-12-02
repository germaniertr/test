package recette.datasource;

import core.domain.Identifiant;
import java.util.List;
import recette.domain.Composant;
import recette.domain.Recette;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class RecetteRef implements Recette {

    private Identifiant identifiant;
    private String nom;
    private String detail;

    /**
     *
     * @param recette
     */
    public RecetteRef(final Recette recette) {
        if (recette != null) {
            this.identifiant = recette.getIdentifiant();
            this.nom = recette.getNom();
            this.detail = recette.getDetail();
        }
    }

    @Override
    public Identifiant getIdentifiant() {
        return this.identifiant;
    }

    @Override
    public String getNom() {
        return this.nom;
    }

    @Override
    public void setNom(final String nom) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getDetail() {
        return this.detail;
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
