package recette.datasource.db;

import core.datasource.PersistenceException;
import core.datasource.TransactionManager;
import javax.sql.DataSource;
import recette.datasource.MapperManager;

/**
 *
 * @author dominique huguenin <dominique.huguenin at rpn.ch>
 */
public final class DbTransactionManagerImpl
        implements TransactionManager<TransactionManager.Operation<MapperManager>> {

    static TransactionManager getInstance(final DataSource instance) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object executeTransaction(final Operation<MapperManager> operation) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
