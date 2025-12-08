package recette.datasource.memory;

import core.domain.Audit;
import core.domain.AuditBase;
import core.domain.Identifiant;
import java.time.Instant;
import java.util.List;
import recette.domain.Composant;
import recette.domain.Recette;
import recette.domain.RecetteBase;

/**
 *
 * @author dom
 */
public class RecetteMemory implements Recette {

    private Long version;
    private Recette entite;
    private Audit audit;

    public RecetteMemory(final Recette entite) {
        if (entite == null) {
            throw new IllegalArgumentException("Erreur: l'argument contact ne peut pas être null");
        }
        this.entite = RecetteBase.builder()
                .recette(entite)
                .build();
        this.version = 1L;
        if (entite.getVersion() != null && entite.getVersion() != 0) {
            this.version = entite.getVersion();
        }

        this.audit = AuditBase.builder()
                .dateCreation(Instant.now())
                .build();
        if (entite.getAudit() != null) {
            this.audit = entite.getAudit();
        }

    }

    @Override
    public String getNom() {
        return this.entite.getNom();
    }

    @Override
    public void setNom(final String nom) {
        this.entite.setNom(nom);
    }

    @Override
    public String getDetail() {
        return this.entite.getDetail();
    }

    @Override
    public void setDetail(final String detail) {
        this.entite.setDetail(detail);
    }

    @Override
    public String getPreparation() {
        return this.entite.getPreparation();
    }

    @Override
    public void setPreparation(final String preparation) {
        this.entite.setPreparation(preparation);
    }

    @Override
    public Integer getNombrePersonnes() {
        return this.entite.getNombrePersonnes();
    }

    @Override
    public void setNombrePersonnes(final Integer nombrePersonnes) {
        this.entite.setNombrePersonnes(nombrePersonnes);
    }

    @Override
    public List<Composant> getComposants() {
        return this.entite.getComposants();
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
    public void update(final Recette pEntite) {
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
