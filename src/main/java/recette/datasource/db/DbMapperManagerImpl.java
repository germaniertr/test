package recette.datasource.db;

import core.datasource.DatabaseSetup;
import core.datasource.PersistenceException;
import core.datasource.db.StatementManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import recette.datasource.IngredientMapper;
import recette.datasource.MapperManager;
import recette.datasource.RecetteMapper;
import recette.datasource.UniteMapper;

/**
 *
 * @author dominique huguenin (dominique.huguenin@rpn.ch)
 */
public final class DbMapperManagerImpl implements MapperManager, StatementManager {

    private final Connection connection;

    private DatabaseSetupImpl databaseSetup;
    private UniteMapperImpl uniteMapper;
    private IngredientMapperImpl ingredientMapper;
    private RecetteMapperImpl recetteMapper;

    DbMapperManagerImpl(final Connection connection) {
        this.connection = connection;
    }

    @Override
    public UniteMapper getUniteMapper() {
        if (this.uniteMapper == null) {
            this.uniteMapper = new UniteMapperImpl(this);
        }
        return this.uniteMapper;
    }

    @Override
    public IngredientMapper getIngredientMapper() {
        if (this.ingredientMapper == null) {
            this.ingredientMapper = new IngredientMapperImpl(this);
        }
        return this.ingredientMapper;
    }

    @Override
    public RecetteMapper getRecetteMapper() {
        if (this.recetteMapper == null) {
            this.recetteMapper = new RecetteMapperImpl(this);
        }
        return this.recetteMapper;

    }

    @Override
    public DatabaseSetup getDatabaseSetup() {
        if (this.databaseSetup == null) {
            this.databaseSetup = new DatabaseSetupImpl(this);
        }
        return this.databaseSetup;
    }

    @Override
    public Statement createStatement() throws PersistenceException {
        try {
            return this.connection.createStatement();
        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }

    }

    @Override
    public PreparedStatement prepareStatement(final String query)
            throws PersistenceException {
        try {
            return this.connection.prepareStatement(query);
        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }
    }

}
