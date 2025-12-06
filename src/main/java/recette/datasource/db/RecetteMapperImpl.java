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
import recette.datasource.RecetteMapper;
import recette.domain.Composant;
import recette.domain.ComposantBase;
import recette.domain.Ingredient;
import recette.domain.Recette;
import recette.domain.RecetteBase;
import recette.domain.Unite;

/**
 *
 * @author dominique huguenin (dominique.huguenin@rpn.ch)
 */
//CHECKSTYLE.OFF: MagicNumber
public class RecetteMapperImpl implements RecetteMapper {

    private final DbMapperManagerImpl mapperManager;
    private static final Logger LOG = Logger.getLogger(UniteMapperImpl.class.getName());

    public RecetteMapperImpl(final DbMapperManagerImpl mm) {
        this.mapperManager = mm;
    }

    @Override
    public Recette create(final Recette entite) throws PersistenceException {
        if (entite == null) {
            return null;
        }
        Recette nouvelEntite = null;
        Identifiant id = IdentifiantBase.builder().build();

        try (Connection connection = this.mapperManager.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement ps
                    = connection.prepareStatement(SQL.RECETTES.INSERT)) {

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

                if (entite.getPreparation() != null) {
                    ps.setString(4,
                            entite.getPreparation());
                } else {
                    ps.setNull(4,
                            Types.VARCHAR);
                }

                if (entite.getNombrePersonnes() != null) {
                    ps.setInt(5,
                            entite.getNombrePersonnes());
                } else {
                    ps.setNull(5,
                            Types.INTEGER);
                }

                ps.executeUpdate();

                if (!entite.getComposants().isEmpty()) {
                    insertComposants(
                            connection,
                            id,
                            entite.getComposants());
                }

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

    private Recette retrieve(final Connection connection,
            final Identifiant id) throws PersistenceException {
        Recette entite = null;

        try (PreparedStatement ps
                = connection.prepareStatement(SQL.RECETTES.SELECT_BY_UUID)) {
            ps.setString(1, id.getUUID());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                entite = readEntite(rs);
                if (entite != null) {
                    List<Composant> composants
                            = this.retrieveComposantByUuidRecette(
                                    connection,
                                    entite.getIdentifiant());
                    for (Composant c : composants) {
                        entite.getComposants().add(c);
                    }
                }
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new PersistenceException(ex);
        }
        return entite;
    }

    @Override
    public Recette retrieve(final Identifiant id) throws PersistenceException {
        if (id == null) {
            return null;
        }
        Recette entite = null;

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
    public List<Recette> retrieve(final String filtre) throws PersistenceException {
        List<Recette> recettes = new ArrayList<>();

        if (filtre == null) {
            return recettes;
        }

        try (Connection connection = this.mapperManager.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement ps
                    = connection.prepareStatement(SQL.RECETTES.SELECT_BY_FILTRE)) {
                ps.setString(1, filtre);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Recette entite = readEntite(rs);
                    if (entite != null) {
                        List<Composant> composants
                                = this.retrieveComposantByUuidRecette(
                                        connection,
                                        entite.getIdentifiant());
                        for (Composant c : composants) {
                            entite.getComposants().add(c);

                        }

                        recettes.add(entite);
                    }
                }
            }
            connection.commit();

        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new PersistenceException(ex);
        }

        return recettes;

    }

    @Override
    public void update(final Recette entite) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(final Recette entite) throws PersistenceException {
        /*pré-condition*/
        if (entite == null) {
            return;
        }

        if (entite.getIdentifiant() == null) {
            return;
        }

        Recette entiteTrouvee
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
                    = connection.prepareStatement(SQL.RECETTES.DELETE_BY_UUID)) {
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

    private Recette readEntite(final ResultSet rs) throws SQLException {
        Identifiant identifiant = readIdentifiant(rs);

        String nom = rs.getString(
                SQL.RECETTES.ATTRIBUTS.NOM);
        String detail = rs.getString(
                SQL.RECETTES.ATTRIBUTS.DETAIL);
        String preparation = rs.getString(
                SQL.RECETTES.ATTRIBUTS.PREPARATION);
        int nombrePersonnes = rs.getInt(
                SQL.RECETTES.ATTRIBUTS.NOMBRE_PERSONNES);

        Recette entite = RecetteBase.builder()
                .identifiant(identifiant)
                .nom(nom)
                .detail(detail)
                .preparation(preparation)
                .nombrePersonnes(nombrePersonnes)
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

    private List<Composant> retrieveComposantByUuidRecette(
            final Connection connection,
            final Identifiant id)
            throws SQLException, PersistenceException {
        List<Composant> list = new ArrayList<>();
        try (PreparedStatement ps
                = connection.prepareStatement(
                        SQL.COMPOSANTS.SELECT_BY_UUID_RECETTE)) {
            ps.setString(1,
                    id.getUUID());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Composant composant = readComposant(rs);
                list.add(composant);
            }

        }

        return list;

    }

    private Composant readComposant(final ResultSet rs)
            throws SQLException, PersistenceException {
        Identifiant identifiant = readIdentifiant(rs);

        Double quantite = rs.getDouble(
                SQL.COMPOSANTS.ATTRIBUTS.QUANTITE);
        if (rs.wasNull()) {
            quantite = null;
        }
        String commentaire = rs.getString(
                SQL.COMPOSANTS.ATTRIBUTS.COMMENTAIRE);
        String ingredientUUID = rs.getString(
                SQL.COMPOSANTS.ATTRIBUTS.INGREDIENTS_UUID);
        String uniteUUID = rs.getString(
                SQL.COMPOSANTS.ATTRIBUTS.UNITES_UUID);

        Ingredient ingredient = null;
        if (ingredientUUID != null) {
            ingredient = this.mapperManager.getIngredientMapper()
                    .retrieve(IdentifiantBase.builder()
                            .uuid(ingredientUUID)
                            .build());
        }

        Unite unite = null;
        if (uniteUUID != null) {
            unite = this.mapperManager.getUniteMapper()
                    .retrieve(IdentifiantBase.builder()
                            .uuid(uniteUUID)
                            .build());
        }

        Composant entite = ComposantBase.builder()
                .identifiant(identifiant)
                .quantite(quantite)
                .commentaire(commentaire)
                .ingredient(ingredient)
                .unite(unite)
                .build();

        return entite;
    }

    private void insertComposants(
            final Connection connection,
            final Identifiant id,
            final List<Composant> composants) throws SQLException, PersistenceException {
        try (PreparedStatement ps
                = connection.prepareStatement(SQL.COMPOSANTS.INSERT)) {
            for (int ordre = 0; ordre < composants.size(); ordre += 1) {
                Composant c = composants.get(ordre);

                ps.setString(1, c.getIdentifiant() != null
                        ? c.getIdentifiant().getUUID()
                        : IdentifiantBase.builder().build().getUUID());
                ps.setString(2, id.getUUID());
                ps.setInt(3, ordre);
                if (c.getQuantite() != null) {
                    ps.setDouble(4,
                            c.getQuantite());
                } else {
                    ps.setNull(4,
                            java.sql.Types.DOUBLE);
                }
                ps.setString(5,
                        c.getCommentaire());
                ps.setString(6,
                        c.getIngredient().getIdentifiant().getUUID());
                if (c.getUnite() != null) {
                    ps.setString(7,
                            c.getUnite().getIdentifiant().getUUID());
                } else {
                    ps.setNull(7,
                            java.sql.Types.VARCHAR);
                }
                ps.addBatch();
            }

            ps.executeBatch();

        }
    }
}
//CHECKSTYLE.ON: MagicNumber
