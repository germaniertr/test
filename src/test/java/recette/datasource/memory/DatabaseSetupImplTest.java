package recette.datasource.memory;

/**
 *
 * @author dominique huguenin (dominique.huguenin at rpn.ch)
 */
public class DatabaseSetupImplTest extends recette.datasource.DatabaseSetupImplTest {

    public DatabaseSetupImplTest() {
        super(MemoryMapperManagerImpl.getInstance());
    }

}
