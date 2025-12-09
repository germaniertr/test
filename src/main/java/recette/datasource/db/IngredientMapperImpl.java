package recette.datasource.db;

import core.datasource.db.EntiteMapperImpl;
import core.datasource.EntiteTropAnciennePersistenceException;
import core.datasource.PersistenceException;
import core.domain.Audit;
import core.domain.Identifiant;
import core.domain.IdentifiantBase;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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
public class IngredientMapperImpl
        extends EntiteMapperImpl<DbMapperManagerImpl, Ingredient>
        implements IngredientMapper {

    IngredientMapperImpl(final DbMapperManagerImpl mm) {
        super(mm,
                SQL.INGREDIENTS.SELECT_BY_UUID,
                SQL.INGREDIENTS.SELECT_BY_FILTRE,
                SQL.INGREDIENTS.DELETE_BY_UUID);
    }

    @Override
    protected void createEntity(final Identifiant id, final Ingredient entite)
            throws SQLException, PersistenceException {
        try (PreparedStatement ps
                = this.getMapperManager().prepareStatement(SQL.INGREDIENTS.INSERT)) {

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
        }

    }

    @Override
    protected void updateEntity(final Ingredient entite)
            throws SQLException, PersistenceException {
        try (PreparedStatement ps
                = this.getMapperManager().prepareStatement(SQL.INGREDIENTS.UPDATE)) {
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
    }

    @Override
    protected Ingredient readEntite(final ResultSet rs) throws SQLException {
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

}
//CHECKSTYLE.ON: MagicNumber

