package recette.datasource.db;

import core.datasource.ContrainteNotNullPersistenceException;
import core.datasource.ContrainteUniquePersistenceException;
import core.datasource.EntiteInconnuePersistenceException;
import core.datasource.EntiteTropAnciennePersistenceException;
import core.datasource.EntiteUtiliseePersistenceException;
import core.datasource.Mapper;
import core.datasource.PersistenceException;
import core.datasource.db.SQL_ERREUR_CODES;
import core.domain.Audit;
import core.domain.AuditBase;
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
import recette.domain.Unite;

/**
 *
 * @author dominique huguenin (dominique.huguenin@rpn.ch)
 */
public abstract class EntiteMapperImpl implements Mapper<Unite> {

    protected static final Logger LOG = Logger.getLogger(UniteMapperImpl.class.getName());
    private final DbMapperManagerImpl mapperManager;
    private final String querySelectById;
    private final String querySelectByFiltre;
    private final String queryDeleteById;

    public EntiteMapperImpl(final DbMapperManagerImpl mm,
            final String querySelectById,
            final String querySelectByFiltre,
            final String queryDeleteById) {

        this.mapperManager = mm;
        this.querySelectById = querySelectById;
        this.querySelectByFiltre = querySelectByFiltre;
        this.queryDeleteById = queryDeleteById;

    }

    public DbMapperManagerImpl getMapperManager() {
        return mapperManager;
    }

    @Override
    public Unite create(final Unite entite)
            throws PersistenceException {
        if (entite == null) {
            return null;
        }
        Unite nouvelEntite = null;
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
    public Unite retrieve(final Identifiant id)
            throws PersistenceException {
        if (id == null) {
            return null;
        }
        Unite unite = null;
        try (PreparedStatement ps = this.mapperManager.prepareStatement(this.querySelectById)) {
            ps.setString(1, id.getUUID());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                unite = readEntite(rs);
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new PersistenceException(ex);
        }
        return unite;
    }

    @Override
    public List<Unite> retrieve(final String filtre)
            throws PersistenceException {
        List<Unite> unites = new ArrayList<>();
        if (filtre == null) {
            return unites;
        }
        try (PreparedStatement ps
                = this.mapperManager.prepareStatement(this.querySelectByFiltre)) {
            ps.setString(1, filtre);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Unite unite = readEntite(rs);
                if (unite != null) {
                    unites.add(unite);
                }
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new PersistenceException(ex);
        }
        return unites;
    }

    @Override
    public void update(final Unite entite)
            throws PersistenceException {
        if (entite == null) {
            return;
        }
        if (entite.getIdentifiant() == null) {
            return;
        }
        Unite entiteTrouvee = this.retrieve(entite.getIdentifiant());
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
    //CHECKSTYLE.ON: MagicNumber

    @Override
    public void delete(final Unite entite)
            throws PersistenceException {
        /*pré-condition*/
        if (entite == null) {
            return;
        }
        if (entite.getIdentifiant() == null) {
            return;
        }
        Unite entiteTrouvee = this.retrieve(entite.getIdentifiant());
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

    protected abstract void createEntity(Identifiant id, Unite entite)
            throws SQLException, PersistenceException;

    protected abstract void updateEntity(Unite entite)
            throws SQLException, PersistenceException;

    protected abstract Unite readEntite(ResultSet rs) throws SQLException;

}
