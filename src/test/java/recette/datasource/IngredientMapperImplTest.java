package recette.datasource;

import core.datasource.ContrainteNotNullPersistenceException;
import core.datasource.ContrainteUniquePersistenceException;
import core.datasource.EntiteInconnuePersistenceException;
import core.datasource.EntiteUtiliseePersistenceException;
import core.datasource.PersistenceException;
import core.domain.Identifiant;
import core.domain.IdentifiantBase;
import java.time.Instant;
import java.util.List;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import recette.domain.DemoData;
import recette.domain.Ingredient;
import recette.domain.IngredientBase;
import recette.domain.Recette;
import recette.domain.RecetteBase;

/**
 *
 * @author dominique huguenin (dominique.huguenin@rpn.ch)
 */
public abstract class IngredientMapperImplTest {

    private static final Logger LOG = Logger.getLogger(IngredientMapperImplTest.class.getName());
    
    protected MapperManager mapperManager;
    protected String filtreRef;
    protected Identifiant identifiantAubergine;
    protected Ingredient ingredientAubergine;
    protected Ingredient nouvelleIngredientRef2;
    protected Ingredient nouvelleIngredientRef1;

    public IngredientMapperImplTest(MapperManager mapperManager) throws PersistenceException {
        this.mapperManager = mapperManager;

        this.mapperManager.getDatabaseSetup().dropTables();
        this.mapperManager.getDatabaseSetup().createTables();
        this.mapperManager.getDatabaseSetup().insertData();

    }

