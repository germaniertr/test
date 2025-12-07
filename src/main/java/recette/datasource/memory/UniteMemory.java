package recette.datasource.memory;

import core.domain.Audit;
import core.domain.AuditBase;
import core.domain.Identifiant;
import java.time.Instant;
import recette.domain.Unite;
import recette.domain.UniteBase;

/**
 *
 * @author dominique huguenin (dominique.huguenin at rpn.ch)
 */
public class UniteMemory implements Unite {

    private Long version;
    private Unite entite;
    private Audit audit;

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

        this.audit = AuditBase.builder()
                .dateCreation(Instant.now())
                .build();

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
    public Audit getAudit() {
        return this.audit;
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

    public void setDateModificationNow() {
        this.audit = AuditBase.builder()
                .audit(this.audit)
                .dateModification(Instant.now())
                .build();
    }

}
