package recette.domain;

import core.domain.IdentifiantBase;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class ComposantBaseTest {

    private IdentifiantBase identifiantRef;
    private Double quantiteRef;
    private String commentaireRef;
    private Ingredient ingredientRef;
    private Unite uniteRef;
    private Composant entiteRef;

    public ComposantBaseTest() {
    }

    @BeforeEach
    public void setUp() {
        identifiantRef = new IdentifiantBase();
        quantiteRef = 2.5;
        commentaireRef = "commentaire";

        ingredientRef
                = new IngredientBase(new IdentifiantBase());

        ingredientRef.setNom("nom ingrédient");
        ingredientRef.setDetail("description ingrédient");

        uniteRef = new UniteBase(new IdentifiantBase());
        uniteRef.setCode("c.c");

        entiteRef = new ComposantBase(identifiantRef, ingredientRef);
        entiteRef.setCommentaire(commentaireRef);
        entiteRef.setQuantite(quantiteRef);
        entiteRef.setUnite(uniteRef);

    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGet() {
        Assertions.assertEquals(identifiantRef, entiteRef.getIdentifiant());
        Assertions.assertEquals(this.quantiteRef, this.entiteRef.getQuantite());
        Assertions.assertEquals(this.commentaireRef, this.entiteRef.getCommentaire());
        Assertions.assertEquals(this.ingredientRef, this.entiteRef.getIngredient());
        Assertions.assertEquals(this.uniteRef, this.entiteRef.getUnite());
    }

    @Test
    public void testSet() {
        Double quantite = 2500.0;
        String commentaire = "nouveau commentaire";

        Unite unite = new UniteBase(new IdentifiantBase());
        unite.setCode("g");

        this.entiteRef.setCommentaire(commentaire);
        this.entiteRef.setQuantite(quantite);
        this.entiteRef.setUnite(unite);

        Assertions.assertEquals(quantite, this.entiteRef.getQuantite());
        Assertions.assertEquals(commentaire, this.entiteRef.getCommentaire());
        Assertions.assertEquals(this.ingredientRef, this.entiteRef.getIngredient());
        Assertions.assertEquals(unite, this.entiteRef.getUnite());
    }

    @Test
    public void testEquals() {
        Composant entite = new ComposantBase(identifiantRef, ingredientRef);
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
        Composant entite = null;

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
        Double quantite = 2500.0;
        String commentaire = "nouveau commentaire";

        Unite unite = new UniteBase(new IdentifiantBase());
        unite.setCode("g");

        Composant entite = new ComposantBase(null, null);
        entite.setCommentaire(commentaire);
        entite.setQuantite(quantite);
        entite.setUnite(unite);

        this.entiteRef.update(entite);

        Assertions.assertEquals(identifiantRef, this.entiteRef.getIdentifiant());
        Assertions.assertEquals(quantite, this.entiteRef.getQuantite());
        Assertions.assertEquals(commentaire, this.entiteRef.getCommentaire());
        Assertions.assertEquals(this.ingredientRef, this.entiteRef.getIngredient());
        Assertions.assertEquals(unite, this.entiteRef.getUnite());
    }

    @Test
    public void testUpdateNull() {
        this.entiteRef.update(null);

        Assertions.assertEquals(identifiantRef, entiteRef.getIdentifiant());
        Assertions.assertEquals(this.quantiteRef, this.entiteRef.getQuantite());
        Assertions.assertEquals(this.commentaireRef, this.entiteRef.getCommentaire());
        Assertions.assertEquals(this.ingredientRef, this.entiteRef.getIngredient());
        Assertions.assertEquals(this.uniteRef, this.entiteRef.getUnite());
    }
}