    @BeforeEach
    public void setUp() {
        identifiantAubergine = IdentifiantBase.builder()
                .uuid(DemoData.INGREDIENTS.AUBERGINE.UUID)
                .build();
        ingredientAubergine = IngredientBase.builder()
                .identifiant(identifiantAubergine)
                .nom(DemoData.INGREDIENTS.AUBERGINE.NOM)
                .detail(DemoData.INGREDIENTS.AUBERGINE.DETAIL)
                .build();

        nouvelleIngredientRef2 = IngredientBase.builder()
                .nom("nouvelle ingredient " + Instant.now().toString())
                .detail("description du nouvelle ingrédient")
                .build();

        nouvelleIngredientRef1 = IngredientBase.builder()
                .nom("nouvelle ingredient " + Instant.now().toString())
                .detail("description du nouvelle ingrédient")
                .recette(RecetteBase.builder()
                        .identifiant(IdentifiantBase.builder()
                                .uuid(DemoData.RECETTES.POIVRONS_AU_FOUR.UUID)
                                .build())
                        .build())
                .build();

    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testRetrieve_String() throws Exception {
        List<Ingredient> entites
                = mapperManager.getIngredientMapper()
                        .retrieve(filtreRef);

        Assertions.assertEquals(4, entites.size());
        for (Ingredient i : entites) {
            LOG.info(i.toString());
            //Assertions.assertTrue(i.getNom().matches(filtreRef));
            //Ne peut pas vérifier! 
        }

    }

    @Test
    public void testRetrieve_String_RecetteRef() throws Exception {
        List<Ingredient> entites
                = mapperManager.getIngredientMapper()
                        .retrieve(filtreRef);

        Assertions.assertEquals(4, entites.size());
        for (Ingredient i : entites) {
            LOG.info(i.toString());
            //Assertions.assertTrue(i.getNom().matches(filtreRef));
            //Ne peut pas vérifier! 
            if (i.getRecette() != null) {
                Assertions.assertTrue(i.getRecette() instanceof RecetteRef);
            }
        }
    }

    @Test
    public void testRetrieve_String_Detacher() throws Exception {
        List<Ingredient> entites1
                = mapperManager.getIngredientMapper()
                        .retrieve(filtreRef);
        List<Ingredient> entites2
                = mapperManager.getIngredientMapper()
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
    public void testRetrieve_StringNull() throws Exception {
        String filtre = null;
        List<Ingredient> entites1
                = mapperManager.getIngredientMapper()
                        .retrieve(filtre);

        Assertions.assertEquals(0, entites1.size());
    }

    @Test
    public void testRetrieve_Identifiant() throws Exception {
        Ingredient entite
                = mapperManager.getIngredientMapper()
                        .retrieve(identifiantAubergine);

        Assertions.assertNotNull(entite);

        Assertions.assertEquals(ingredientAubergine, entite);
        Assertions.assertEquals(ingredientAubergine.getNom(),
                entite.getNom());
        Assertions.assertEquals(ingredientAubergine.getDetail(),
                entite.getDetail());
    }

    @Test
    public void testRetrieve_SauceTomate() throws Exception {
        Ingredient entite
                = mapperManager.getIngredientMapper()
                        .retrieve(IdentifiantBase.builder()
                                .uuid(DemoData.INGREDIENTS.SAUCE_TOMATES.UUID)
                                .build());

        Assertions.assertNotNull(entite);

        Assertions.assertEquals(DemoData.INGREDIENTS.SAUCE_TOMATES.UUID,
                entite.getIdentifiant().getUUID());
        Assertions.assertEquals(DemoData.INGREDIENTS.SAUCE_TOMATES.NOM,
                entite.getNom());
        Assertions.assertEquals(DemoData.INGREDIENTS.SAUCE_TOMATES.DETAIL,
                entite.getDetail());
        Assertions.assertEquals(DemoData.RECETTES.SAUCE_TOMATES.UUID,
                entite.getRecette().getIdentifiant().getUUID());

        Assertions.assertTrue(entite.getRecette() instanceof RecetteRef);

        Recette recette = mapperManager.getRecetteMapper()
                .retrieve(IdentifiantBase.builder()
                        .uuid(DemoData.RECETTES.SAUCE_TOMATES.UUID)
                        .build());
        Assertions.assertNotSame(recette, entite.getRecette());
    }

    @Test
    public void testRetrieve_IdentifiantNull() throws Exception {
        Identifiant identifiant = null;
        Ingredient entite
                = mapperManager.getIngredientMapper()
                        .retrieve(identifiant);
        Assertions.assertNull(entite);
    }

    @Test
    public void testRetrieve_Identifiant_Detacher() throws Exception {
        Ingredient entite1
                = mapperManager.getIngredientMapper()
                        .retrieve(identifiantAubergine);
        Ingredient entite2
                = mapperManager.getIngredientMapper()
                        .retrieve(identifiantAubergine);

        Assertions.assertEquals(entite1, entite2);
        Assertions.assertNotSame(entite1, entite2);
        entite1.setNom(entite1.getNom() + " update entite1");
        Assertions.assertNotEquals(entite1.getNom(), entite2.getNom());
    }

    @Test
    public void testCreate() throws Exception {
        Ingredient nouvelleEntite
                = mapperManager.getIngredientMapper()
                        .create(nouvelleIngredientRef1);

        Assertions.assertNotNull(nouvelleEntite.getIdentifiant());
        Assertions.assertEquals(nouvelleIngredientRef1.getNom(),
                nouvelleEntite.getNom());
        Assertions.assertEquals(nouvelleIngredientRef1.getDetail(),
                nouvelleEntite.getDetail());

        Ingredient entite
                = mapperManager.getIngredientMapper()
                        .retrieve(nouvelleEntite.getIdentifiant());

        Assertions.assertNotNull(entite);
        Assertions.assertEquals(nouvelleEntite, entite);
        Assertions.assertNotSame(nouvelleEntite, entite);
        Assertions.assertEquals(nouvelleEntite.getNom(), entite.getNom());
        Assertions.assertEquals(nouvelleEntite.getDetail(), entite.getDetail());
    }

    @Test
    public void testCreateRecetteInconnue() throws Exception {
        Assertions.assertThrows(
                EntiteInconnuePersistenceException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                nouvelleIngredientRef1.setRecette(
                        RecetteBase.builder()
                                .identifiant(IdentifiantBase
                                        .builder()
                                        .build())
                                .build());
                mapperManager.getIngredientMapper()
                        .create(nouvelleIngredientRef1);
            }
        });

    }

