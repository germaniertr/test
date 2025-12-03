package recette.datasource.db;

import core.datasource.PersistenceException;
import core.domain.IdentifiantBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import recette.domain.Composant;
import recette.domain.DemoData;
import recette.domain.Ingredient;
import recette.domain.Recette;
import recette.domain.Unite;

/**
 *
 * @author dominique huguenin (dominique.huguenin at rpn.ch)
 */
public class ConnexionTest {

    private DataSource datasource;

    public ConnexionTest() {
    }

    @BeforeEach
    public void setUp() {
        datasource = TestDataSourceFactory.getInstance();
    }

    @Test
    public void creerDatabaseTest() throws SQLException, PersistenceException {
        int[] status;

        try (Connection conn = datasource.getConnection()) {
            conn.setAutoCommit(false);

            try (Statement requete = conn.createStatement()) {

                requete.addBatch(SQL.COMPOSANTS.DROP_TABLE);
                requete.addBatch(SQL.INGREDIENTS.DROP_TABLE);
                requete.addBatch(SQL.UNITES.DROP_TABLE);
                requete.addBatch(SQL.RECETTES.DROP_TABLE);

                requete.addBatch(SQL.UNITES.CREATE_TABLE);
                requete.addBatch(SQL.INGREDIENTS.CREATE_TABLE);
                requete.addBatch(SQL.COMPOSANTS.CREATE_TABLE);
                requete.addBatch(SQL.RECETTES.CREATE_TABLE);

                requete.addBatch(SQL.UNITES.ALTER_TABLE);
                requete.addBatch(SQL.INGREDIENTS.ALTER_TABLE);
                requete.addBatch(SQL.COMPOSANTS.ALTER_TABLE);
                requete.addBatch(SQL.RECETTES.ALTER_TABLE);

                status = requete.executeBatch();
                Assertions.assertEquals(12, status.length);
            }

            conn.commit();

        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }

        DemoData data = new DemoData();
        data.initialisation();

        try (Connection conn = datasource.getConnection()) {
            conn.setAutoCommit(false);
            conn.createStatement()
                    .execute("SET CONSTRAINTS ALL DEFERRED");

            try (PreparedStatement rc
                    = conn.prepareStatement(SQL.UNITES.INSERT)) {

                for (Unite e : data.getUnites().values()) {

                    rc.setString(1, e.getIdentifiant().getUUID());
                    rc.setString(2, e.getCode());

                    rc.addBatch();
                }
                status = rc.executeBatch();

                Assertions.assertEquals(data.getUnites().size(), status.length);

            }

            try (PreparedStatement rc
                    = conn.prepareStatement(SQL.INGREDIENTS.INSERT)) {

                for (Ingredient e : data.getIngredients().values()) {

                    rc.setString(1, e.getIdentifiant().getUUID());
                    rc.setString(2, e.getNom());
                    rc.setString(3, e.getDetail());
                    if (e.getRecette() != null) {
                        rc.setString(4, e.getRecette().getIdentifiant().
                                getUUID());
                    } else {
                        rc.setNull(4, java.sql.Types.VARCHAR);
                    }

                    rc.addBatch();
                }
                status = rc.executeBatch();

                Assertions.assertEquals(data.getIngredients().size(), status.length);
            }

            try (PreparedStatement rc
                    = conn.prepareStatement(SQL.RECETTES.INSERT); PreparedStatement rt
                    = conn.prepareStatement(SQL.COMPOSANTS.INSERT)) {

                for (Recette e : data.getRecettes().values()) {

                    rc.setString(1, e.getIdentifiant().getUUID());
                    rc.setString(2, e.getNom());
                    rc.setString(3, e.getDetail());
                    rc.setString(4, e.getPreparation());
                    rc.setInt(5, e.getNombrePersonnes());

                    rc.addBatch();

                    for (int i = 0; i < e.getComposants().size(); i += 1) {
                        Composant t = e.getComposants().get(i);
                        rt.setString(1, t.getIdentifiant() != null
                                ? t.getIdentifiant().getUUID()
                                : IdentifiantBase.builder().build().getUUID());
                        rt.setString(2, e.getIdentifiant().getUUID());
                        rt.setInt(3, i);
                        if (t.getQuantite() != null) {
                            rt.setDouble(4, t.getQuantite());
                        } else {
                            rt.setNull(4, java.sql.Types.DOUBLE);
                        }
                        rt.setString(5, t.getCommentaire());
                        rt.setString(6, t.getIngredient().getIdentifiant()
                                .getUUID());
                        if (t.getUnite() != null) {
                            rt.setString(7, t.getUnite().getIdentifiant()
                                    .getUUID());
                        } else {
                            rt.setNull(7, java.sql.Types.VARCHAR);
                        }
                        rt.addBatch();
                    }
                }
                status = rc.executeBatch();
                rt.executeBatch();

                Assertions.assertEquals(data.getRecettes().size(), status.length);

            }

            conn.commit();

        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }

    }

}
