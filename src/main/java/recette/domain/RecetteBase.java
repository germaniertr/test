package recette.domain;

import java.util.List;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class RecetteBase implements Recette {

    private final Identifiant identifiant;
    private String nom;
    private String detail;
    private String preparation;
    private Integer nombrePersonnes;

    RecetteBase(final Identifiant identifiant) {
        this.identifiant = identifiant;
    }

    @Override
    public Identifiant getIdentifiant() {
        return this.identifiant;
    }

    @Override
    public void update(final Recette recette) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getNom() {
        return this.nom;
    }

    @Override
    public void setNom(final String nom) {
        this.nom = nom;
    }

    @Override
    public String getDetail() {
        return this.detail;
    }

    @Override
    public void setDetail(final String detail) {
        this.detail = detail;
    }

    @Override
    public String getPreparation() {
        return this.preparation;
    }

    @Override
    public void setPreparation(final String preparation) {
        this.preparation = preparation;
    }

    @Override
    public Integer getNombrePersonnes() {
        return this.nombrePersonnes;
    }

    @Override
    public void setNombrePersonnes(final Integer nombrePersonnes) {
        this.nombrePersonnes = nombrePersonnes;
    }

    @Override
    public List<Composant> getComposants() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
