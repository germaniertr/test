package recette.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class RecetteBaseTest {

    private static final Logger LOG = Logger.getLogger(UniteBaseTest.class.getName());

    private IdentifiantBase identifiantRef;
    private String nomRef;
    private String detailRef;
    private String preparationRef;
    private Integer nombrePersonneRef;
    private Recette entiteRef;
    private Composant composantRef1;
    private Composant composantRef2;
    private Composant composantRef3;
    private List<Object> composantsRef;

    public RecetteBaseTest() {
    }

    @BeforeEach
    public void setUp() {
        identifiantRef = new IdentifiantBase(UUID.randomUUID().toString());
        nomRef = "nom recette";
        detailRef = "description recette";
        preparationRef = "preparation recette";
        nombrePersonneRef = 4;

        composantRef1 = new ComposantBase(new IdentifiantBase(UUID.randomUUID().toString()),
                new IngredientBase(new IdentifiantBase(UUID.randomUUID().toString())));
        composantRef1.setCommentaire("commentaire 1");
        composantRef1.setQuantite(123.0);
        composantRef1.setUnite(new UniteBase(new IdentifiantBase(UUID.randomUUID().toString())));

        composantRef2 = new ComposantBase(new IdentifiantBase(UUID.randomUUID().toString()),
                new IngredientBase(new IdentifiantBase(UUID.randomUUID().toString())));
        composantRef2.setCommentaire("commentaire 2");
        composantRef2.setQuantite(34.6);
        composantRef2.setUnite(new UniteBase(new IdentifiantBase(UUID.randomUUID().toString())));

        composantRef3 = new ComposantBase(new IdentifiantBase(UUID.randomUUID().toString()),
                new IngredientBase(new IdentifiantBase(UUID.randomUUID().toString())));
        composantRef3.setCommentaire("commentaire 3");
        composantRef3.setQuantite(45.7);
        composantRef3.setUnite(new UniteBase(new IdentifiantBase(UUID.randomUUID().toString())));

        composantsRef = new ArrayList<>();
        composantsRef.add(composantRef1);
        composantsRef.add(composantRef2);
        composantsRef.add(composantRef3);

        entiteRef = new RecetteBase(identifiantRef);
        entiteRef.setNom(nomRef);
        entiteRef.setDetail(detailRef);
        entiteRef.setPreparation(preparationRef);
        entiteRef.setNombrePersonnes(nombrePersonneRef);

        entiteRef.getComposants().add(composantRef1);
        entiteRef.getComposants().add(composantRef2);
        entiteRef.getComposants().add(composantRef3);

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
    public void testGet() {
        Assertions.assertEquals(identifiantRef, this.entiteRef.getIdentifiant());
        Assertions.assertEquals(nomRef, this.entiteRef.getNom());
        Assertions.assertEquals(detailRef, this.entiteRef.getDetail());
        Assertions.assertEquals(preparationRef, this.entiteRef.getPreparation());
        Assertions.assertEquals(nombrePersonneRef, this.entiteRef.getNombrePersonnes());
    }

    @Test
    public void testSet() {
        String nom = "nouveau nom";
        String detail = "nouveau détail";
        String preparation = "nouvelle preparation";
        Integer nombrePersonne = 40;

        this.entiteRef.setNom(nom);
        this.entiteRef.setDetail(detail);
        this.entiteRef.setPreparation(preparation);
        this.entiteRef.setNombrePersonnes(nombrePersonne);

        Assertions.assertEquals(identifiantRef, this.entiteRef.getIdentifiant());
        Assertions.assertEquals(nom, this.entiteRef.getNom());
        Assertions.assertEquals(detail, this.entiteRef.getDetail());
        Assertions.assertEquals(preparation, this.entiteRef.getPreparation());
        Assertions.assertEquals(nombrePersonne, this.entiteRef.getNombrePersonnes());

    }

    @Test
    public void testEquals() {
        Recette entite = new RecetteBase(identifiantRef);
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
        Recette entite = null;

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
        String nom = "nouveau nom";
        String detail = "nouveau détail";
        String preparation = "nouvelle preparation";
        Integer nombrePersonne = 40;

        Recette entite = new RecetteBase(identifiantRef);
        entite.setNom(nom);
        entite.setDetail(detail);
        entite.setPreparation(preparation);
        entite.setNombrePersonnes(nombrePersonne);
        this.entiteRef.update(entite);

        Assertions.assertEquals(identifiantRef, this.entiteRef.getIdentifiant());
        Assertions.assertEquals(nom, this.entiteRef.getNom());
        Assertions.assertEquals(detail, this.entiteRef.getDetail());
        Assertions.assertEquals(preparation, this.entiteRef.getPreparation());
        Assertions.assertEquals(nombrePersonne, this.entiteRef.getNombrePersonnes());
    }

    @Test
    public void testUpdateNull() {
        this.entiteRef.update(null);

        Assertions.assertEquals(identifiantRef, this.entiteRef.getIdentifiant());
        Assertions.assertEquals(nomRef, this.entiteRef.getNom());
        Assertions.assertEquals(detailRef, this.entiteRef.getDetail());
        Assertions.assertEquals(preparationRef, this.entiteRef.getPreparation());
        Assertions.assertEquals(nombrePersonneRef, this.entiteRef.getNombrePersonnes());
    }

}
