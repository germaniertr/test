package recette.datasource.db;

import core.datasource.PersistenceException;
import core.datasource.TransactionManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import recette.datasource.MapperManager;

/**
 *
 * @author dominique huguenin <dominique.huguenin at rpn.ch>
 */
public final class DbTransactionManagerImpl
        implements TransactionManager<TransactionManager.Operation<MapperManager>> {

    private static final Logger LOG = Logger.getLogger(DbTransactionManagerImpl.class.getName());

    private static TransactionManager transactionManager;

    static TransactionManager getInstance(final DataSource datasource) {
        if (transactionManager == null) {
            transactionManager = new DbTransactionManagerImpl(datasource);
        }
        return transactionManager;

    }
    private final DataSource datasource;

    private DbTransactionManagerImpl(final DataSource datasource) {
        this.datasource = datasource;
    }

    @Override
    public Object executeTransaction(final Operation<MapperManager> operation) throws PersistenceException {
        try (Connection conn = datasource.getConnection()) {
            conn.setAutoCommit(false);

            Object returnValue = operation.execute(
                    new DbMapperManagerImpl(conn));

            conn.commit();
            return returnValue;
        } catch (SQLException ex) {

            LOG.log(Level.SEVERE,
                    null,
                    ex);
            throw new PersistenceException(ex);
        }

    }

}
