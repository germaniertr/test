package recette.datasource.memory;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import recette.datasource.MapperManager;
import recette.domain.DemoData;
import recette.domain.Ingredient;
import recette.domain.Recette;
import recette.domain.Unite;

/**
 *
 * @author dominique huguenin <dominique.huguenin at rpn.ch>
 */
public class DatabaseSetupImplTest {

    private final MapperManager mapperManager;

    public DatabaseSetupImplTest() {
        this.mapperManager = MemoryMapperManagerImpl.getInstance();
    }

    @Test
    public void testDropCreateTable() throws Exception {
        mapperManager.getDatabaseSetup().dropTables();
        mapperManager.getDatabaseSetup().createTables();

        List<Unite> unites = mapperManager.getUniteMapper()
                .retrieve(".*");
        Assertions.assertTrue(unites.isEmpty());

        List<Ingredient> ingredients = mapperManager.getIngredientMapper()
                .retrieve(".*");
        Assertions.assertTrue(ingredients.isEmpty());

        List<Recette> recettes = mapperManager.getRecetteMapper()
                .retrieve(".*");
        Assertions.assertTrue(recettes.isEmpty());

    }

    @Test
    public void testDropCreateTablesInsertData() throws Exception {
        mapperManager.getDatabaseSetup().dropTables();
        mapperManager.getDatabaseSetup().createTables();
        mapperManager.getDatabaseSetup().insertData();

        List<Unite> unites = mapperManager.getUniteMapper()
                .retrieve(".*");
        Assertions.assertFalse(unites.isEmpty());

        List<Ingredient> ingredients = mapperManager.getIngredientMapper()
                .retrieve(".*");
        Assertions.assertFalse(ingredients.isEmpty());

        List<Recette> recettes = mapperManager.getRecetteMapper()
                .retrieve(".*");
        Assertions.assertFalse(recettes.isEmpty());

    }

}
