package recette.datasource.db;

import core.datasource.PersistenceException;

/**
 *
 * @author dominique huguenin (dominique.huguenin at rpn.ch)
 */
public class RecetteMapperImplTest  extends recette.datasource.RecetteMapperImplTest {
    
    public RecetteMapperImplTest() throws PersistenceException {
        super(DbMapperManagerImpl.getInstance(TestDataSourceFactory.getInstance()));  
        
        filtreRef1 = "poire or poivron or tomate";
        filtreRef2 = "aubergine";
        
    }
}
