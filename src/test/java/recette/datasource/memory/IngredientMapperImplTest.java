package recette.datasource.memory;

import core.datasource.PersistenceException;

public class IngredientMapperImplTest
        extends recette.datasource.IngredientMapperImplTest {

    public IngredientMapperImplTest() throws PersistenceException {
        super(MemoryMapperManagerImpl.getInstance());

        filtreRef = "^.*(aubergine|tomate|thym).*$";

    }
}