    @Test
    public void testCreateNomNotUnique() throws Exception {
        Assertions.assertThrows(
                ContrainteUniquePersistenceException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                mapperManager.getIngredientMapper()
                        .create(ingredientAubergine);
            }
        });
    }

    @Test
    public void testCreateNomNull() throws Exception {
        Assertions.assertThrows(
                ContrainteNotNullPersistenceException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                nouvelleIngredientRef1.setNom(null);

                mapperManager.getIngredientMapper()
                        .create(nouvelleIngredientRef1);
            }
        });

    }

    @Test
    public void testDelete() throws Exception {
        Ingredient nouvelleEntite
                = mapperManager.getIngredientMapper()
                        .create(nouvelleIngredientRef2);
        final Ingredient entite
                = mapperManager.getIngredientMapper()
                        .retrieve(nouvelleEntite.getIdentifiant());

        Assertions.assertNotNull(entite);

        mapperManager.getIngredientMapper()
                .delete(entite);

        Ingredient entiteNull
                = mapperManager.getIngredientMapper()
                        .retrieve(entite.getIdentifiant());

        Assertions.assertNull(entiteNull);
    }

    @Test
    public void testDeleteEntiteUtilisee() throws Exception {
        Assertions.assertThrows(
                EntiteUtiliseePersistenceException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Ingredient entite
                        = mapperManager.getIngredientMapper()
                                .retrieve(identifiantAubergine);
                Assertions.assertNotNull(entite);

                mapperManager.getIngredientMapper()
                        .delete(entite);
            }
        });

    }

    @Test
    public void testDeleteEntiteInconnu() throws Exception {
        Assertions.assertThrows(
                EntiteInconnuePersistenceException.class, new Executable() {
            @Override
            public void execute() throws Throwable {

                Ingredient entite
                        = IngredientBase.builder()
                                .ingredient(nouvelleIngredientRef2)
                                .identifiant(IdentifiantBase.builder()
                                        .build())
                                .build();

                mapperManager.getIngredientMapper()
                        .delete(entite);
            }
        });
    }

    @Test
    public void testUpdate() throws Exception {

        Ingredient nouvelleEntite
                = mapperManager.getIngredientMapper()
                        .create(nouvelleIngredientRef2);
        Ingredient entite
                = mapperManager.getIngredientMapper()
                        .retrieve(nouvelleEntite.getIdentifiant());

        Assertions.assertNotNull(entite.getIdentifiant());
        Assertions.assertEquals(nouvelleIngredientRef2.getNom(),
                entite.getNom());
        Assertions.assertEquals(nouvelleIngredientRef2.getDetail(),
                entite.getDetail());

        Ingredient entiteMod
                = IngredientBase.builder()
                        .ingredient(entite)
                        .build();
        entiteMod.setNom(entiteMod.getNom() + " update" + Instant.now().toString());
        entiteMod.setDetail(entiteMod.getDetail() + " update" + Instant.now().toString());
        entiteMod.setRecette(
                RecetteBase.builder()
                        .identifiant(IdentifiantBase.builder()
                                .uuid(DemoData.RECETTES.POIRES_AUX_AMANDES.UUID)
                                .build())
                        .build());

        mapperManager.getIngredientMapper()
                .update(entiteMod);
        Ingredient entiteModifie
                = mapperManager.getIngredientMapper()
                        .retrieve(entiteMod
                                .getIdentifiant());
        Assertions.assertEquals(entiteMod, entiteModifie);

        Assertions.assertEquals(entiteMod.getNom(),
                entiteModifie.getNom());
        Assertions.assertEquals(entiteMod.getDetail(),
                entiteModifie.getDetail());

    }

    @Test
    public void testUpdateEntiteInconnu() throws Exception {
        Assertions.assertThrows(
                EntiteInconnuePersistenceException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Ingredient entiteMod
                        = IngredientBase.builder()
                                .ingredient(nouvelleIngredientRef2)
                                .identifiant(IdentifiantBase.builder()
                                        .build())
                                .build();

                mapperManager.getIngredientMapper()
                        .update(entiteMod);
            }
        });

    }

    @Test
    public void testUpdateRecetteInconnu() throws Exception {
        Assertions.assertThrows(
                EntiteInconnuePersistenceException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Ingredient nouvelleEntite
                        = mapperManager.getIngredientMapper()
                                .create(nouvelleIngredientRef2);
                Ingredient entite
                        = mapperManager.getIngredientMapper()
                                .retrieve(nouvelleEntite.getIdentifiant());

                Ingredient entiteMod
                        = IngredientBase.builder()
                                .ingredient(entite)
                                .recette(
                                        RecetteBase.builder()
                                                .identifiant(IdentifiantBase
                                                        .builder()
                                                        .build())
                                                .build())
                                .build();

                mapperManager.getIngredientMapper()
                        .update(entiteMod);
            }
        });

    }

    @Test
    public void testUpdateNomNull() throws Exception {
        Assertions.assertThrows(
                ContrainteNotNullPersistenceException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Ingredient entite
                        = mapperManager.getIngredientMapper()
                                .retrieve(identifiantAubergine);
                entite.setNom(null);

                mapperManager.getIngredientMapper()
                        .update(entite);
            }
        });
    }

}
