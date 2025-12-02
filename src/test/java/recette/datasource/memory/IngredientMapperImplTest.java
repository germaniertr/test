/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package recette.datasource.memory;

import core.datasource.PersistenceException;
import core.domain.Identifiant;
import core.domain.IdentifiantBase;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import recette.datasource.MapperManager;
import recette.datasource.RecetteRef;
import recette.domain.DemoData;
import recette.domain.Ingredient;
import recette.domain.IngredientBase;

/**
 *
 * @author dom
 */
public class IngredientMapperImplTest {

    private final MapperManager mapperManager;
    private final String filtreRef;
    private Identifiant identifiantAubergine;
    private Ingredient ingredientAubergine;

    public IngredientMapperImplTest() throws PersistenceException {
        mapperManager = MemoryMapperManagerImpl.getInstance();
        mapperManager.getDatabaseSetup().dropTables();
        mapperManager.getDatabaseSetup().createTables();
        mapperManager.getDatabaseSetup().insertData();

        filtreRef = "^.*(aubergine|tomate|thym).*$";

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
            Assertions.assertTrue(i.getNom().matches(filtreRef));
        }

    }

    @Test
    public void testRetrieve_String_RecetteRef() throws Exception {
        List<Ingredient> entites
                = mapperManager.getIngredientMapper()
                        .retrieve(filtreRef);

        Assertions.assertEquals(4, entites.size());
        for (Ingredient i : entites) {
            Assertions.assertTrue(i.getNom().matches(filtreRef));
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

//        Recette recette = mapperManager.getRecetteMapper()
//                        .retrieve(IdentifiantBase.builder()
//                                .uuid(DemoData.RECETTES.SAUCE_TOMATES.UUID)
//                                .build());
//        Assertions.assertNotSame(recette, entite.getRecette());
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

}
