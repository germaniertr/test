package recette.datasource;

import core.datasource.TransactionManager;
import core.datasource.TransactionManager.Operation;
import org.junit.jupiter.api.Test;

/**
 *
 * @author dominique huguenin (dominique.huguenin at rpn.ch)
 */
public abstract class DatabaseSetupImplTest {

    private final TransactionManager transactionManager;

    public DatabaseSetupImplTest(TransactionManager tm) {
        this.transactionManager = tm;
    }

    @Test
    public void testDropCreateTable() throws Exception {
        transactionManager.executeTransaction(
                (Operation<MapperManager>) (MapperManager mm) -> {
                    mm.getDatabaseSetup().dropTables();
                    mm.getDatabaseSetup().createTables();
                    return null;
                });

//        List<Unite> unites = mapperManager.getUniteMapper().retrieve(".*");
//        Assertions.assertTrue(unites.isEmpty());
//        List<Ingredient> ingredients = mapperManager.getIngredientMapper().retrieve(".*");
//        Assertions.assertTrue(ingredients.isEmpty());
//        List<Recette> recettes = mapperManager.getRecetteMapper().retrieve(".*");
//        Assertions.assertTrue(recettes.isEmpty());
    }

    @Test
    public void testDropCreateTablesInsertData() throws Exception {
        transactionManager.executeTransaction(
                (Operation<MapperManager>) (MapperManager mm) -> {
                    mm.getDatabaseSetup().dropTables();
                    mm.getDatabaseSetup().createTables();
                    mm.getDatabaseSetup().insertData();
                    return null;
                });

//        List<Unite> unites = mapperManager.getUniteMapper().retrieve(".*");
//        Assertions.assertFalse(unites.isEmpty());
//        List<Ingredient> ingredients = mapperManager.getIngredientMapper().retrieve(".*");
//        Assertions.assertFalse(ingredients.isEmpty());
//        List<Recette> recettes = mapperManager.getRecetteMapper().retrieve(".*");
//        Assertions.assertFalse(recettes.isEmpty());
    }

}
