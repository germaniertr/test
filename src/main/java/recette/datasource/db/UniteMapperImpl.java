package recette.datasource.db;

import core.datasource.ContrainteNotNullPersistenceException;
import core.datasource.ContrainteUniquePersistenceException;
import core.datasource.EntiteInconnuePersistenceException;
import core.datasource.EntiteUtiliseePersistenceException;
import core.datasource.PersistenceException;
import core.datasource.db.SQL_ERREUR_CODES;
import core.domain.Identifiant;
import core.domain.IdentifiantBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import recette.datasource.UniteMapper;
import recette.domain.Unite;
import recette.domain.UniteBase;

/**
 *
 * @author dominique huguenin (dominique.huguenin@rpn.ch)
 */
public class UniteMapperImpl implements UniteMapper {

    private final DbMapperManagerImpl mapperManager;
    private static final Logger LOG = Logger.getLogger(UniteMapperImpl.class.getName());

    UniteMapperImpl(final DbMapperManagerImpl mm) {
        this.mapperManager = mm;
    }

    @Override
    public Unite create(final Unite entite) throws PersistenceException {
        if (entite == null) {
            return null;
        }
        Unite nouvelEntite = null;
        Identifiant id = IdentifiantBase.builder().build();

        try (Connection connection = this.mapperManager.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement ps
                    = connection.prepareStatement(SQL.UNITES.INSERT)) {

                ps.setString(1, id.getUUID());

                if (entite.getCode() != null) {
                    ps.setString(2,
                            entite.getCode());
                } else {
                    ps.setNull(2,
                            Types.VARCHAR);
                }

                ps.executeUpdate();

                nouvelEntite = this.retrieve(connection, id);
            }

            connection.commit();

        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
            if (ex.getSQLState()
                    .equals(SQL_ERREUR_CODES.POSTGRESQL.CONSTRAINT_NOT_NULL_VIOLATION)) {
                throw new ContrainteNotNullPersistenceException(ex);
            }
            if (ex.getSQLState()
                    .equals(SQL_ERREUR_CODES.POSTGRESQL.CONSTRAINT_UNIQUE_VIOLATION)) {
                throw new ContrainteUniquePersistenceException(ex);
            }
            if (ex.getSQLState()
                    .equals(SQL_ERREUR_CODES.POSTGRESQL.CONSTRAINT_FOREIGN_KEY_VIOLATION)) {
                throw new EntiteInconnuePersistenceException(ex);
            }

            throw new PersistenceException(ex);
        }

        return nouvelEntite;
    }

    private Unite retrieve(final Connection connection, final Identifiant id) throws PersistenceException {
        Unite unite = null;

        try (PreparedStatement ps
                = connection.prepareStatement(SQL.UNITES.SELECT_BY_UUID)) {
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
    public Unite retrieve(final Identifiant id) throws PersistenceException {
        if (id == null) {
            return null;
        }
        Unite unite = null;

        try (Connection connection = this.mapperManager.getConnection()) {
            connection.setAutoCommit(false);

            unite = this.retrieve(connection, id);

            connection.commit();
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new PersistenceException(ex);
        }

        return unite;
    }

    @Override
    public List<Unite> retrieve(final String filtre) throws PersistenceException {
        List<Unite> unites = new ArrayList<>();

        if (filtre == null) {
            return unites;
        }

        try (Connection connection = this.mapperManager.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement ps
                    = connection.prepareStatement(SQL.UNITES.SELECT_BY_FILTRE)) {
                ps.setString(1, filtre);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Unite unite = readEntite(rs);
                    if (unite != null) {
                        unites.add(unite);
                    }
                }
            }

            connection.commit();
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new PersistenceException(ex);
        }

        return unites;

    }

    @Override
    public void update(final Unite entite) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(final Unite entite) throws PersistenceException {
        /*pré-condition*/
        if (entite == null) {
            return;
        }

        if (entite.getIdentifiant() == null) {
            return;
        }

        Unite entiteTrouvee
                = this.retrieve(entite.getIdentifiant());
        if (entiteTrouvee == null) {
            throw new EntiteInconnuePersistenceException(
                    String.format("Erreur: l'entitée (%s) "
                            + "n'est pas connue!",
                            entite.toString()));
        }

        /* traitement*/
        try (Connection connection = this.mapperManager.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement ps
                    = connection.prepareStatement(SQL.UNITES.DELETE_BY_UUID)) {
                ps.setString(1, entite.getIdentifiant().getUUID());

                ps.executeUpdate();
            }

            connection.commit();
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
            if (ex.getSQLState().equals(
                    SQL_ERREUR_CODES.POSTGRESQL.CONSTRAINT_FOREIGN_KEY_VIOLATION)) {
                throw new EntiteUtiliseePersistenceException(ex);
            }

            throw new PersistenceException(ex);
        }
    }

    private Unite readEntite(final ResultSet rs) throws SQLException {
        Identifiant identifiant = readIdentifiant(rs);

        String code = rs.getString(SQL.UNITES.ATTRIBUTS.CODE);

        Unite entite = UniteBase.builder()
                .identifiant(identifiant)
                .code(code)
                .build();

        return entite;
    }

    protected Identifiant readIdentifiant(final ResultSet rs)
            throws SQLException {
        String uuid = rs.getString(SQL.ENTITES.ATTRIBUTS.UUID);

        return IdentifiantBase.builder()
                .uuid(uuid)
                .build();
    }
}
