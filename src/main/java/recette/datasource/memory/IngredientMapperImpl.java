package recette.datasource.memory;

import core.datasource.PersistenceException;
import core.domain.Identifiant;
import core.domain.IdentifiantBase;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import recette.datasource.IngredientMapper;
import recette.datasource.RecetteRef;
import recette.domain.Ingredient;
import recette.domain.IngredientBase;

/**
 *
 * @author dominique huguenin (dominique.huguenin@rpn.ch)
 */
public class IngredientMapperImpl implements IngredientMapper {

    private final MemoryMapperManagerImpl mapperManager;

    public IngredientMapperImpl(final MemoryMapperManagerImpl mm) {
        this.mapperManager = mm;
    }

    @Override
    public Ingredient create(final Ingredient entite) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Ingredient retrieve(final Identifiant id) throws PersistenceException {
        if (id == null) {
            return null;
        }

        Ingredient entite = mapperManager.getData().getIngredients().get(id);
        if (entite != null) {
            entite = IngredientBase.builder()
                    .ingredient(entite)
                    .identifiant(IdentifiantBase.builder()
                            .identifiant(entite.getIdentifiant())
                            .build())
                    .build();

            if (entite.getRecette() != null) {
                entite.setRecette(new RecetteRef(entite.getRecette()));
            }
        }
        return entite;

    }

    @Override
    public List<Ingredient> retrieve(final String regex) throws PersistenceException {
        if (regex == null) {
            return new ArrayList<>();
        }

        Pattern pattern = Pattern.compile(regex);

        List<Ingredient> entites = new ArrayList<>();

        for (Ingredient e : mapperManager.getData()
                .getIngredients().values()) {
            Matcher matcher = pattern.matcher(e.getNom());
            if (matcher.find()) {
                Ingredient entite = IngredientBase.builder()
                        .ingredient(e)
                        .identifiant(IdentifiantBase.builder()
                                .identifiant(e.getIdentifiant())
                                .build())
                        .build();

                if (entite.getRecette() != null) {
                    entite.setRecette(new RecetteRef(entite.getRecette()));
                }

                entites.add(entite);
            }
        }

        return entites;

    }

    @Override
    public void update(final Ingredient entite) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(final Ingredient entite) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
