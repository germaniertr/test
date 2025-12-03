package recette.datasource.db;

import core.datasource.DatabaseSetup;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import recette.datasource.IngredientMapper;
import recette.datasource.MapperManager;
import recette.datasource.RecetteMapper;
import recette.datasource.UniteMapper;

/**
 *
 * @author dominique huguenin (dominique.huguenin@rpn.ch)
 */
public final class DbMapperManagerImpl implements MapperManager {

    private static DbMapperManagerImpl mapperManager;
    private final DataSource datasource;

    public static MapperManager getInstance(final DataSource datasource) {
        if (mapperManager == null) {
            mapperManager = new DbMapperManagerImpl(datasource);
        }
        return mapperManager;
    }
    private DatabaseSetupImpl databaseSetup;

    private DbMapperManagerImpl(final DataSource datasource) {
        this.datasource = datasource;
    }

    Connection getConnection() throws SQLException {
        return this.datasource.getConnection();
    }

    @Override
    public UniteMapper getUniteMapper() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public IngredientMapper getIngredientMapper() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RecetteMapper getRecetteMapper() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DatabaseSetup getDatabaseSetup() {
        if (this.databaseSetup == null) {
            this.databaseSetup = new DatabaseSetupImpl(this);
        }
        return this.databaseSetup;
    }

}
