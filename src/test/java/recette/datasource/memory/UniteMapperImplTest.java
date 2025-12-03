package recette.datasource.memory;

import core.datasource.PersistenceException;

/**
 *
 * @author dominique huguenin (dominique.huguenin@rpn.ch)
 */
public class UniteMapperImplTest extends recette.datasource.UniteMapperImplTest {

    public UniteMapperImplTest() throws PersistenceException {
        super(MemoryMapperManagerImpl.getInstance());

        filtreRef = "^.*(c.s|g|brins).*$";
    }
}
