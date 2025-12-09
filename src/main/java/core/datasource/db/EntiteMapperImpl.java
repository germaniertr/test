package core.datasource.db;

import core.datasource.ContrainteNotNullPersistenceException;
import core.datasource.ContrainteUniquePersistenceException;
import core.datasource.EntiteInconnuePersistenceException;
import core.datasource.EntiteTropAnciennePersistenceException;
import core.datasource.EntiteUtiliseePersistenceException;
import core.datasource.Mapper;
import core.datasource.PersistenceException;
import core.domain.Audit;
import core.domain.AuditBase;
import core.domain.Entite;
import core.domain.Identifiant;
import core.domain.IdentifiantBase;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import recette.datasource.db.SQL;

/**
 *
 * @author dominique huguenin (dominique.huguenin@rpn.ch)
 */
public abstract class EntiteMapperImpl<M extends StatementManager, E extends Entite> implements Mapper<E> {

    protected static final Logger LOG = Logger.getLogger(EntiteMapperImpl.class.getName());
    private final M mapperManager;
    private final String querySelectById;
    private final String querySelectByFiltre;
    private final String queryDeleteById;

    public EntiteMapperImpl(final M mm,
            final String querySelectById,
            final String querySelectByFiltre,
            final String queryDeleteById) {

        this.mapperManager = mm;
        this.querySelectById = querySelectById;
        this.querySelectByFiltre = querySelectByFiltre;
        this.queryDeleteById = queryDeleteById;

    }

    public M getMapperManager() {
        return mapperManager;
    }

    @Override
    public E create(final E entite)
            throws PersistenceException {
        if (entite == null) {
            return null;
        }
        E nouvelEntite = null;
        Identifiant id = IdentifiantBase.builder().build();
        try {
            createEntity(id, entite);
            nouvelEntite = this.retrieve(id);
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
            if (ex.getSQLState().equals(SQL_ERREUR_CODES.POSTGRESQL.CONSTRAINT_NOT_NULL_VIOLATION)) {
                throw new ContrainteNotNullPersistenceException(ex);
            }
            if (ex.getSQLState().equals(SQL_ERREUR_CODES.POSTGRESQL.CONSTRAINT_UNIQUE_VIOLATION)) {
                throw new ContrainteUniquePersistenceException(ex);
            }
            if (ex.getSQLState().equals(SQL_ERREUR_CODES.POSTGRESQL.CONSTRAINT_FOREIGN_KEY_VIOLATION)) {
                throw new EntiteInconnuePersistenceException(ex);
            }
            throw new PersistenceException(ex);
        }
        return nouvelEntite;
    }

    @Override
    public E retrieve(final Identifiant id)
            throws PersistenceException {
        if (id == null) {
            return null;
        }
        E entite = null;
        try (PreparedStatement ps = this.mapperManager.prepareStatement(this.querySelectById)) {
            ps.setString(1, id.getUUID());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                entite = readEntite(rs);
                if (entite != null) {
                    retrieveEntitesDependantes(entite);
                }
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new PersistenceException(ex);
        }
        return entite;
    }

    @Override
    public List<E> retrieve(final String filtre)
            throws PersistenceException {
        List<E> entites = new ArrayList<>();
        if (filtre == null) {
            return entites;
        }
        try (PreparedStatement ps
                = this.mapperManager.prepareStatement(this.querySelectByFiltre)) {
            ps.setString(1, filtre);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                E entite = readEntite(rs);
                if (entite != null) {
                    retrieveEntitesDependantes(entite);
                    entites.add(entite);
                }
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new PersistenceException(ex);
        }
        return entites;
    }

    @Override
    public void update(final E entite)
            throws PersistenceException {
        if (entite == null) {
            return;
        }
        if (entite.getIdentifiant() == null) {
            return;
        }
        E entiteTrouvee = this.retrieve(entite.getIdentifiant());
        if (entiteTrouvee == null) {
            throw new EntiteInconnuePersistenceException(
                    String.format("Erreur: l'entitée (%s) n'est pas connue!",
                            entite.toString()));
        }
        /* traitement*/
        try {
            updateEntity(entite);
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
            if (ex.getSQLState().equals(SQL_ERREUR_CODES.POSTGRESQL.CONSTRAINT_NOT_NULL_VIOLATION)) {
                throw new ContrainteNotNullPersistenceException(ex);
            }
            if (ex.getSQLState().equals(SQL_ERREUR_CODES.POSTGRESQL.CONSTRAINT_UNIQUE_VIOLATION)) {
                throw new ContrainteUniquePersistenceException(ex);
            }
            if (ex.getSQLState().equals(SQL_ERREUR_CODES.POSTGRESQL.CONSTRAINT_FOREIGN_KEY_VIOLATION)) {
                throw new EntiteInconnuePersistenceException(ex);
            }
            throw new PersistenceException(ex);
        }
    }

    @Override
    public void delete(final E entite)
            throws PersistenceException {
        /*pré-condition*/
        if (entite == null) {
            return;
        }
        if (entite.getIdentifiant() == null) {
            return;
        }
        E entiteTrouvee = this.retrieve(entite.getIdentifiant());
        if (entiteTrouvee == null) {
            throw new EntiteInconnuePersistenceException(
                    String.format("Erreur: l'entitée (%s) n'est pas connue!",
                            entite.toString()));
        }
        /* traitement*/
        try (PreparedStatement ps = this.mapperManager.prepareStatement(this.queryDeleteById)) {
            ps.setString(1, entite.getIdentifiant().getUUID());
            ps.setLong(2, entite.getVersion());
            int row = ps.executeUpdate();
            if (row == 0) {
                throw new EntiteTropAnciennePersistenceException(entite.toString());
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
            if (ex.getSQLState().equals(SQL_ERREUR_CODES.POSTGRESQL.CONSTRAINT_FOREIGN_KEY_VIOLATION)) {
                throw new EntiteUtiliseePersistenceException(ex);
            }
            throw new PersistenceException(ex);
        }
    }

    protected Identifiant readIdentifiant(final ResultSet rs)
            throws SQLException {
        String uuid = rs.getString(SQL.ENTITES.ATTRIBUTS.UUID);
        return IdentifiantBase.builder().uuid(uuid).build();
    }

    protected Audit readAudit(final ResultSet rs)
            throws SQLException {
        Timestamp rsDateCreation = rs.getTimestamp(SQL.ENTITES.ATTRIBUTS.DATE_CREATION);
        String rsUserCreation = rs.getString(SQL.ENTITES.ATTRIBUTS.USER_CREATION);
        Timestamp rsDateModification = rs.getTimestamp(SQL.ENTITES.ATTRIBUTS.DATE_MODIFICATION);
        String rsUserModification = rs.getString(SQL.ENTITES.ATTRIBUTS.USER_MODIFICATION);
        AuditBase.Builder builder = AuditBase.builder()
                .userCreation(rsUserCreation)
                .userModification(rsUserModification);
        if (rsDateCreation != null) {
            builder.dateCreation(rsDateCreation.toInstant());
        }
        if (rsDateModification != null) {
            builder.dateModification(rsDateModification.toInstant());
        }
        return builder.build();
    }

    protected abstract void createEntity(Identifiant id, E entite)
            throws SQLException, PersistenceException;

    protected abstract void updateEntity(E entite)
            throws SQLException, PersistenceException;

    protected abstract E readEntite(ResultSet rs) throws SQLException;

    protected void retrieveEntitesDependantes(final E entite)
            throws SQLException, PersistenceException {
        //PAr défaut ne fait rien
    }

}
