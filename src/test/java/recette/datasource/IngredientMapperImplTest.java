package recette.datasource;

import core.datasource.ContrainteNotNullPersistenceException;
import core.datasource.ContrainteUniquePersistenceException;
import core.datasource.EntiteInconnuePersistenceException;
import core.datasource.EntiteTropAnciennePersistenceException;
import core.datasource.EntiteUtiliseePersistenceException;
import core.datasource.PersistenceException;
import core.datasource.TransactionManager;
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

    protected String filtreRef;
    protected Identifiant identifiantAubergine;
    protected Ingredient ingredientAubergine;
    protected Ingredient nouvelleIngredientRef2;
    protected Ingredient nouvelleIngredientRef1;
    private final TransactionManager transactionManager;

    public IngredientMapperImplTest(TransactionManager tm) throws PersistenceException {
        this.transactionManager = tm;

        transactionManager.executeTransaction(
                (TransactionManager.Operation<MapperManager>) (MapperManager mm) -> {
                    mm.getDatabaseSetup().dropTables();
                    mm.getDatabaseSetup().createTables();
                    mm.getDatabaseSetup().insertData();
                    return null;
                });
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
        List<Ingredient> entites = (List<Ingredient>) transactionManager.executeTransaction(
                (TransactionManager.Operation<MapperManager>) (MapperManager mm) -> {
                    return mm.getIngredientMapper()
                            .retrieve(filtreRef);
                });

        Assertions.assertEquals(4, entites.size());
        for (Ingredient i : entites) {
            LOG.info(i.toString());
            //Assertions.assertTrue(i.getNom().matches(filtreRef));
            //Ne peut pas vérifier! 
        }

    }

    @Test
    public void testRetrieve_String_RecetteRef() throws Exception {
        List<Ingredient> entites = (List<Ingredient>) transactionManager.executeTransaction(
                (TransactionManager.Operation<MapperManager>) (MapperManager mm) -> {
                    return mm.getIngredientMapper()
                            .retrieve(filtreRef);
                });

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
        List<Ingredient> entites1 = (List<Ingredient>) transactionManager.executeTransaction(
                (TransactionManager.Operation<MapperManager>) (MapperManager mm) -> {
                    return mm.getIngredientMapper()
                            .retrieve(filtreRef);
                });

        List<Ingredient> entites2 = (List<Ingredient>) transactionManager.executeTransaction(
                (TransactionManager.Operation<MapperManager>) (MapperManager mm) -> {
                    return mm.getIngredientMapper()
                            .retrieve(filtreRef);
                });

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

        List<Ingredient> entites = (List<Ingredient>) transactionManager.executeTransaction(
                (TransactionManager.Operation<MapperManager>) (MapperManager mm) -> {
                    return mm.getIngredientMapper()
                            .retrieve(filtre);
                });

        Assertions.assertEquals(0, entites.size());
    }

    @Test
    public void testRetrieve_Identifiant() throws Exception {
        Ingredient entite = (Ingredient) transactionManager.executeTransaction(
                (TransactionManager.Operation<MapperManager>) (MapperManager mm) -> {
                    return mm.getIngredientMapper()
                            .retrieve(identifiantAubergine);
                });

        Assertions.assertNotNull(entite);

        Assertions.assertEquals(ingredientAubergine, entite);
        Assertions.assertTrue(entite.getVersion() > 0);
        Assertions.assertNotNull(entite.getAudit());

        Assertions.assertEquals(ingredientAubergine.getNom(),
                entite.getNom());
        Assertions.assertEquals(ingredientAubergine.getDetail(),
                entite.getDetail());
    }

    @Test
    public void testRetrieve_SauceTomate() throws Exception {
        Ingredient entite = (Ingredient) transactionManager.executeTransaction(
                (TransactionManager.Operation<MapperManager>) (MapperManager mm) -> {
                    return mm.getIngredientMapper()
                            .retrieve(IdentifiantBase.builder()
                                    .uuid(DemoData.INGREDIENTS.SAUCE_TOMATES.UUID)
                                    .build());
                });

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

        Recette recette = (Recette) transactionManager.executeTransaction(
                (TransactionManager.Operation<MapperManager>) (MapperManager mm) -> {
                    return mm.getRecetteMapper()
                            .retrieve(IdentifiantBase.builder()
                                    .uuid(DemoData.RECETTES.SAUCE_TOMATES.UUID)
                                    .build());
                });

        Assertions.assertNotSame(recette, entite.getRecette());
    }

    @Test
    public void testRetrieve_IdentifiantNull() throws Exception {
        Identifiant identifiant = null;

        Ingredient entite = (Ingredient) transactionManager.executeTransaction(
                (TransactionManager.Operation<MapperManager>) (MapperManager mm) -> {
                    return mm.getIngredientMapper()
                            .retrieve(identifiant);
                });

        Assertions.assertNull(entite);
    }

    @Test
    public void testRetrieve_Identifiant_Detacher() throws Exception {
        Ingredient entite1
                = (Ingredient) transactionManager.executeTransaction(
                        (TransactionManager.Operation<MapperManager>) (MapperManager mm) -> {
                            return mm.getIngredientMapper()
                                    .retrieve(identifiantAubergine);
                        });
        Ingredient entite2
                = (Ingredient) transactionManager.executeTransaction(
                        (TransactionManager.Operation<MapperManager>) (MapperManager mm) -> {
                            return mm.getIngredientMapper()
                                    .retrieve(identifiantAubergine);
                        });

        Assertions.assertEquals(entite1, entite2);
        Assertions.assertNotSame(entite1, entite2);
        entite1.setNom(entite1.getNom() + " update entite1");
        Assertions.assertNotEquals(entite1.getNom(), entite2.getNom());
    }

    @Test
    public void testCreate() throws Exception {
        Ingredient nouvelleEntite
                = (Ingredient) transactionManager.executeTransaction(
                        (TransactionManager.Operation<MapperManager>) (MapperManager mm) -> {
                            return mm.getIngredientMapper()
                                    .create(nouvelleIngredientRef1);
                        });

        Assertions.assertNotNull(nouvelleEntite.getIdentifiant());
        Assertions.assertEquals(Long.valueOf(1),
                nouvelleEntite.getVersion());

        Assertions.assertNotNull(nouvelleEntite.getAudit());
        Assertions.assertNull(nouvelleEntite.getAudit().getUserModification());
        Assertions.assertTrue(Instant.now()
                .isAfter(nouvelleEntite.getAudit()
                        .getDateCreation()));

        Assertions.assertEquals(nouvelleIngredientRef1.getNom(),
                nouvelleEntite.getNom());
        Assertions.assertEquals(nouvelleIngredientRef1.getDetail(),
                nouvelleEntite.getDetail());

        Ingredient entite
                = (Ingredient) transactionManager.executeTransaction(
                        (TransactionManager.Operation<MapperManager>) (MapperManager mm) -> {
                            return mm.getIngredientMapper()
                                    .retrieve(nouvelleEntite.getIdentifiant());
                        });

        Assertions.assertNotNull(entite);
        Assertions.assertEquals(Long.valueOf(1),
                nouvelleEntite.getVersion());
        Assertions.assertNotNull(entite.getAudit());

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
                transactionManager.executeTransaction(
                        (TransactionManager.Operation<MapperManager>) (MapperManager mm) -> {
                            return mm.getIngredientMapper()
                                    .create(nouvelleIngredientRef1);
                        });
            }
        });

    }

    @Test
    public void testCreateNomNotUnique() throws Exception {
        Assertions.assertThrows(
                ContrainteUniquePersistenceException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                transactionManager.executeTransaction(
                        (TransactionManager.Operation<MapperManager>) (MapperManager mm) -> {
                            return mm.getIngredientMapper()
                                    .create(ingredientAubergine);
                        });
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

                transactionManager.executeTransaction(
                        (TransactionManager.Operation<MapperManager>) (MapperManager mm) -> {
                            return mm.getIngredientMapper()
                                    .create(nouvelleIngredientRef1);
                        });
            }
        });

    }

    @Test
    public void testDelete() throws Exception {
        final Ingredient entite
                = (Ingredient) transactionManager.executeTransaction(
                        (TransactionManager.Operation<MapperManager>) (MapperManager mm) -> {
                            Ingredient nouvelleEntite = mm.getIngredientMapper()
                                    .create(nouvelleIngredientRef2);
                            return mm.getIngredientMapper()
                                    .retrieve(nouvelleEntite.getIdentifiant());
                        });

        Assertions.assertNotNull(entite);

        transactionManager.executeTransaction(
                (TransactionManager.Operation<MapperManager>) (MapperManager mm) -> {
                    mm.getIngredientMapper().delete(entite);
                    return null;
                });

        Ingredient entiteNull
                = (Ingredient) transactionManager.executeTransaction(
                        (TransactionManager.Operation<MapperManager>) (MapperManager mm) -> {
                            return mm.getIngredientMapper()
                                    .retrieve(entite.getIdentifiant());
                        });

        Assertions.assertNull(entiteNull);
    }

    @Test
    public void testDeleteEntiteTropAncienne() throws Exception {
        Assertions.assertThrows(
                EntiteTropAnciennePersistenceException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final Ingredient entite
                        = (Ingredient) transactionManager.executeTransaction(
                                (TransactionManager.Operation<MapperManager>) (MapperManager mm) -> {
                                    Ingredient nouvelleEntite = mm.getIngredientMapper()
                                            .create(nouvelleIngredientRef2);
                                    return mm.getIngredientMapper()
                                            .retrieve(nouvelleEntite.getIdentifiant());
                                });

                Assertions.assertNotNull(entite);

                transactionManager.executeTransaction(
                        (TransactionManager.Operation<MapperManager>) (MapperManager mm) -> {
                            mm.getIngredientMapper().update(entite);
                            return null;
                        });

                transactionManager.executeTransaction(
                        (TransactionManager.Operation<MapperManager>) (MapperManager mm) -> {
                            mm.getIngredientMapper().delete(entite);
                            return null;
                        });
            }
        });

    }

    @Test
    public void testDeleteEntiteUtilisee() throws Exception {
        Assertions.assertThrows(
                EntiteUtiliseePersistenceException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                transactionManager.executeTransaction(
                        (TransactionManager.Operation<MapperManager>) (MapperManager mm) -> {

                            Ingredient entite
                            = mm.getIngredientMapper()
                                    .retrieve(identifiantAubergine);
                            Assertions.assertNotNull(entite);

                            mm.getIngredientMapper()
                                    .delete(entite);
                            return null;
                        });
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

                transactionManager.executeTransaction(
                        (TransactionManager.Operation<MapperManager>) (MapperManager mm) -> {
                            mm.getIngredientMapper()
                                    .delete(entite);
                            return null;
                        });

            }
        });
    }

    @Test
    public void testUpdate() throws Exception {

        final Ingredient entite
                = (Ingredient) transactionManager.executeTransaction(
                        (TransactionManager.Operation<MapperManager>) (MapperManager mm) -> {
                            Ingredient nouvelleEntite
                            = mm.getIngredientMapper()
                                    .create(nouvelleIngredientRef2);
                            return mm.getIngredientMapper()
                                    .retrieve(nouvelleEntite.getIdentifiant());
                        });

        Assertions.assertNotNull(entite.getIdentifiant());
        Assertions.assertEquals(Long.valueOf(1),
                entite.getVersion());
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

        final Ingredient entiteModifie
                = (Ingredient) transactionManager.executeTransaction(
                        (TransactionManager.Operation<MapperManager>) (MapperManager mm) -> {
                            mm.getIngredientMapper()
                                    .update(entiteMod);
                            return mm.getIngredientMapper()
                                    .retrieve(entiteMod
                                            .getIdentifiant());
                        });

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

        Assertions.assertEquals(entiteMod.getNom(),
                entiteModifie.getNom());
        Assertions.assertEquals(entiteMod.getDetail(),
                entiteModifie.getDetail());

    }

    @Test
    public void testUpdateEntiteTropAncienne() throws Exception {
        Assertions.assertThrows(
                EntiteTropAnciennePersistenceException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final Ingredient entite
                        = (Ingredient) transactionManager.executeTransaction(
                                (TransactionManager.Operation<MapperManager>) (MapperManager mm) -> {
                                    Ingredient nouvelleEntite
                                    = mm.getIngredientMapper()
                                            .create(nouvelleIngredientRef2);
                                    return mm.getIngredientMapper()
                                            .retrieve(nouvelleEntite.getIdentifiant());
                                });

                Assertions.assertNotNull(entite.getIdentifiant());
                Assertions.assertEquals(Long.valueOf(1),
                        entite.getVersion());
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

                transactionManager.executeTransaction(
                        (TransactionManager.Operation<MapperManager>) (MapperManager mm) -> {
                            mm.getIngredientMapper()
                                    .update(entiteMod);
                            return null;
                        });

                transactionManager.executeTransaction(
                        (TransactionManager.Operation<MapperManager>) (MapperManager mm) -> {
                            mm.getIngredientMapper()
                                    .update(entiteMod);
                            return null;
                        });

            }
        });

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

                transactionManager.executeTransaction(
                        (TransactionManager.Operation<MapperManager>) (MapperManager mm) -> {
                            mm.getIngredientMapper()
                                    .update(entiteMod);
                            return null;
                        });
            }
        });

    }

    @Test
    public void testUpdateRecetteInconnu() throws Exception {
        Assertions.assertThrows(
                EntiteInconnuePersistenceException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final Ingredient entite
                        = (Ingredient) transactionManager.executeTransaction(
                                (TransactionManager.Operation<MapperManager>) (MapperManager mm) -> {
                                    Ingredient nouvelleEntite
                                    = mm.getIngredientMapper()
                                            .create(nouvelleIngredientRef2);
                                    return mm.getIngredientMapper()
                                            .retrieve(nouvelleEntite.getIdentifiant());
                                });

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

                transactionManager.executeTransaction(
                        (TransactionManager.Operation<MapperManager>) (MapperManager mm) -> {
                            mm.getIngredientMapper()
                                    .update(entiteMod);
                            return null;
                        });
            }
        });

    }

    @Test
    public void testUpdateNomNull() throws Exception {
        Assertions.assertThrows(
                ContrainteNotNullPersistenceException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                transactionManager.executeTransaction(
                        (TransactionManager.Operation<MapperManager>) (MapperManager mm) -> {
                            Ingredient entite
                            = mm.getIngredientMapper()
                                    .retrieve(identifiantAubergine);
                            entite.setNom(null);

                            mm.getIngredientMapper()
                                    .update(entite);
                            return null;
                        });

            }
        });
    }

}
