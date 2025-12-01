package recette.datasource.memory;

import core.datasource.DatabaseSetup;
import core.datasource.PersistenceException;

/**
 *
 * @author dominique huguenin (dominique.huguenin@rpn.ch)
 */
public class DatabaseSetupImpl implements DatabaseSetup {

    @Override
    public void createTables() throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void dropTables() throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void insertData() throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
