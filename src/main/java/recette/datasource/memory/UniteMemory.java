package recette.datasource.memory;

import core.domain.Identifiant;
import recette.domain.Unite;
import recette.domain.UniteBase;

/**
 *
 * @author dominique huguenin (dominique.huguenin at rpn.ch)
 */
public class UniteMemory implements Unite {

    private Long version;
    private Unite entite;

    public UniteMemory(final Unite entite) {
        if (entite == null) {
            throw new IllegalArgumentException("Erreur: l'argument contact ne peut pas être null");
        }

        this.entite = UniteBase.builder()
                .unite(entite)
                .build();
        this.version = 1L;
        if (entite.getVersion() != null && entite.getVersion() != 0) {
            this.version = entite.getVersion();
        }
    }

    @Override
    public String getCode() {
        return this.entite.getCode();
    }

    @Override
    public void setCode(final String code) {
        this.entite.setCode(code);
    }

    @Override
    public Identifiant getIdentifiant() {
        return this.entite.getIdentifiant();
    }

    @Override
    public Long getVersion() {
        return this.version;
    }

    @Override
    public void update(final Unite pEntite) {
        this.entite.update(pEntite);
    }

    @Override
    public int hashCode() {
        return this.entite.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        return this.entite.equals(obj);
    }

    public void incrementVersion() {
        this.version = this.version + 1;
    }

}
