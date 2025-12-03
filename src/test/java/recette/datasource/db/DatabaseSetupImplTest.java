package recette.datasource.db;

/**
 *
 * @author dominique huguenin (dominique.huguenin@rpn.ch)
 */
public class DatabaseSetupImplTest extends recette.datasource.DatabaseSetupImplTest {

    public DatabaseSetupImplTest() {
        super(DbMapperManagerImpl.getInstance(TestDataSourceFactory.getInstance()));
    }

}
