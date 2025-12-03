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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Unite retrieve(final Identifiant id) throws PersistenceException {
        if (id == null) {
            return null;
        }
        Unite unite = null;

        try (Connection connection = this.mapperManager.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement ps
                    = connection.prepareStatement(SQL.UNITES.SELECT_BY_UUID)) {
                ps.setString(1, id.getUUID());

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    unite = readEntite(rs);
                }
            }

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
        throw new UnsupportedOperationException("Not supported yet.");
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
