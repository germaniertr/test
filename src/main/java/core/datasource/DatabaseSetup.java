package core.datasource;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public interface DatabaseSetup {

    void createTables() throws PersistenceException;

    void dropTables() throws PersistenceException;

    void insertData() throws PersistenceException;
}
