package recette.datasource.db;

import core.datasource.PersistenceException;
import core.domain.Identifiant;
import java.util.List;
import recette.datasource.UniteMapper;
import recette.domain.Unite;

/**
 *
 * @author dominique huguenin (dominique.huguenin@rpn.ch)
 */
public class UniteMapperImpl implements UniteMapper {

    @Override
    public Unite create(final Unite entite) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Unite retrieve(final Identifiant id) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Unite> retrieve(final String filtre) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(final Unite entite) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(final Unite entite) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
