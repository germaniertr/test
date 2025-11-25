package recette.domain;

import core.domain.Identifiant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private final List<Composant> composants;

    RecetteBase(final Identifiant identifiant) {
        this.identifiant = identifiant;
        this.composants = new ArrayList<>();
    }

    @Override
    public Identifiant getIdentifiant() {
        return this.identifiant;
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
            Composant copie = new ComposantBase(c.getIdentifiant(), c.getIngredient());
            copie.update(c);
            this.composants.add(copie);
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
        return "RecetteBase{" + "identifiant=" + identifiant
                + ", nom=" + nom
                + ", detail=" + detail
                + ", preparation=" + preparation
                + ", nombrePersonnes=" + nombrePersonnes + '}';
    }

    @Override
    public int hashCode() {
        int hash = HASH;
        hash = HASH2 * hash + Objects.hashCode(this.identifiant);
        return hash;
    }
    private static final int HASH2 = 37;
    private static final int HASH = 3;

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
        return Objects.equals(this.identifiant, other.getIdentifiant());
    }

}
