package recette.datasource.memory;

import core.datasource.DatabaseSetup;
import core.datasource.PersistenceException;
import recette.domain.Composant;
import recette.domain.ComposantBase;
import recette.domain.DemoData;
import recette.domain.Ingredient;
import recette.domain.IngredientBase;
import recette.domain.Recette;
import recette.domain.RecetteBase;
import recette.domain.Unite;
import recette.domain.UniteBase;

/**
 *
 * @author dominique huguenin (dominique.huguenin@rpn.ch)
 */
public class DatabaseSetupImpl implements DatabaseSetup {

    private final MemoryMapperManagerImpl mapperManager;

    DatabaseSetupImpl(final MemoryMapperManagerImpl mm) {
        this.mapperManager = mm;
    }

    @Override
    public void createTables() throws PersistenceException {
        this.mapperManager.getData().getRecettes().clear();
        this.mapperManager.getData().getIngredients().clear();
        this.mapperManager.getData().getUnites().clear();
    }

    @Override
    public void dropTables() throws PersistenceException {
        this.mapperManager.getData().getRecettes().clear();
        this.mapperManager.getData().getIngredients().clear();
        this.mapperManager.getData().getUnites().clear();
    }

    @Override
    public void insertData() throws PersistenceException {
        DemoData newData = new DemoData();
        newData.initialisation();

        for (Unite e : newData.getUnites().values()) {
            this.mapperManager.getData().getUnites()
                    .put(e.getIdentifiant(),
                            UniteBase.builder()
                                    .unite(e)
                                    .build());
        }

        for (Ingredient e : newData.getIngredients().values()) {
            this.mapperManager.getData().getIngredients()
                    .put(e.getIdentifiant(),
                            IngredientBase.builder().
                                    ingredient(e).
                                    build());
        }

        for (Recette e : newData.getRecettes().values()) {
            Recette em = RecetteBase.builder().recette(e).build();
            em.getComposants().clear();
            for (Composant c : e.getComposants()) {
                Unite um = null;
                if (c.getUnite() != null) {
                    um = this.mapperManager.getData().getUnites()
                            .get(c.getUnite().getIdentifiant());
                }
                Ingredient im = null;
                if (c.getIngredient() != null) {
                    im = this.mapperManager.getData().getIngredients()
                            .get(c.getIngredient().getIdentifiant());
                }
                Composant cm = ComposantBase.builder()
                        .composant(c)
                        .unite(um)
                        .ingredient(im)
                        .build();
                em.getComposants().add(cm);
            }
            this.mapperManager.getData().getRecettes().put(em.getIdentifiant(), em);
        }

        for (Ingredient em : this.mapperManager.getData().getIngredients().values()) {
            if (em.getRecette() != null) {
                Recette rm = this.mapperManager.getData()
                        .getRecettes().get(em.getRecette().getIdentifiant());
                em.setRecette(rm);
            }
        }

    }

}
