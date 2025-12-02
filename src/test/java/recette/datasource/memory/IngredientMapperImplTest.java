/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package recette.datasource.memory;

import core.datasource.PersistenceException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import recette.datasource.MapperManager;
import recette.datasource.RecetteRef;
import recette.domain.Ingredient;

/**
 *
 * @author dom
 */
public class IngredientMapperImplTest {

    private final MapperManager mapperManager;
    private final String filtreRef;

    public IngredientMapperImplTest() throws PersistenceException {
        mapperManager = MemoryMapperManagerImpl.getInstance();
        mapperManager.getDatabaseSetup().dropTables();
        mapperManager.getDatabaseSetup().createTables();
        mapperManager.getDatabaseSetup().insertData();

        filtreRef = "^.*(aubergine|tomate|thym).*$";

    }

    @BeforeEach
    public void setUp() {
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

}
