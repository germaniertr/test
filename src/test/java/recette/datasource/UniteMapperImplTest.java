package recette.datasource;

import core.datasource.ContrainteNotNullPersistenceException;
import core.datasource.ContrainteUniquePersistenceException;
import core.datasource.EntiteInconnuePersistenceException;
import core.datasource.EntiteTropAnciennePersistenceException;
import core.datasource.EntiteUtiliseePersistenceException;
import core.datasource.PersistenceException;
import core.domain.Identifiant;
import core.domain.IdentifiantBase;
import java.time.Instant;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import recette.domain.DemoData;
import recette.domain.Unite;
import recette.domain.UniteBase;

/**
 *
 * @author dominique huguenin (dominique.huguenin@rpn.ch)
 */
public abstract class UniteMapperImplTest {

    private static final Logger LOG = Logger.getLogger(UniteMapperImplTest.class.getName());

    protected MapperManager mapperManager;
    protected String filtreRef;
    protected Identifiant identifiantCS;
    protected Unite uniteCS;
    protected Unite nouvelleUniteRef;

    public UniteMapperImplTest(MapperManager mapperManager) throws PersistenceException {
        this.mapperManager = mapperManager;

        this.mapperManager.getDatabaseSetup().dropTables();
        this.mapperManager.getDatabaseSetup().createTables();
        this.mapperManager.getDatabaseSetup().insertData();

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
                .code("nouvelle unité " + Instant.now().toString()).build();
    }

    @Test
    public void testRetrieve_String() throws Exception {
        Pattern pattern = Pattern.compile(filtreRef);
        List<Unite> entites1 = mapperManager.getUniteMapper()
                .retrieve(filtreRef);

        Assertions.assertEquals(3, entites1.size());
        for (Unite i : entites1) {
            LOG.info(i.toString());
            //Assertions.assertTrue(pattern.matcher(i.getCode()).find());
            //Ne peut pas vérifier! 
        }
    }

    @Test
    public void testRetrieve_StringNull() throws Exception {
        String filtre = null;
        List<Unite> entites1 = mapperManager.getUniteMapper()
                .retrieve(filtre);

        Assertions.assertEquals(0, entites1.size());
    }

    @Test
    public void testRetrieve_String_Detacher() throws Exception {
        List<Unite> entites1 = mapperManager.getUniteMapper()
                .retrieve(filtreRef);
        List<Unite> entites2 = mapperManager.getUniteMapper()
                .retrieve(filtreRef);

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
        Unite entite = mapperManager.getUniteMapper()
                .retrieve(identifiantCS);

        Assertions.assertNotNull(entite);
        Assertions.assertEquals(uniteCS, entite);
        Assertions.assertTrue(entite.getVersion() > 0);
        Assertions.assertNotNull(entite.getAudit());

        Assertions.assertEquals(uniteCS.getCode(), entite.getCode());
    }

    @Test
    public void testRetrieve_IdentifiantNull() throws Exception {
        Identifiant identifiant = null;
        Unite entite = mapperManager.getUniteMapper()
                .retrieve(identifiant);

        Assertions.assertNull(entite);
    }

    @Test
    public void testRetrieve_Identifiant_Detacher() throws Exception {
        Unite entite1 = mapperManager.getUniteMapper()
                .retrieve(identifiantCS);
        Unite entite2 = mapperManager.getUniteMapper()
                .retrieve(identifiantCS);

        Assertions.assertEquals(entite1, entite2);
        Assertions.assertNotSame(entite1, entite2);
        entite1.setCode(entite1.getCode() + " update entite1");
        Assertions.assertNotEquals(entite1.getCode(), entite2.getCode());
    }

    @Test
    public void testCreate() throws Exception {
        Unite nouvelleEntite = mapperManager.getUniteMapper()
                .create(nouvelleUniteRef);

        Assertions.assertNotNull(nouvelleEntite.getIdentifiant());
        Assertions.assertEquals(Long.valueOf(1),
                nouvelleEntite.getVersion());

        Assertions.assertNotNull(nouvelleEntite.getAudit());
        Assertions.assertNull(nouvelleEntite.getAudit().getUserModification());
        Assertions.assertTrue(Instant.now()
                .isAfter(nouvelleEntite.getAudit()
                        .getDateCreation()));

        Assertions.assertEquals(nouvelleUniteRef.getCode(), nouvelleEntite.getCode());

        Unite entite = mapperManager.getUniteMapper()
                .retrieve(nouvelleEntite.getIdentifiant());

        Assertions.assertNotNull(entite);
        Assertions.assertEquals(Long.valueOf(1),
                nouvelleEntite.getVersion());
        Assertions.assertNotNull(entite.getAudit());

        Assertions.assertEquals(nouvelleEntite, entite);
        Assertions.assertNotSame(nouvelleEntite, entite);
        Assertions.assertEquals(nouvelleEntite.getCode(), entite.getCode());
    }

    @Test
    public void testCreateCodeNotUnique() throws Exception {
        Assertions.assertThrows(ContrainteUniquePersistenceException.class,
                new Executable() {
            @Override
            public void execute() throws Throwable {
                mapperManager.getUniteMapper().create(nouvelleUniteRef);
                mapperManager.getUniteMapper().create(nouvelleUniteRef);
            }
        });
    }

    @Test
    public void testCreateCodeNull() throws Exception {
        Assertions.assertThrows(ContrainteNotNullPersistenceException.class,
                new Executable() {
            @Override
            public void execute() throws Throwable {
                nouvelleUniteRef.setCode(null);
                mapperManager.getUniteMapper().create(nouvelleUniteRef);
            }
        });
    }

