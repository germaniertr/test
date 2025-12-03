package recette.datasource.db;

import core.datasource.db.DataSourceFactory;
import javax.sql.DataSource;

/**
 *
 * @author dominique huguenin (dominique.huguenin at rpn.ch)
 */
public class TestDataSourceFactory {

    public static DataSource getInstance() {
        return DataSourceFactory.getPostgreSQLDataSource(
                "db.dev", 5432,
                "recetteTestDB",
                "recette", "recettepass");
    }

}
