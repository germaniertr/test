package recette.datasource.memory;

import core.datasource.PersistenceException;
import java.util.List;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import recette.datasource.MapperManager;
import recette.domain.Unite;

/**
 *
 * @author dominique huguenin (dominique.huguenin@rpn.ch)
 */
public class UniteMapperImplTest {

    private final MapperManager mapperManager;
    private final String filtreRef;

    public UniteMapperImplTest() throws PersistenceException {
        mapperManager = MemoryMapperManagerImpl.getInstance();
        mapperManager.getDatabaseSetup().dropTables();
        mapperManager.getDatabaseSetup().createTables();
        mapperManager.getDatabaseSetup().insertData();

        filtreRef = "^.*(c.s|g|brins).*$";
    }

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testRetrieve_String() throws Exception {
        Pattern pattern = Pattern.compile(filtreRef);
        List<Unite> entites1 = mapperManager.getUniteMapper().retrieve(filtreRef);
        Assertions.assertEquals(3, entites1.size());
        for (Unite i : entites1) {
            Assertions.assertTrue(pattern.matcher(i.getCode()).find());
        }
    }

    @Test
    public void testRetrieve_StringNull() throws Exception {
        String filtre = null;
        List<Unite> entites1 = mapperManager.getUniteMapper().retrieve(filtre);
        Assertions.assertEquals(0, entites1.size());
    }

    @Test
    public void testRetrieve_String_Detacher() throws Exception {
        List<Unite> entites1 = mapperManager.getUniteMapper().retrieve(filtreRef);
        List<Unite> entites2 = mapperManager.getUniteMapper().retrieve(filtreRef);

        Assertions.assertEquals(entites1, entites2);
        Assertions.assertNotSame(entites1, entites2);
        Assertions.assertEquals(entites1.size(), entites2.size());
        for (int i = 0; i < entites1.size(); i += 1) {
            Assertions.assertEquals(entites1.get(i), entites2.get(i));
            Assertions.assertNotSame(entites1.get(i), entites2.get(i));
        }
    }

}
