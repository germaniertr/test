package recette.datasource.db;

import core.datasource.DatabaseSetup;
import core.datasource.PersistenceException;
import core.domain.IdentifiantBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import recette.domain.Composant;
import recette.domain.DemoData;
import recette.domain.Ingredient;
import recette.domain.Recette;
import recette.domain.Unite;

/**
 *
 * @author dominique huguenin (dominique.huguenin@rpn.ch)
 */
public class DatabaseSetupImpl implements DatabaseSetup {

    private static final Logger LOG = Logger.getLogger(DatabaseSetupImpl.class.getName());
    private final DbMapperManagerImpl mapperManager;

    DatabaseSetupImpl(final DbMapperManagerImpl mm) {
        this.mapperManager = mm;
    }

    @Override
    public void createTables() throws PersistenceException {
        try (Connection connection = this.mapperManager.getConnection()) {
            connection.setAutoCommit(false);
            try (Statement requete = connection.createStatement();) {
                requete.addBatch(SQL.UNITES.CREATE_TABLE);
                requete.addBatch(SQL.INGREDIENTS.CREATE_TABLE);
                requete.addBatch(SQL.COMPOSANTS.CREATE_TABLE);
                requete.addBatch(SQL.RECETTES.CREATE_TABLE);

                requete.addBatch(SQL.UNITES.ALTER_TABLE);
                requete.addBatch(SQL.INGREDIENTS.ALTER_TABLE);
                requete.addBatch(SQL.COMPOSANTS.ALTER_TABLE);
                requete.addBatch(SQL.RECETTES.ALTER_TABLE);

                requete.addBatch(SQL.VERROU_OPTIMISTE.CREATE_PROCEDURE);
                requete.addBatch(SQL.UNITES.CREATE_TRIGGER_VERROU_OPTIMISTE);
                requete.addBatch(SQL.INGREDIENTS.CREATE_TRIGGER_VERROU_OPTIMISTE);
                requete.addBatch(SQL.RECETTES.CREATE_TRIGGER_VERROU_OPTIMISTE);
                requete.addBatch(SQL.COMPOSANTS.CREATE_TRIGGER_VERROU_OPTIMISTE);

                requete.executeBatch();
            }
            connection.commit();
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new PersistenceException(ex);
        }

    }

    @Override
    public void dropTables() throws PersistenceException {
        try (Connection connection = this.mapperManager.getConnection()) {
            connection.setAutoCommit(false);
            try (Statement requete = connection.createStatement();) {

                requete.addBatch(SQL.UNITES.DROP_TRIGGER_VERROU_OPTIMISTE);
                requete.addBatch(SQL.INGREDIENTS.DROP_TRIGGER_VERROU_OPTIMISTE);
                requete.addBatch(SQL.RECETTES.DROP_TRIGGER_VERROU_OPTIMISTE);
                requete.addBatch(SQL.COMPOSANTS.DROP_TRIGGER_VERROU_OPTIMISTE);

                requete.addBatch(SQL.COMPOSANTS.DROP_TABLE);
                requete.addBatch(SQL.INGREDIENTS.DROP_TABLE);
                requete.addBatch(SQL.UNITES.DROP_TABLE);
                requete.addBatch(SQL.RECETTES.DROP_TABLE);

                requete.executeBatch();
            }
            connection.commit();
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new PersistenceException(ex);
        }
    }
//CHECKSTYLE.OFF: MagicNumber

    @Override
    public void insertData() throws PersistenceException {
        DemoData data = new DemoData();
        data.initialisation();

        try (Connection connection = this.mapperManager.getConnection()) {
            connection.setAutoCommit(false);
            connection.createStatement()
                    .execute("SET CONSTRAINTS ALL DEFERRED");

            try (PreparedStatement rc
                    = connection.prepareStatement(SQL.UNITES.INSERT)) {

                for (Unite e : data.getUnites().values()) {

                    rc.setString(1, e.getIdentifiant().getUUID());
                    rc.setString(2, e.getCode());

                    rc.addBatch();
                }
                rc.executeBatch();
            }

            try (PreparedStatement rc
                    = connection.prepareStatement(SQL.INGREDIENTS.INSERT)) {

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
                rc.executeBatch();
            }

            try (PreparedStatement rc
                    = connection.prepareStatement(SQL.RECETTES.INSERT); PreparedStatement rt
                    = connection.prepareStatement(SQL.COMPOSANTS.INSERT)) {

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
                rc.executeBatch();
                rt.executeBatch();

            }

            connection.commit();
        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }
    }
//CHECKSTYLE.ON: MagicNumber
}
