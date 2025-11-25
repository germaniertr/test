package recette.domain;

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
        identifiantRef = new IdentifiantBase(UUID.randomUUID().toString());
        quantiteRef = 2.5;
        commentaireRef = "commentaire";

        ingredientRef
                = new IngredientBase(new IdentifiantBase(UUID.randomUUID().toString()));

        ingredientRef.setNom("nom ingrédient");
        ingredientRef.setDetail("description ingrédient");

        uniteRef = new UniteBase(new IdentifiantBase(UUID.randomUUID().toString()));
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

        Unite unite = new UniteBase(new IdentifiantBase(UUID.randomUUID().toString()));
        unite.setCode("g");

        this.entiteRef.setCommentaire(commentaire);
        this.entiteRef.setQuantite(quantite);
        this.entiteRef.setUnite(unite);

        Assertions.assertEquals(quantite, this.entiteRef.getQuantite());
        Assertions.assertEquals(commentaire, this.entiteRef.getCommentaire());
        Assertions.assertEquals(this.ingredientRef, this.entiteRef.getIngredient());
        Assertions.assertEquals(unite, this.entiteRef.getUnite());
    }

}
