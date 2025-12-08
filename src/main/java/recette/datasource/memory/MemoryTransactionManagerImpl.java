package recette.datasource.memory;

import core.datasource.PersistenceException;
import core.datasource.TransactionManager;
import core.datasource.TransactionManager.Operation;

/**
 *
 * @author dominique huguenin (dominique.huguenin@rpn.ch)
 */
public class MemoryTransactionManagerImpl implements TransactionManager<Operation<MapperManager>> {


    static TransactionManager getInstance() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object executeTransaction(Object operation) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
