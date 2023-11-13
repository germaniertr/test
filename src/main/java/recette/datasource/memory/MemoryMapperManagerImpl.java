package recette.datasource.memory;

import core.datasource.DatabaseSetup;
import recette.datasource.IngredientMapper;
import recette.datasource.MapperManager;
import recette.datasource.RecetteMapper;
import recette.datasource.UniteMapper;
import recette.domain.DemoData;

/**
 *
 * @author dominique huguenin (dominique.huguenin@rpn.ch)
 */
public final class MemoryMapperManagerImpl implements MapperManager {

    private static MemoryMapperManagerImpl mapperManager;

    private final DemoData data;
    private DatabaseSetup databaseSetup;
    private UniteMapper uniteMapper;
    private IngredientMapper ingredientMapper;
    private RecetteMapper recetteMapper;

    private MemoryMapperManagerImpl() {
        this.data = new DemoData();
    }

    public static MapperManager getInstance() {
        if (mapperManager == null) {
            mapperManager = new MemoryMapperManagerImpl();
        }
        return mapperManager;
    }

    @Override
    public UniteMapper getUniteMapper() {
        if (this.uniteMapper == null) {
            this.uniteMapper = new UniteMapperImpl(this);
        }
        return this.uniteMapper;
    }

    @Override
    public IngredientMapper getIngredientMapper() {
        if (this.ingredientMapper == null) {
            this.ingredientMapper = new IngredientMapperImpl(this);
        }
        return this.ingredientMapper;
    }

    @Override
    public DatabaseSetup getDatabaseSetup() {
        if (this.databaseSetup == null) {
            this.databaseSetup = new DatabaseSetupImpl(this);
        }
        return this.databaseSetup;
    }

    @Override
    public RecetteMapper getRecetteMapper() {
        if (this.recetteMapper == null) {
            this.recetteMapper = new RecetteMapperImpl(this);
        }
        return this.recetteMapper;
    }

    DemoData getData() {
        return this.data;
    }

}
