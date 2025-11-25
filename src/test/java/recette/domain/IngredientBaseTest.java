package recette.domain;

import core.domain.Identifiant;
import core.domain.IdentifiantBase;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class IngredientBaseTest {

    private static final Logger LOG = Logger.getLogger(IngredientBaseTest.class.getName());

    private IdentifiantBase identifiantRef;
    private String nomRef;
    private String detailRef;
    private Ingredient entiteRef;
    private Recette recetteRef;

    public IngredientBaseTest() {
    }

    @BeforeEach
    public void setUp() {
        identifiantRef = new IdentifiantBase();
        nomRef = "nom de référence";
        detailRef = "détail de référence";
        recetteRef = new RecetteBase(new IdentifiantBase());

        entiteRef = new IngredientBase(identifiantRef);
        this.entiteRef.setNom(nomRef);
        this.entiteRef.setDetail(detailRef);
        this.entiteRef.setRecette(recetteRef);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testToString() {
        //Ceci n'est pas un test!
        LOG.info(this.entiteRef.toString());
    }

    @Test
    public void testGetSet() {
        String nom = "nom modifié";
        String detail = "détail modifié";
        Recette recette = new RecetteBase(new IdentifiantBase());

        this.entiteRef.setNom(nom);
        this.entiteRef.setDetail(detail);
        this.entiteRef.setRecette(recette);

        Assertions.assertEquals(identifiantRef, entiteRef.getIdentifiant());
        Assertions.assertEquals(nom, entiteRef.getNom());
        Assertions.assertEquals(detail, entiteRef.getDetail());
        Assertions.assertEquals(recette, entiteRef.getRecette());

    }

    @Test
    public void testEquals() {
        Ingredient entite = new IngredientBase(identifiantRef);
        Assertions.assertNotSame(this.entiteRef, entite);
        Assertions.assertEquals(this.entiteRef, entite);
        Assertions.assertEquals(this.entiteRef.hashCode(), entite.hashCode());
    }

    @Test
    public void testEqualsSame() {
        Assertions.assertSame(this.entiteRef, this.entiteRef);
        Assertions.assertEquals(this.entiteRef, this.entiteRef);
        Assertions.assertEquals(this.entiteRef.hashCode(), entiteRef.hashCode());
    }

    @Test
    public void testEqualsNull() {
        Ingredient entite = null;

        Assertions.assertNotSame(this.entiteRef, entite);
        Assertions.assertNotEquals(this.entiteRef, entite);
    }

    @Test
    public void testEqualsObject() {
        Object entite = new Object();

        Assertions.assertNotSame(this.entiteRef, entite);
        Assertions.assertNotEquals(this.entiteRef, entite);
        Assertions.assertNotEquals(this.entiteRef.hashCode(), entite.hashCode());
    }

    @Test
    public void testUpdate() {
        String nom = "nom";
        String detail = "detail";
        Recette recette = new RecetteBase(new IdentifiantBase());

        Ingredient entite = new IngredientBase((Identifiant) null);
        entite.setNom(nom);
        entite.setDetail(detail);
        entite.setRecette(recette);
        this.entiteRef.update(entite);

        Assertions.assertEquals(identifiantRef, entiteRef.getIdentifiant());
        Assertions.assertEquals(nom, entiteRef.getNom());
        Assertions.assertEquals(detail, entiteRef.getDetail());
        Assertions.assertEquals(recette, entiteRef.getRecette());
    }

    @Test
    public void testUpdateNull() {
        this.entiteRef.update(null);

        Assertions.assertEquals(identifiantRef, entiteRef.getIdentifiant());
        Assertions.assertEquals(nomRef, entiteRef.getNom());
        Assertions.assertEquals(detailRef, entiteRef.getDetail());
    }

    @Test
    public void testClone() {
        Ingredient entite = new IngredientBase(entiteRef);
        Assertions.assertNotSame(this.entiteRef, entite);
        Assertions.assertEquals(this.entiteRef, entite);
        Assertions.assertEquals(this.entiteRef.hashCode(), entite.hashCode());

        Assertions.assertEquals(this.entiteRef.getIdentifiant(), entite.getIdentifiant());

        Assertions.assertEquals(this.entiteRef.getNom(), entite.getNom());
        Assertions.assertEquals(this.entiteRef.getDetail(), entite.getDetail());
        Assertions.assertEquals(this.entiteRef.getRecette(), entite.getRecette());

    }

    @Test
    public void testCloneIllegalArgument() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> {
                    new IngredientBase((Ingredient) null);
                });
    }
}
