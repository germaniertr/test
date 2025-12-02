package recette.datasource.memory;

import core.datasource.ContrainteNotNullPersistenceException;
import core.datasource.ContrainteUniquePersistenceException;
import core.datasource.PersistenceException;
import core.domain.Identifiant;
import core.domain.IdentifiantBase;
import java.time.Instant;
import java.util.List;
import java.util.regex.Pattern;
import jdk.dynalink.Operation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import recette.datasource.MapperManager;
import recette.domain.DemoData;
import recette.domain.Unite;
import recette.domain.UniteBase;

/**
 *
 * @author dominique huguenin (dominique.huguenin@rpn.ch)
 */
public class UniteMapperImplTest {

    private final MapperManager mapperManager;
    private final String filtreRef;
    private Identifiant identifiantCS;
    private Unite uniteCS;
    private Unite nouvelleUniteRef;

    public UniteMapperImplTest() throws PersistenceException {
        mapperManager = MemoryMapperManagerImpl.getInstance();
        mapperManager.getDatabaseSetup().dropTables();
        mapperManager.getDatabaseSetup().createTables();
        mapperManager.getDatabaseSetup().insertData();

        filtreRef = "^.*(c.s|g|brins).*$";
    }

    @BeforeEach
    public void setUp() {
        identifiantCS = IdentifiantBase.builder()
                .uuid(DemoData.UNITES.C_S.UUID)
                .build();
        uniteCS = UniteBase.builder()
                .identifiant(identifiantCS)
                .code(DemoData.UNITES.C_S.CODE)
                .build();
        nouvelleUniteRef = UniteBase.builder()
                .code("nouvelle unité " + Instant.now().toString())
                .build();
        
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

    @Test
    public void testRetrieve_Identifiant() throws Exception {
        Unite entite = mapperManager.getUniteMapper().retrieve(identifiantCS);

        Assertions.assertNotNull(entite);
        Assertions.assertEquals(uniteCS, entite);
        Assertions.assertEquals(uniteCS.getCode(), entite.getCode());
    }

    @Test
    public void testRetrieve_IdentifiantNull() throws Exception {
        Identifiant identifiant = null;
        Unite entite = mapperManager.getUniteMapper().retrieve(identifiant);
        Assertions.assertNull(entite);
    }

    @Test
    public void testRetrieve_Identifiant_Detacher() throws Exception {
        Unite entite1 = mapperManager.getUniteMapper().retrieve(identifiantCS);
        Unite entite2 = mapperManager.getUniteMapper().retrieve(identifiantCS);

        Assertions.assertEquals(entite1, entite2);
        Assertions.assertNotSame(entite1, entite2);
        entite1.setCode(entite1.getCode() + " update entite1");
        Assertions.assertNotEquals(entite1.getCode(), entite2.getCode());
    }
    
@Test
    public void testCreate() throws Exception {
        Unite nouvelleEntite = mapperManager.getUniteMapper().create(nouvelleUniteRef);

        Assertions.assertNotNull(nouvelleEntite.getIdentifiant());
        Assertions.assertEquals(nouvelleUniteRef.getCode(), nouvelleEntite.getCode());

        Unite entite = mapperManager.getUniteMapper().retrieve(nouvelleEntite.getIdentifiant());

        Assertions.assertNotNull(entite);
        Assertions.assertEquals(nouvelleEntite, entite);
        Assertions.assertNotSame(nouvelleEntite, entite);
        Assertions.assertEquals(nouvelleEntite.getCode(), entite.getCode());
    }

    @Test
    public void testCreateCodeNotUnique() throws Exception {
        Assertions.assertThrows(
                ContrainteUniquePersistenceException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                mapperManager.getUniteMapper().create(nouvelleUniteRef);
                mapperManager.getUniteMapper().create(nouvelleUniteRef);
            }
        });
    }

    @Test
    public void testCreateCodeNull() throws Exception {
        Assertions.assertThrows(
                ContrainteNotNullPersistenceException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                nouvelleUniteRef.setCode(null);
                mapperManager.getUniteMapper().create(nouvelleUniteRef);
            }
        });
    }    
}
