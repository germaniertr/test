package recette.datasource.memory;

import core.datasource.ContrainteNotNullPersistenceException;
import core.datasource.ContrainteUniquePersistenceException;
import core.datasource.PersistenceException;
import core.domain.Identifiant;
import core.domain.IdentifiantBase;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import recette.datasource.MapperManager;
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
public class RecetteMapperImplTest {

    private final MapperManager mapperManager;
    private final String filtreRef1;
    private final String filtreRef2;
    private Identifiant identifiantAubergineRef;
    private final DemoData demoData;
    private Recette recetteAubergineRef;
    private Recette nouvelleEntiteRef;

    public RecetteMapperImplTest() throws PersistenceException {
        mapperManager = MemoryMapperManagerImpl.getInstance();
        mapperManager.getDatabaseSetup().dropTables();
        mapperManager.getDatabaseSetup().createTables();
        mapperManager.getDatabaseSetup().insertData();

        filtreRef1 = "^.*(Poires|Poivrons|tomates).*$";
        filtreRef2 = "^.*(Aubergine).*$";

        demoData = new DemoData();
        demoData.initialisation();

    }

    @BeforeEach
    public void setUp() {
        identifiantAubergineRef = IdentifiantBase.builder()
                .uuid(DemoData.RECETTES.AUBERGINES_AU_FOUR.UUID)
                .build();
        recetteAubergineRef = demoData.getRecettes().get(identifiantAubergineRef);

        nouvelleEntiteRef = RecetteBase.builder()
                .nom("nouvelle recette " + Instant.now().toString())
                .detail("description du nouvelle recette")
                .preparation("préparation de la nouvelle recette")
                .nombrePersonnes(100)
                .composant(ComposantBase.builder()
                        .ingredient(IngredientBase.builder()
                                .identifiant(IdentifiantBase.builder()
                                        .uuid("DEMO0000-0000-0000-0001-000000000001")//aubergine
                                        .build())
                                .build())
                        .quantite(10.0)
                        .build())
                .composant(ComposantBase.builder()
                        .ingredient(IngredientBase.builder()
                                .identifiant(IdentifiantBase.builder()
                                        .uuid("DEMO0000-0000-0000-0001-000000000007")//Feta
                                        .build())
                                .build())
                        .quantite(250.0)
                        .unite(UniteBase.builder()
                                .identifiant(IdentifiantBase.builder()
                                        .uuid("DEMO0000-0000-0000-0002-000000000003")//g
                                        .build())
                                .build())
                        .build())
                .composant(ComposantBase.builder()
                        .ingredient(IngredientBase.builder()
                                .identifiant(IdentifiantBase.builder()
                                        .uuid("DEMO0000-0000-0000-0001-000000000004") //poivre
                                        .build())
                                .build())
                        .commentaire("un peu")
                        .build())
                .build();

    }

    @Test
    public void testRetrieve_String() throws Exception {
        List<Recette> entites
                = mapperManager.getRecetteMapper()
                        .retrieve(filtreRef1);

        Assertions.assertEquals(3, entites.size());
        for (Recette i : entites) {
            Assertions.assertTrue(i.getNom().matches(filtreRef1));
        }
    }

    @Test
    public void testRetrieve_String_Aubergine() throws Exception {
        List<Recette> entites
                = mapperManager.getRecetteMapper()
                        .retrieve(filtreRef2);

        Assertions.assertEquals(1, entites.size());
        for (Recette i : entites) {
            Assertions.assertTrue(i.getNom().matches(filtreRef2));
            Assertions.assertEquals(12, i.getComposants().size());
        }
    }

    @Test
    public void testRetrieve_String_Detacher() throws Exception {
        List<Recette> entites1
                = mapperManager.getRecetteMapper()
                        .retrieve(filtreRef1);

        List<Recette> entites2
                = mapperManager.getRecetteMapper()
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

        List<Recette> entites1
                = mapperManager.getRecetteMapper()
                        .retrieve(regex);
        Assertions.assertEquals(0, entites1.size());
    }

    @Test
    public void testRetrieve_Identifiant() throws Exception {
        Recette entite = mapperManager.getRecetteMapper()
                .retrieve(identifiantAubergineRef);

        Assertions.assertNotNull(entite);

        Assertions.assertEquals(recetteAubergineRef, entite);
        Assertions.assertEquals(recetteAubergineRef.getNom(), entite.getNom());
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

        Assertions.assertEquals(entite1.getComposants().size(), entite2.getComposants().size());
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
        Recette entite
                = mapperManager.getRecetteMapper()
                        .retrieve(identifiant);
        Assertions.assertNull(entite);
    }

    @Test
    public void testCreate() throws Exception {
        Recette nouvelleEntite
                = mapperManager.getRecetteMapper()
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

        Recette entite
                = mapperManager.getRecetteMapper()
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
        Assertions.assertThrows(
                ContrainteUniquePersistenceException.class, new Executable() {
            @Override
            public void execute() throws Throwable {

                mapperManager.getRecetteMapper()
                        .create(recetteAubergineRef);
            }
        });

    }

    @Test
    public void testCreateNomNull() throws Exception {
        Assertions.assertThrows(
                ContrainteNotNullPersistenceException.class, new Executable() {
            @Override
            public void execute() throws Throwable {

                nouvelleEntiteRef.setNom(null);

                mapperManager.getRecetteMapper()
                        .create(nouvelleEntiteRef);
            }
        });

    }

}
