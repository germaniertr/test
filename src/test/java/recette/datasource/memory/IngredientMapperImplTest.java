package recette.datasource.memory;

import core.datasource.PersistenceException;

public class IngredientMapperImplTest
        extends recette.datasource.IngredientMapperImplTest {

    public IngredientMapperImplTest() throws PersistenceException {
        super(MemoryTransactionManagerImpl.getInstance());

        filtreRef = "^.*(aubergine|tomate|thym).*$";

    }
}
