package recette.datasource.memory;

import core.datasource.PersistenceException;
import core.datasource.TransactionManager;
import core.datasource.TransactionManager.Operation;
import recette.datasource.MapperManager;
import recette.domain.DemoData;

/**
 *
 * @author dominique huguenin (dominique.huguenin@rpn.ch)
 */
public final class MemoryTransactionManagerImpl implements TransactionManager<Operation<MapperManager>> {

    private static MemoryTransactionManagerImpl transactionManager;

    static TransactionManager getInstance() {
        if (transactionManager == null) {
            transactionManager = new MemoryTransactionManagerImpl();
        }

        return transactionManager;

    }
    private final DemoData data;

    private MemoryTransactionManagerImpl() {
        this.data = new DemoData();
    }

    @Override
    public Object executeTransaction(final Operation<MapperManager> operation)
            throws PersistenceException {
        return operation.execute(new MemoryMapperManagerImpl(data));
    }

}
