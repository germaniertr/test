package recette.datasource.db;

import core.datasource.PersistenceException;
import core.domain.Identifiant;
import java.util.List;
import recette.datasource.IngredientMapper;
import recette.domain.Ingredient;

/**
 *
 * @author dominique huguenin (dominique.huguenin@rpn.ch)
 */
public class IngredientMapperImpl implements IngredientMapper {

    @Override
    public Ingredient create(final Ingredient entite) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Ingredient retrieve(final Identifiant id) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Ingredient> retrieve(final String filtre) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet.");
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
