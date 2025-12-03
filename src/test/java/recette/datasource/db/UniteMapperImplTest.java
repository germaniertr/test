package recette.datasource.db;

import core.datasource.PersistenceException;

/**
 *
 * @author dominique huguenin (dominique.huguenin at rpn.ch)
 */
public class UniteMapperImplTest  extends recette.datasource.UniteMapperImplTest {
    
    public UniteMapperImplTest() throws PersistenceException {
        super(DbMapperManagerImpl.getInstance(TestDataSourceFactory.getInstance()));  
        
        filtreRef = "c.s or g or brin";
        
    }
}
