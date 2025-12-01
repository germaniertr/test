package recette.datasource.memory;

import core.datasource.PersistenceException;
import core.domain.Identifiant;
import java.util.List;
import recette.datasource.RecetteMapper;
import recette.domain.Recette;

/**
 *
 * @author dominique huguenin (dominique.huguenin@rpn.ch)
 */
public class RecetteMapperImpl implements RecetteMapper {

    @Override
    public Recette create(final Recette entite) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Recette retrieve(final Identifiant id) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Recette> retrieve(final String filtre) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(final Recette entite) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(final Recette entite) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
