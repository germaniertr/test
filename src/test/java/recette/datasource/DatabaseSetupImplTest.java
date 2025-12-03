package recette.datasource;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 *
 * @author dominique huguenin (dominique.huguenin at rpn.ch)
 */
public abstract class DatabaseSetupImplTest {

    protected final MapperManager mapperManager;

    public DatabaseSetupImplTest(MapperManager mapperManager) {
        this.mapperManager = mapperManager;
    }

    @Test
    public void testDropCreateTable() throws Exception {
        mapperManager.getDatabaseSetup().dropTables();
        mapperManager.getDatabaseSetup().createTables();
//        List<Unite> unites = mapperManager.getUniteMapper().retrieve(".*");
//        Assertions.assertTrue(unites.isEmpty());
//        List<Ingredient> ingredients = mapperManager.getIngredientMapper().retrieve(".*");
//        Assertions.assertTrue(ingredients.isEmpty());
//        List<Recette> recettes = mapperManager.getRecetteMapper().retrieve(".*");
//        Assertions.assertTrue(recettes.isEmpty());
    }

    @Test
    public void testDropCreateTablesInsertData() throws Exception {
        mapperManager.getDatabaseSetup().dropTables();
        mapperManager.getDatabaseSetup().createTables();
        mapperManager.getDatabaseSetup().insertData();
//        List<Unite> unites = mapperManager.getUniteMapper().retrieve(".*");
//        Assertions.assertFalse(unites.isEmpty());
//        List<Ingredient> ingredients = mapperManager.getIngredientMapper().retrieve(".*");
//        Assertions.assertFalse(ingredients.isEmpty());
//        List<Recette> recettes = mapperManager.getRecetteMapper().retrieve(".*");
//        Assertions.assertFalse(recettes.isEmpty());
    }

}
