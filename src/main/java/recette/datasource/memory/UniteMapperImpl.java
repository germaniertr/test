package recette.datasource.memory;

import core.datasource.PersistenceException;
import core.domain.Identifiant;
import core.domain.IdentifiantBase;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import recette.datasource.UniteMapper;
import recette.domain.Unite;
import recette.domain.UniteBase;

/**
 *
 * @author dominique huguenin (dominique.huguenin@rpn.ch)
 */
public class UniteMapperImpl implements UniteMapper {

    private final MemoryMapperManagerImpl mapperManager;

    UniteMapperImpl(final MemoryMapperManagerImpl mm) {
        this.mapperManager = mm;
    }

    @Override
    public Unite create(final Unite entite) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Unite retrieve(final Identifiant id) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Unite> retrieve(final String regex) throws PersistenceException {
        if (regex == null) {
            return new ArrayList<>();
        }

        Pattern pattern = Pattern.compile(regex);

        List<Unite> entites = new ArrayList<>();

        for (Unite e : mapperManager.getData()
                .getUnites().values()) {
            Matcher matcher = pattern.matcher(e.getCode());
            if (matcher.find()) {
                entites.add(UniteBase.builder()
                        .unite(e)
                        .identifiant(IdentifiantBase.builder()
                                .identifiant(e.getIdentifiant())
                                .build())
                        .build());
            }
        }

        return entites;
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
