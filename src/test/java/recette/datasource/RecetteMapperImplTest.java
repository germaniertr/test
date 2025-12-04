package recette.datasource;

import core.datasource.ContrainteNotNullPersistenceException;
import core.datasource.ContrainteUniquePersistenceException;
import core.datasource.EntiteInconnuePersistenceException;
import core.datasource.PersistenceException;
import core.domain.Identifiant;
import core.domain.IdentifiantBase;
import java.time.Instant;
import java.util.List;
import java.util.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import recette.domain.ComposantBase;
import recette.domain.DemoData;
import recette.domain.IngredientBase;
import recette.domain.Recette;
import recette.domain.RecetteBase;
import recette.domain.UniteBase;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public abstract class RecetteMapperImplTest {

    private static final Logger LOG = Logger.getLogger(UniteMapperImplTest.class.getName());

    protected MapperManager mapperManager;
    protected String filtreRef1;
    protected String filtreRef2;
    protected Identifiant identifiantAubergineRef;
    protected DemoData demoData;
    protected Recette recetteAubergineRef;
    protected Recette nouvelleEntiteRef;

    public RecetteMapperImplTest(MapperManager mapperManager) throws PersistenceException {
        this.mapperManager = mapperManager;

        this.mapperManager.getDatabaseSetup().dropTables();
        this.mapperManager.getDatabaseSetup().createTables();
        this.mapperManager.getDatabaseSetup().insertData();

        this.demoData = new DemoData();
        this.demoData.initialisation();

    }

    @BeforeEach
    public void setUp() {
        identifiantAubergineRef = IdentifiantBase.builder()
                .uuid(DemoData.RECETTES.AUBERGINES_AU_FOUR.UUID)
                .build();
        recetteAubergineRef = demoData.getRecettes()
                .get(identifiantAubergineRef);
        nouvelleEntiteRef
                = //aubergine
                //Feta
                //g
                //poivre
                RecetteBase.builder()
                        .nom("nouvelle recette " + Instant.now().toString())
                        .detail("description du nouvelle recette")
                        .preparation("préparation de la nouvelle recette")
                        .nombrePersonnes(100)
                        .composant( //aubergine
                                ComposantBase.builder()
                                        .ingredient( //aubergine
                                                IngredientBase.builder()
                                                        .identifiant( //aubergine
                                                                IdentifiantBase.builder()
                                                                        .uuid("DEMO0000-0000-0000-0001-000000000001") //aubergine
                                                                        .build())
                                                        .build())
                                        .quantite(10.0)
                                        .build())
                        .composant( //Feta
                                //g
                                ComposantBase.builder().ingredient( //Feta
                                        IngredientBase.builder()
                                                .identifiant(IdentifiantBase.builder()
                                                        .uuid("DEMO0000-0000-0000-0001-000000000007") //Feta
                                                        .build())
                                                .build())
                                        .quantite(250.0)
                                        .unite( //g
                                                UniteBase.builder()
                                                        .identifiant(IdentifiantBase.builder()
                                                                .uuid("DEMO0000-0000-0000-0002-000000000003") //g
                                                                .build())
                                                        .build())
                                        .build())
                        .composant( //poivre
                                ComposantBase.builder()
                                        .ingredient( //poivre
                                                IngredientBase.builder()
                                                        .identifiant(IdentifiantBase.builder().
                                                                uuid("DEMO0000-0000-0000-0001-000000000004") //poivre
                                                                .build())
                                                        .build())
                                        .commentaire("un peu")
                                        .build())
                        .build();
    }

    @Test
    public void testRetrieve_String() throws Exception {
        List<Recette> entites = mapperManager.getRecetteMapper()
                .retrieve(filtreRef1);

        Assertions.assertEquals(3, entites.size());
        for (Recette i : entites) {
            LOG.info(i.toString());
            //Assertions.assertTrue(i.getNom().matches(filtreRef1));
            //Ne peut pas vérifier! 
        }
    }

    @Test
    public void testRetrieve_String_Aubergine() throws Exception {
        List<Recette> entites = mapperManager.getRecetteMapper()
                .retrieve(filtreRef2);

        Assertions.assertEquals(1, entites.size());
        for (Recette i : entites) {
            LOG.info(i.toString());
            //Assertions.assertTrue(i.getNom().matches(filtreRef2));
            //Ne peut pas vérifier! 
            Assertions.assertEquals(12, i.getComposants().size());
        }
    }

    @Test
    public void testRetrieve_String_Detacher() throws Exception {
        List<Recette> entites1 = mapperManager.getRecetteMapper()
                .retrieve(filtreRef1);
        List<Recette> entites2 = mapperManager.getRecetteMapper()
                .retrieve(filtreRef1);

        Assertions.assertEquals(entites1, entites2);
        Assertions.assertNotSame(entites1, entites2);
        Assertions.assertEquals(entites1.size(), entites2.size());
        for (int j = 0; j < entites1.size(); j += 1) {
            Recette entite1 = entites1.get(j);
            Recette entite2 = entites2.get(j);
            Assertions.assertEquals(entite1, entite2);
            Assertions.assertNotSame(entite1, entite2);
            Assertions.assertEquals(entite1.getComposants().size(),
                    entite2.getComposants().size());
            for (int i = 0; i < entite1.getComposants().size(); i += 1) {
                Assertions.assertNotSame(entite1.getComposants().get(i),
                        entite2.getComposants().get(i));
                Assertions.assertEquals(entite1.getComposants().get(i).getIngredient(),
                        entite2.getComposants().get(i).getIngredient());
                Assertions.assertNotSame(entite1.getComposants().get(i).getIngredient(),
                        entite2.getComposants().get(i).getIngredient());
                Assertions.assertEquals(entite1.getComposants().get(i).getUnite(),
                        entite2.getComposants().get(i).getUnite());
                if (entite1.getComposants().get(i).getUnite() != null) {
                    Assertions.assertNotSame(entite1.getComposants().get(i).getUnite(),
                            entite2.getComposants().get(i).getUnite());
                }
            }
        }
    }

    @Test
    public void testRetrieve_StringNull() throws Exception {
        String regex = null;
        List<Recette> entites1 = mapperManager.getRecetteMapper()
                .retrieve(regex);

        Assertions.assertEquals(0, entites1.size());
    }

    @Test
    public void testRetrieve_Identifiant() throws Exception {
        Recette entite = mapperManager.getRecetteMapper()
                .retrieve(identifiantAubergineRef);

        Assertions.assertNotNull(entite);
        Assertions.assertEquals(recetteAubergineRef, entite);
        Assertions.assertEquals(recetteAubergineRef.getNom(),
                entite.getNom());
        Assertions.assertEquals(recetteAubergineRef.getDetail(),
                entite.getDetail());
        Assertions.assertEquals(recetteAubergineRef.getPreparation(),
                entite.getPreparation());
        Assertions.assertEquals(recetteAubergineRef.getNombrePersonnes(),
                entite.getNombrePersonnes());
        Assertions.assertEquals(recetteAubergineRef.getComposants().size(),
                entite.getComposants().size());
        for (int i = 0; i < recetteAubergineRef.getComposants().size(); i += 1) {
            Assertions.assertEquals(recetteAubergineRef.getComposants().get(i).getIngredient(),
                    entite.getComposants().get(i).getIngredient());
            Assertions.assertNotNull(entite.getComposants().get(i).getIngredient().getNom());
            Assertions.assertEquals(recetteAubergineRef.getComposants().get(i).getQuantite(),
                    entite.getComposants().get(i).getQuantite());
            Assertions.assertEquals(recetteAubergineRef.getComposants().get(i).getUnite(),
                    entite.getComposants().get(i).getUnite());
            Assertions.assertEquals(recetteAubergineRef.getComposants().get(i).getCommentaire(),
                    entite.getComposants().get(i).getCommentaire());
        }
    }

    @Test
    public void testRetrieve_Identifiant_Detacher() throws Exception {
        Recette entite1 = mapperManager.getRecetteMapper()
                .retrieve(identifiantAubergineRef);

        Recette entite2 = mapperManager.getRecetteMapper()
                .retrieve(identifiantAubergineRef);

        Assertions.assertEquals(entite1, entite2);
        Assertions.assertNotSame(entite1, entite2);
        Assertions.assertEquals(entite1.getComposants().size(),
                entite2.getComposants().size());
        for (int i = 0; i < entite1.getComposants().size(); i += 1) {
            Assertions.assertNotSame(entite1.getComposants().get(i),
                    entite2.getComposants().get(i));
            Assertions.assertEquals(entite1.getComposants().get(i).getIngredient(),
                    entite2.getComposants().get(i).getIngredient());
            Assertions.assertNotSame(entite1.getComposants().get(i).getIngredient(),
                    entite2.getComposants().get(i).getIngredient());
            Assertions.assertEquals(entite1.getComposants().get(i).getUnite(),
                    entite2.getComposants().get(i).getUnite());
            if (entite1.getComposants().get(i).getUnite() != null) {
                Assertions.assertNotSame(entite1.getComposants().get(i).getUnite(),
                        entite2.getComposants().get(i).getUnite());
            }
        }
    }

    @Test
    public void testRetrieve_IdentifiantNull() throws Exception {
        Identifiant identifiant = null;
        Recette entite = mapperManager.getRecetteMapper()
                .retrieve(identifiant);

        Assertions.assertNull(entite);
    }

    @Test
    public void testCreate() throws Exception {
        Recette nouvelleEntite = mapperManager.getRecetteMapper()
                .create(nouvelleEntiteRef);

        Assertions.assertNotNull(nouvelleEntite.getIdentifiant());
        Assertions.assertEquals(nouvelleEntiteRef.getNom(),
                nouvelleEntite.getNom());
        Assertions.assertEquals(nouvelleEntiteRef.getDetail(),
                nouvelleEntite.getDetail());
        Assertions.assertEquals(nouvelleEntiteRef.getPreparation(),
                nouvelleEntite.getPreparation());
        Assertions.assertEquals(nouvelleEntiteRef.getNombrePersonnes(),
                nouvelleEntite.getNombrePersonnes());
        Assertions.assertEquals(nouvelleEntiteRef.getComposants().size(),
                nouvelleEntite.getComposants().size());
        for (int i = 0; i < nouvelleEntiteRef.getComposants().size(); i += 1) {
            Assertions.assertNotSame(nouvelleEntiteRef.getComposants().get(i),
                    nouvelleEntite.getComposants().get(i));
            Assertions.assertEquals(nouvelleEntiteRef.getComposants().get(i).getIngredient(),
                    nouvelleEntite.getComposants().get(i).getIngredient());
            Assertions.assertNotNull(nouvelleEntite.getComposants().get(i).getIngredient().getNom());
            Assertions.assertEquals(nouvelleEntiteRef.getComposants().get(i).getQuantite(),
                    nouvelleEntite.getComposants().get(i).getQuantite());
            Assertions.assertEquals(nouvelleEntiteRef.getComposants().get(i).getUnite(),
                    nouvelleEntite.getComposants().get(i).getUnite());
            Assertions.assertEquals(nouvelleEntiteRef.getComposants().get(i).getCommentaire(),
                    nouvelleEntite.getComposants().get(i).getCommentaire());
        }

        Recette entite = mapperManager.getRecetteMapper()
                .retrieve(nouvelleEntite.getIdentifiant());

        Assertions.assertEquals(nouvelleEntite, entite);
        Assertions.assertEquals(nouvelleEntite.getDetail(),
                entite.getDetail());
        Assertions.assertEquals(nouvelleEntite.getPreparation(),
                entite.getPreparation());
        Assertions.assertEquals(nouvelleEntite.getNombrePersonnes(),
                entite.getNombrePersonnes());
        Assertions.assertEquals(nouvelleEntite.getComposants().size(),
                entite.getComposants().size());
        for (int i = 0; i < nouvelleEntite.getComposants().size(); i += 1) {
            Assertions.assertEquals(nouvelleEntite.getComposants().get(i).getIngredient(),
                    entite.getComposants().get(i).getIngredient());
            Assertions.assertNotNull(entite.getComposants().get(i).getIngredient().getNom());
            Assertions.assertEquals(nouvelleEntite.getComposants().get(i).getQuantite(),
                    entite.getComposants().get(i).getQuantite());
            Assertions.assertEquals(nouvelleEntite.getComposants().get(i).getUnite(),
                    entite.getComposants().get(i).getUnite());
            if (entite.getComposants().get(i).getUnite() != null) {
                Assertions.assertNotNull(entite.getComposants().get(i).getUnite().getCode());
            }
            Assertions.assertEquals(nouvelleEntite.getComposants().get(i).getCommentaire(),
                    entite.getComposants().get(i).getCommentaire());
        }
    }

    @Test
    public void testCreateNomNotUnique() throws Exception {
        Assertions.assertThrows(ContrainteUniquePersistenceException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                mapperManager.getRecetteMapper()
                        .create(recetteAubergineRef);
            }
        });
    }

    @Test
    public void testCreateNomNull() throws Exception {
        Assertions.assertThrows(ContrainteNotNullPersistenceException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                nouvelleEntiteRef.setNom(null);
                mapperManager.getRecetteMapper()
                        .create(nouvelleEntiteRef);
            }
        });
    }

    @Test
    public void testDelete() throws Exception {
        Recette nouvelleEntite = mapperManager.getRecetteMapper()
                .create(nouvelleEntiteRef);
        Recette entite = mapperManager.getRecetteMapper()
                .retrieve(nouvelleEntite.getIdentifiant());

        Assertions.assertNotNull(entite);

        mapperManager.getRecetteMapper().delete(entite);
        Recette entiteDel = mapperManager.getRecetteMapper()
                .retrieve(entite.getIdentifiant());

        Assertions.assertNull(entiteDel);
    }

    @Test
    public void testDeleteEntiteInconnu() throws Exception {
        Assertions.assertThrows(EntiteInconnuePersistenceException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Recette entite = RecetteBase.builder()
                        .recette(nouvelleEntiteRef)
                        .identifiant(IdentifiantBase.builder()
                                .build())
                        .build();
                mapperManager.getRecetteMapper()
                        .delete(entite);
            }
        });
    }

    @Test
    public void testUpdate() throws Exception {
        Recette nouvelleEntite = mapperManager.getRecetteMapper()
                .create(nouvelleEntiteRef);
        Recette entite = mapperManager.getRecetteMapper()
                .retrieve(nouvelleEntite.getIdentifiant());
        Recette entiteModifie
                = //thym
                //brin
                RecetteBase.builder()
                        .identifiant(entite.getIdentifiant())
                        .nom(entite.getNom() + " update")
                        .detail(entite.getDetail() + " update")
                        .preparation(entite.getPreparation() + " update")
                        .nombrePersonnes(entite.getNombrePersonnes() * 10)
                        .composant(entite.getComposants().get(2))
                        .composant( //thym
                                //brin
                                ComposantBase.builder()
                                        .ingredient( //thym
                                                IngredientBase.builder()
                                                        .identifiant( //thym
                                                                IdentifiantBase.builder()
                                                                        .uuid(DemoData.INGREDIENTS.THYM.UUID) //thym
                                                                        .build())
                                                        .build())
                                        .quantite(1.0)
                                        .unite( //brin
                                                UniteBase.builder().identifiant( //brin
                                                        IdentifiantBase.builder()
                                                                .uuid(DemoData.UNITES.BRINS.UUID) //brin
                                                                .build())
                                                        .build())
                                        .commentaire("commentaire update")
                                        .build())
                        .build();

        mapperManager.getRecetteMapper()
                .update(entiteModifie);

        Recette entiteMod = mapperManager.getRecetteMapper()
                .retrieve(entiteModifie.getIdentifiant());

        Assertions.assertEquals(entiteModifie, entiteMod);
        Assertions.assertEquals(entiteModifie.getDetail(),
                entiteMod.getDetail());
        Assertions.assertEquals(entiteModifie.getPreparation(),
                entiteMod.getPreparation());
        Assertions.assertEquals(entiteModifie.getNombrePersonnes(),
                entiteMod.getNombrePersonnes());
        Assertions.assertEquals(entiteModifie.getComposants().size(),
                entiteMod.getComposants().size());
        for (int i = 0; i < entiteModifie.getComposants().size(); i += 1) {
            Assertions.assertEquals(entiteModifie.getComposants().get(i).getIngredient(),
                    entiteMod.getComposants().get(i).getIngredient());
            Assertions.assertNotNull(entiteMod.getComposants().get(i).getIngredient().getNom());
            Assertions.assertEquals(entiteModifie.getComposants().get(i).getQuantite(),
                    entiteMod.getComposants().get(i).getQuantite());
            Assertions.assertEquals(entiteModifie.getComposants().get(i).getUnite(),
                    entiteMod.getComposants().get(i).getUnite());
            if (entiteMod.getComposants().get(i).getUnite() != null) {
                Assertions.assertNotNull(entiteMod.getComposants().get(i).getUnite().getCode());
            }
            Assertions.assertEquals(entiteModifie.getComposants().get(i).getCommentaire(),
                    entiteMod.getComposants().get(i).getCommentaire());
        }
    }

    @Test
    public void testUpdateEntiteInconnu() throws Exception {
        Assertions.assertThrows(EntiteInconnuePersistenceException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Recette entiteMod = RecetteBase.builder()
                        .recette(nouvelleEntiteRef)
                        .identifiant(IdentifiantBase.builder()
                                .build())
                        .build();
                mapperManager.getRecetteMapper()
                        .update(entiteMod);
            }
        });
    }

    @Test
    public void testUpdateIngredientInconnu() throws Exception {
        Assertions.assertThrows(EntiteInconnuePersistenceException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Recette entite = mapperManager.getRecetteMapper()
                        .retrieve(identifiantAubergineRef);
                entite.getComposants().add(ComposantBase.builder()
                        .ingredient(IngredientBase.builder()
                                .identifiant(IdentifiantBase.builder()
                                        .build())
                                .build())
                        .build());
                mapperManager.getRecetteMapper().update(entite);
            }
        });
    }

    @Test
    public void testUpdateUniteInconnu() throws Exception {
        Assertions.assertThrows(EntiteInconnuePersistenceException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Recette entite = mapperManager.getRecetteMapper().retrieve(identifiantAubergineRef);
                entite.getComposants().add(ComposantBase.builder()
                        .ingredient(IngredientBase.builder()
                                .identifiant(IdentifiantBase.builder()
                                        .uuid(DemoData.INGREDIENTS.SEL.UUID)
                                        .build())
                                .build())
                        .unite(UniteBase.builder()
                                .identifiant(IdentifiantBase.builder()
                                        .build())
                                .build())
                        .build());
                mapperManager.getRecetteMapper().update(entite);
            }
        });
    }

    @Test
    public void testUpdateNomNull() throws Exception {
        Assertions.assertThrows(ContrainteNotNullPersistenceException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Recette entite = mapperManager.getRecetteMapper()
                        .retrieve(identifiantAubergineRef);
                entite.setNom(null);
                mapperManager.getRecetteMapper()
                        .update(entite);
            }
        });
    }

}
