package recette.datasource.db;

import core.datasource.PersistenceException;

/**
 *
 * @author dominique huguenin (dominique.huguenin at rpn.ch)
 */
public class IngredientMapperImplTest  extends recette.datasource.IngredientMapperImplTest {
    
    public IngredientMapperImplTest() throws PersistenceException {
        super(DbMapperManagerImpl.getInstance(TestDataSourceFactory.getInstance()));  
        
        filtreRef = "aubergine or tomate or thym";
        
    }
}
