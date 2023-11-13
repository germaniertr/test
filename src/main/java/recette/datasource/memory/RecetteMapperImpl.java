package recette.datasource.memory;

import core.datasource.EntiteInconnuePersistenceException;
import core.datasource.PersistenceException;
import core.domain.Identifiant;
import core.domain.IdentifiantBase;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import recette.datasource.RecetteMapper;
import recette.domain.Composant;
import recette.domain.ComposantBase;
import recette.domain.Ingredient;
import recette.domain.Recette;
import recette.domain.RecetteBase;
import recette.domain.Unite;

/**
 *
 * @author dominique huguenin (dominique.huguenin@rpn.ch)
 */
public class RecetteMapperImpl implements RecetteMapper {

    private final MemoryMapperManagerImpl mapperManager;

    RecetteMapperImpl(final MemoryMapperManagerImpl mm) {
        this.mapperManager = mm;
    }

    @Override
    public Recette create(final Recette entite) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Recette retrieve(final Identifiant id) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Recette> retrieve(final String regex) throws PersistenceException {
        if (regex == null) {
            return new ArrayList<>();
        }

        Pattern pattern = Pattern.compile(regex);

        List<Recette> entites = new ArrayList<>();

        for (Recette i : mapperManager.getData()
                .getRecettes().values()) {
            Matcher matcher = pattern.matcher(i.getNom());
            if (matcher.find()) {
                entites.add(deepClone(i));
            }
        }

        return entites;
    }

    @Override
    public void update(final Recette entite) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(final Recette entite) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private Recette deepClone(final Recette entite)
            throws PersistenceException {
        RecetteBase.Builder builder = RecetteBase.builder()
                .identifiant(IdentifiantBase.builder()
                        .identifiant(entite.getIdentifiant())
                        .build())
                .nom(entite.getNom())
                .detail(entite.getDetail())
                .preparation(entite.getPreparation())
                .nombrePersonnes(entite.getNombrePersonnes());

        int numero = 0;
        for (Composant c : entite.getComposants()) {
            Ingredient ingredient = this.mapperManager.getIngredientMapper()
                    .retrieve(c.getIngredient().getIdentifiant());
            checkIngredientInconnu(ingredient, c);

            Unite unite = null;
            if (c.getUnite() != null) {
                unite = this.mapperManager.getUniteMapper()
                        .retrieve(c.getUnite().getIdentifiant());

                checkUniteInconnue(unite, c);
            }

            numero = numero + 1;
            Composant composant = ComposantBase.builder()
                    .composant(c)
                    .ingredient(ingredient)
                    .unite(unite)
                    .build();

            builder.composant(composant);
        }

        return builder.build();
    }

    private void checkIngredientInconnu(final Ingredient ingredient,
            final Composant c)
            throws EntiteInconnuePersistenceException {
        if (ingredient == null) {
            throw new EntiteInconnuePersistenceException(
                    String.format("Erreur: L'ingrédient avec "
                            + "l'uuid %s est inconnue!",
                            c.getIngredient().getIdentifiant().getUUID()));
        }
    }

    private void checkUniteInconnue(final Unite unite, final Composant c)
            throws EntiteInconnuePersistenceException {
        if (unite == null) {
            throw new EntiteInconnuePersistenceException(
                    String.format("Erreur: L'unité avec"
                            + " l'uuid %s est inconnue!",
                            c.getUnite().getIdentifiant().getUUID()));
        }
    }
}
