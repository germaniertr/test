package recette.domain;

import core.domain.EntiteBase;
import core.domain.Identifiant;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class RecetteBase extends EntiteBase<Recette> implements Recette {

    private String nom;
    private String detail;
    private String preparation;
    private Integer nombrePersonnes;
    private final List<Composant> composants;

    RecetteBase(final Identifiant identifiant) {
        super(identifiant);
        this.composants = new ArrayList<>();
    }

    RecetteBase(final Recette entite) {
        super(entite);
        this.nom = entite.getNom();
        this.detail = entite.getDetail();
        this.preparation = entite.getPreparation();
        this.nombrePersonnes = entite.getNombrePersonnes();

        this.composants = new ArrayList<>();
        for (Composant c : entite.getComposants()) {
            this.composants.add(new ComposantBase(c));
        }
    }

    @Override
    public void update(final Recette recette) {
        if (recette == null) {
            return;
        }
        this.nom = recette.getNom();
        this.detail = recette.getDetail();
        this.preparation = recette.getPreparation();
        this.nombrePersonnes = recette.getNombrePersonnes();

        this.composants.clear();
        for (Composant c : recette.getComposants()) {
            this.composants.add(new ComposantBase(c));
        }
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
        return this.composants;
    }

    @Override
    public String toString() {
        return "RecetteBase{" + super.toString()
                + ", nom=" + nom
                + ", detail=" + detail
                + ", preparation=" + preparation
                + ", nombrePersonnes=" + nombrePersonnes + '}';
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
        if (!(obj instanceof Recette)) {
            return false;
        }
        final Recette other = (Recette) obj;
        return super.equals(obj);
    }

}
