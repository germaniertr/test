package core.datasource.db;

import core.datasource.PersistenceException;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public interface StatementManager {

    Statement createStatement() throws PersistenceException;

    PreparedStatement prepareStatement(String query) throws PersistenceException;

}
