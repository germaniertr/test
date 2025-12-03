package recette.datasource.db;

import core.datasource.DatabaseSetup;
import recette.datasource.IngredientMapper;
import recette.datasource.MapperManager;
import recette.datasource.RecetteMapper;
import recette.datasource.UniteMapper;

/**
 *
 * @author dominique huguenin (dominique.huguenin@rpn.ch)
 */
public class DbMapperManagerImpl implements MapperManager {

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
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
