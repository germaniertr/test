package recette.datasource.memory;

import core.datasource.PersistenceException;
import core.domain.Identifiant;
import core.domain.IdentifiantBase;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import recette.datasource.MapperManager;
import recette.domain.ComposantBase;
import recette.domain.DemoData;
import recette.domain.Recette;
import recette.domain.RecetteBase;

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

}
