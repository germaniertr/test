package recette.datasource.db;

import core.datasource.EntiteTropAnciennePersistenceException;
import core.datasource.PersistenceException;
import core.domain.Audit;
import core.domain.Identifiant;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import recette.datasource.UniteMapper;
import recette.domain.Unite;
import recette.domain.UniteBase;

/**
 *
 * @author dominique huguenin (dominique.huguenin@rpn.ch)
 */
//CHECKSTYLE.OFF: MagicNumber
public class UniteMapperImpl extends EntiteMapperImpl<Unite> implements UniteMapper {

    UniteMapperImpl(final DbMapperManagerImpl mm) {
        super(mm,
                SQL.UNITES.SELECT_BY_UUID,
                SQL.UNITES.SELECT_BY_FILTRE,
                SQL.UNITES.DELETE_BY_UUID);
    }

    @Override
    protected void createEntity(final Identifiant id, final Unite entite)
            throws SQLException, PersistenceException {
        try (PreparedStatement ps
                = this.getMapperManager().prepareStatement(SQL.UNITES.INSERT)) {

            ps.setString(1, id.getUUID());

            if (entite.getCode() != null) {
                ps.setString(2,
                        entite.getCode());
            } else {
                ps.setNull(2,
                        Types.VARCHAR);
            }

            ps.executeUpdate();
        }
    }

    @Override
    protected void updateEntity(final Unite entite)
            throws SQLException, PersistenceException {
        try (PreparedStatement ps
                = this.getMapperManager().prepareStatement(SQL.UNITES.UPDATE)) {

            if (entite.getCode() != null) {
                ps.setString(1,
                        entite.getCode());
            } else {
                ps.setNull(1,
                        Types.VARCHAR);
            }

            ps.setString(2,
                    entite.getIdentifiant().getUUID());

            ps.setLong(3,
                    entite.getVersion());

            int row = ps.executeUpdate();
            if (row == 0) {
                throw new EntiteTropAnciennePersistenceException(
                        entite.toString());
            }

        }
    }

    @Override
    protected Unite readEntite(final ResultSet rs)
            throws SQLException {
        Identifiant identifiant = readIdentifiant(rs);

        String code = rs.getString(SQL.UNITES.ATTRIBUTS.CODE);
        Long version = rs.getLong(SQL.ENTITES.ATTRIBUTS.VERSION);
        Audit audit = readAudit(rs);

        Unite entite = UniteBase.builder()
                .identifiant(identifiant)
                .version(version)
                .audit(audit)
                .code(code)
                .build();

        return entite;
    }
}
//CHECKSTYLE.ON: MagicNumber
