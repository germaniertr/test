package recette.datasource.db;

import core.datasource.PersistenceException;
import core.domain.Identifiant;
import core.domain.IdentifiantBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class RecetteMapperImpl implements RecetteMapper {

    private final DbMapperManagerImpl mapperManager;
    private static final Logger LOG = Logger.getLogger(UniteMapperImpl.class.getName());

    public RecetteMapperImpl(final DbMapperManagerImpl mm) {
        this.mapperManager = mm;
    }

    @Override
    public Recette create(final Recette entite) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Recette retrieve(final Identifiant id) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet.");
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
        throw new UnsupportedOperationException("Not supported yet.");
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

}
