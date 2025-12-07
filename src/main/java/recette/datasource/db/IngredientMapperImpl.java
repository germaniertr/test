package recette.datasource.db;

import core.datasource.ContrainteNotNullPersistenceException;
import core.datasource.ContrainteUniquePersistenceException;
import core.datasource.EntiteInconnuePersistenceException;
import core.datasource.EntiteTropAnciennePersistenceException;
import core.datasource.EntiteUtiliseePersistenceException;
import core.datasource.PersistenceException;
import core.datasource.db.SQL_ERREUR_CODES;
import core.domain.Audit;
import core.domain.AuditBase;
import core.domain.Identifiant;
import core.domain.IdentifiantBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import recette.datasource.IngredientMapper;
import recette.datasource.RecetteRef;
import recette.domain.Ingredient;
import recette.domain.IngredientBase;
import recette.domain.RecetteBase;

/**
 *
 * @author dominique huguenin (dominique.huguenin@rpn.ch)
 */
//CHECKSTYLE.OFF: MagicNumber
public class IngredientMapperImpl implements IngredientMapper {

    private final DbMapperManagerImpl mapperManager;
    private static final Logger LOG = Logger.getLogger(UniteMapperImpl.class.getName());

    public IngredientMapperImpl(final DbMapperManagerImpl mm) {
        this.mapperManager = mm;
    }

    @Override
    public Ingredient create(final Ingredient entite) throws PersistenceException {
        if (entite == null) {
            return null;
        }
        Ingredient nouvelEntite = null;
        Identifiant id = IdentifiantBase.builder().build();

        try (Connection connection = this.mapperManager.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement ps
                    = connection.prepareStatement(SQL.INGREDIENTS.INSERT)) {

                ps.setString(1,
                        id.getUUID());

                if (entite.getNom() != null) {
                    ps.setString(2,
                            entite.getNom());
                } else {
                    ps.setNull(2,
                            Types.VARCHAR);
                }
                if (entite.getDetail() != null) {
                    ps.setString(3,
                            entite.getDetail());
                } else {
                    ps.setNull(3,
                            Types.VARCHAR);
                }
                if (entite.getRecette() != null) {
                    ps.setString(4,
                            entite.getRecette()
                                    .getIdentifiant()
                                    .getUUID());
                } else {
                    ps.setNull(4,
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

    private Ingredient retrieve(final Connection connection,
            final Identifiant id) throws PersistenceException {
        Ingredient unite = null;

        try (PreparedStatement ps
                = connection.prepareStatement(SQL.INGREDIENTS.SELECT_BY_UUID)) {
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
    public Ingredient retrieve(final Identifiant id) throws PersistenceException {
        if (id == null) {
            return null;
        }
        Ingredient entite = null;

        try (Connection connection = this.mapperManager.getConnection()) {
            connection.setAutoCommit(false);

            entite = this.retrieve(connection, id);

            connection.commit();
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new PersistenceException(ex);
        }

        return entite;
    }

    @Override
    public List<Ingredient> retrieve(final String filtre) throws PersistenceException {
        List<Ingredient> ingredients = new ArrayList<>();

        if (filtre == null) {
            return ingredients;
        }

        try (Connection connection = this.mapperManager.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement ps
                    = connection.prepareStatement(SQL.INGREDIENTS.SELECT_BY_FILTRE)) {
                ps.setString(1, filtre);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Ingredient unite = readEntite(rs);
                    if (unite != null) {
                        ingredients.add(unite);
                    }
                }
            }

            connection.commit();

        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new PersistenceException(ex);
        }

        return ingredients;

    }

    @Override
    public void update(final Ingredient entite) throws PersistenceException {
        if (entite == null) {
            return;
        }

        if (entite.getIdentifiant() == null) {
            return;
        }

        Ingredient entiteTrouvee
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
                    = connection.prepareStatement(SQL.INGREDIENTS.UPDATE)) {
                if (entite.getNom() != null) {
                    ps.setString(1,
                            entite.getNom());
                } else {
                    ps.setNull(1,
                            Types.VARCHAR);
                }
                if (entite.getDetail() != null) {
                    ps.setString(2,
                            entite.getDetail());
                } else {
                    ps.setNull(2,
                            Types.VARCHAR);
                }
                if (entite.getRecette() != null) {
                    ps.setString(3,
                            entite.getRecette()
                                    .getIdentifiant()
                                    .getUUID());
                } else {
                    ps.setNull(3,
                            Types.VARCHAR);
                }

                ps.setString(4,
                        entite.getIdentifiant().getUUID());

                ps.setLong(5,
                        entite.getVersion());

                int row = ps.executeUpdate();
                if (row == 0) {
                    throw new EntiteTropAnciennePersistenceException(
                            entite.toString());
                }
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

    }

    @Override
    public void delete(final Ingredient entite) throws PersistenceException {
        /*pré-condition*/
        if (entite == null) {
            return;
        }

        if (entite.getIdentifiant() == null) {
            return;
        }

        Ingredient entiteTrouvee
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
                    = connection.prepareStatement(SQL.INGREDIENTS.DELETE_BY_UUID)) {
                ps.setString(1, entite.getIdentifiant().getUUID());

                ps.setLong(2,
                        entite.getVersion());

                int row = ps.executeUpdate();
                if (row == 0) {
                    throw new EntiteTropAnciennePersistenceException(
                            entite.toString());
                }
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

    private Ingredient readEntite(final ResultSet rs) throws SQLException {
        Identifiant identifiant
                = readIdentifiant(rs);
        Long version = rs.getLong(SQL.ENTITES.ATTRIBUTS.VERSION);
        Audit audit = readAudit(rs);

        String nom
                = rs.getString(SQL.INGREDIENTS.ATTRIBUTS.NOM);
        String detail
                = rs.getString(SQL.INGREDIENTS.ATTRIBUTS.DETAIL);
        String recetteUUID
                = rs.getString(SQL.INGREDIENTS.ATTRIBUTS.RECETTES_UUID);

        IngredientBase.Builder builder
                = IngredientBase.builder()
                        .identifiant(identifiant)
                        .version(version)
                        .audit(audit)
                        .nom(nom)
                        .detail(detail);

        if (recetteUUID != null) {
            builder.recette(new RecetteRef(RecetteBase.builder()
                    .identifiant(IdentifiantBase.builder()
                            .uuid(recetteUUID)
                            .build())
                    .build()));
        }

        Ingredient entite = builder.build();

        return entite;

    }

    protected Identifiant readIdentifiant(final ResultSet rs)
            throws SQLException {
        String uuid = rs.getString(SQL.ENTITES.ATTRIBUTS.UUID);

        return IdentifiantBase.builder()
                .uuid(uuid)
                .build();
    }

    protected Audit readAudit(final ResultSet rs)
            throws SQLException {
        Timestamp rsDateCreation
                = rs.getTimestamp(SQL.ENTITES.ATTRIBUTS.DATE_CREATION);
        String rsUserCreation
                = rs.getString(SQL.ENTITES.ATTRIBUTS.USER_CREATION);
        Timestamp rsDateModification
                = rs.getTimestamp(SQL.ENTITES.ATTRIBUTS.DATE_MODIFICATION);
        String rsUserModification
                = rs.getString(SQL.ENTITES.ATTRIBUTS.USER_MODIFICATION);

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

}
//CHECKSTYLE.ON: MagicNumber

