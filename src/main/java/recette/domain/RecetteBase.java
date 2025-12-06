package recette.domain;

import core.domain.EntiteBase;
import core.domain.Identifiant;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public final class RecetteBase extends EntiteBase<Recette> implements Recette {

    private String nom;
    private String detail;
    private String preparation;
    private Integer nombrePersonnes;
    private final List<Composant> composants;

    private RecetteBase(final Builder b) {
        super(b.identifiant, b.version);
        this.nom = b.nom;
        this.detail = b.detail;
        this.preparation = b.preparation;
        this.nombrePersonnes = b.nombrePersonnes;

        this.composants = new ArrayList<>();
        for (Composant c : b.composants) {
            this.composants.add(ComposantBase.builder()
                    .composant(c)
                    .build());
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
            this.composants.add(ComposantBase.builder()
                    .composant(c)
                    .build());
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

    public static Builder builder() {
        return new Builder();

    }

    public static class Builder {

        private Identifiant identifiant = null;
        private Long version = 0L;
        private String nom;
        private String detail;
        private String preparation;
        private Integer nombrePersonnes;
        private List<Composant> composants;

        protected Builder() {
            composants = new ArrayList<>();
        }

        public Builder recette(final Recette pRecette) {
            if (pRecette == null) {
                throw new IllegalArgumentException("Erreur: l'argument recette ne peut pas être null");
            }
            this.identifiant = pRecette.getIdentifiant();
            this.version = pRecette.getVersion();
            this.nom = pRecette.getNom();
            this.detail = pRecette.getDetail();
            this.preparation = pRecette.getPreparation();
            this.nombrePersonnes = pRecette.getNombrePersonnes();

            for (Composant c : pRecette.getComposants()) {
                this.composants.add(ComposantBase.builder()
                        .composant(c)
                        .build());
            }

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

        public Builder nom(final String pNom) {
            this.nom = pNom;
            return this;
        }

        public Builder detail(final String pDetail) {
            this.detail = pDetail;
            return this;
        }

        public Builder preparation(final String pPreparation) {
            this.preparation = pPreparation;
            return this;
        }

        public Builder nombrePersonnes(final Integer pNombrePersonnes) {
            this.nombrePersonnes = pNombrePersonnes;
            return this;
        }

        public Builder composant(final Composant pComposant) {
            this.composants.add(pComposant);
            return this;
        }

        public Recette build() {
            return new RecetteBase(this);
        }

    }
}
