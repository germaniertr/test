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
import recette.datasource.IngredientMapper;
import recette.datasource.RecetteRef;
import recette.domain.Ingredient;
import recette.domain.IngredientBase;
import recette.domain.RecetteBase;

/**
 *
 * @author dominique huguenin (dominique.huguenin@rpn.ch)
 */
public class IngredientMapperImpl implements IngredientMapper {

    private final DbMapperManagerImpl mapperManager;
    private static final Logger LOG = Logger.getLogger(UniteMapperImpl.class.getName());

    public IngredientMapperImpl(final DbMapperManagerImpl mm) {
        this.mapperManager = mm;
    }

    @Override
    public Ingredient create(final Ingredient entite) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet.");
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(final Ingredient entite) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private Ingredient readEntite(final ResultSet rs) throws SQLException {
        Identifiant identifiant
                = readIdentifiant(rs);
        String nom
                = rs.getString(SQL.INGREDIENTS.ATTRIBUTS.NOM);
        String detail
                = rs.getString(SQL.INGREDIENTS.ATTRIBUTS.DETAIL);
        String recetteUUID
                = rs.getString(SQL.INGREDIENTS.ATTRIBUTS.RECETTES_UUID);

        IngredientBase.Builder builder
                = IngredientBase.builder()
                        .identifiant(identifiant)
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

}
