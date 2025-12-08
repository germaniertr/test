package recette.datasource.memory;

import core.datasource.PersistenceException;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class RecetteMapperImplTest extends recette.datasource.RecetteMapperImplTest {

    public RecetteMapperImplTest() throws PersistenceException {
        super(MemoryTransactionManagerImpl.getInstance());

        filtreRef1 = "^.*(Poires|Poivrons|tomates).*$";
        filtreRef2 = "^.*(Aubergine).*$";

    }

}