    @Test
    public void testDelete() throws Exception {
        Unite nouvelleEntite = mapperManager.getUniteMapper()
                .create(nouvelleUniteRef);
        Unite entite = mapperManager.getUniteMapper()
                .retrieve(nouvelleEntite.getIdentifiant());

        Assertions.assertNotNull(entite);

        mapperManager.getUniteMapper()
                .delete(entite);
        Unite entiteNull = mapperManager.getUniteMapper()
                .retrieve(entite.getIdentifiant());

        Assertions.assertNull(entiteNull);
    }

    @Test
    public void testDeleteEntiteTropAncienne() throws Exception {
        Assertions.assertThrows(
                EntiteTropAnciennePersistenceException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Unite nouvelleEntite = mapperManager.getUniteMapper()
                        .create(nouvelleUniteRef);
                Unite entite = mapperManager.getUniteMapper()
                        .retrieve(nouvelleEntite.getIdentifiant());

                Assertions.assertNotNull(entite);

                mapperManager.getUniteMapper()
                        .update(entite);

                mapperManager.getUniteMapper()
                        .delete(entite);
            }
        });

    }

    @Test
    public void testDeleteEntiteUtilisee() throws Exception {
        Assertions.assertThrows(EntiteUtiliseePersistenceException.class,
                new Executable() {
            @Override
            public void execute() throws Throwable {
                Unite entite = mapperManager.getUniteMapper()
                        .retrieve(identifiantCS);

                Assertions.assertNotNull(entite);

                mapperManager.getUniteMapper()
                        .delete(entite);
            }
        });
    }

    @Test
    public void testDeleteEntiteInconnu() throws Exception {
        Assertions.assertThrows(EntiteInconnuePersistenceException.class,
                new Executable() {
            @Override
            public void execute() throws Throwable {
                Unite entite = UniteBase.builder()
                        .unite(nouvelleUniteRef)
                        .identifiant(IdentifiantBase.builder().build())
                        .build();
                mapperManager.getUniteMapper()
                        .delete(entite);
            }
        });
    }

    @Test
    public void testUpdate() throws Exception {
        Unite nouvelleEntite = mapperManager.getUniteMapper()
                .create(nouvelleUniteRef);
        Unite entite = mapperManager.getUniteMapper()
                .retrieve(nouvelleEntite.getIdentifiant());

        Assertions.assertNotNull(entite.getIdentifiant());
        Assertions.assertEquals(Long.valueOf(1),
                entite.getVersion());
        Assertions.assertEquals(nouvelleUniteRef.getCode(), entite.getCode());

        Unite entiteMod = UniteBase.builder()
                .unite(entite)
                .build();
        entiteMod.setCode(entite.getCode() + " update");

        mapperManager.getUniteMapper()
                .update(entiteMod);
        Unite entiteModifie = mapperManager.getUniteMapper()
                .retrieve(entiteMod.getIdentifiant());

        Assertions.assertEquals(entiteMod, entiteModifie);
        Assertions.assertEquals(Long.valueOf(2),
                entiteModifie.getVersion());

        Assertions.assertNotNull(entiteModifie.getAudit());
        Assertions.assertTrue(Instant.now()
                .isAfter(entiteModifie.getAudit()
                        .getDateCreation()));
        Assertions.assertTrue(Instant.now()
                .isAfter(entiteModifie.getAudit()
                        .getDateModification()));

        Assertions.assertEquals(entiteMod.getCode(), entiteModifie.getCode());
    }

    @Test
    public void testUpdateEntiteTropAncienne() throws Exception {
        Assertions.assertThrows(
                EntiteTropAnciennePersistenceException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Unite nouvelleEntite = mapperManager.getUniteMapper()
                        .create(nouvelleUniteRef);
                Unite entite = mapperManager.getUniteMapper()
                        .retrieve(nouvelleEntite.getIdentifiant());

                Assertions.assertNotNull(entite.getIdentifiant());
                Assertions.assertEquals(Long.valueOf(1),
                        entite.getVersion());
                Assertions.assertEquals(nouvelleUniteRef.getCode(), entite.getCode());

                Unite entiteMod = UniteBase.builder()
                        .unite(entite)
                        .build();
                entiteMod.setCode(entite.getCode() + " update");

                mapperManager.getUniteMapper()
                        .update(entiteMod);

                mapperManager.getUniteMapper()
                        .update(entiteMod);

            }
        });

    }

    @Test
    public void testUpdateEntiteInconnu() throws Exception {
        Assertions.assertThrows(EntiteInconnuePersistenceException.class,
                new Executable() {
            @Override
            public void execute() throws Throwable {
                Unite entiteMod = UniteBase.builder()
                        .unite(nouvelleUniteRef)
                        .identifiant(IdentifiantBase.builder().build())
                        .build();
                mapperManager.getUniteMapper().update(entiteMod);
            }
        });
    }

    @Test
    public void testUpdateCodeNull() throws Exception {
        Assertions.assertThrows(ContrainteNotNullPersistenceException.class,
                new Executable() {
            @Override
            public void execute() throws Throwable {
                Unite entite = mapperManager.getUniteMapper()
                        .retrieve(identifiantCS);
                entite.setCode(null);
                mapperManager.getUniteMapper()
                        .update(entite);
            }
        });
    }

}
