package recette.datasource.memory;

import core.datasource.DatabaseSetup;
import recette.datasource.IngredientMapper;
import recette.datasource.MapperManager;
import recette.datasource.RecetteMapper;
import recette.datasource.UniteMapper;
import recette.domain.DemoData;

/**
 *
 * @author dominique huguenin (dominique.huguenin@rpn.ch)
 */
public class MemoryMapperManagerImpl implements MapperManager{

    private MemoryMapperManagerImpl(DemoData demoData) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public static MapperManager getInstance(DemoData demoData){
        throw new UnsupportedOperationException("Not supported yet.");
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
    public DatabaseSetup getDatabaseSetup() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RecetteMapper getRecetteMapper() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
