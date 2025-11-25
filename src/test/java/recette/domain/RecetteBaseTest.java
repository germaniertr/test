package recette.domain;

import core.domain.IdentifiantBase;
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
    private List<Composant> composantsRef;

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

        Assertions.assertEquals(composantsRef.size(), this.entiteRef.getComposants().size());
        for (int i = 0; i < composantsRef.size(); i += 1) {
            Assertions.assertEquals(composantsRef.get(i).getIngredient(),
                    this.entiteRef.getComposants().get(i).getIngredient());
            Assertions.assertEquals(composantsRef.get(i).getCommentaire(),
                    this.entiteRef.getComposants().get(i).getCommentaire());
            Assertions.assertEquals(composantsRef.get(i).getQuantite(),
                    this.entiteRef.getComposants().get(i).getQuantite());
            Assertions.assertEquals(composantsRef.get(i).getUnite(),
                    this.entiteRef.getComposants().get(i).getUnite());
        }

    }

    @Test
    public void testSet() {
        String nom = "nouveau nom";
        String detail = "nouveau détail";
        String preparation = "nouvelle preparation";
        Integer nombrePersonne = 40;

        Composant composant2 = new ComposantBase(composantRef2.getIdentifiant(),
                composantRef2.getIngredient());
        composant2.setCommentaire(composantRef2.getCommentaire());
        composant2.setQuantite(composantRef2.getQuantite());
        composant2.setUnite(composantRef2.getUnite());

        composant2.setCommentaire("Composant modifié");
        composant2.setQuantite(34.6);
        composant2.setUnite(new UniteBase(new IdentifiantBase(UUID.randomUUID().toString())));

        Composant composant4 = new ComposantBase(new IdentifiantBase(UUID.randomUUID().toString()),
                new IngredientBase(new IdentifiantBase(UUID.randomUUID().toString())));
        composant4.setCommentaire("commentaire 4");
        composant4.setQuantite(45.7);
        composant4.setUnite(new UniteBase(new IdentifiantBase(UUID.randomUUID().toString())));;

        List<Composant> composants = new ArrayList<>();
        composants.add(composant2);
        composants.add(composant4);

        this.entiteRef.setNom(nom);
        this.entiteRef.setDetail(detail);
        this.entiteRef.setPreparation(preparation);
        this.entiteRef.setNombrePersonnes(nombrePersonne);

        this.entiteRef.getComposants().get(1).update(composant2);
        this.entiteRef.getComposants().remove(0);
        this.entiteRef.getComposants().remove(1);
        this.entiteRef.getComposants().add(composant4);

        Assertions.assertEquals(identifiantRef, this.entiteRef.getIdentifiant());
        Assertions.assertEquals(nom, this.entiteRef.getNom());
        Assertions.assertEquals(detail, this.entiteRef.getDetail());
        Assertions.assertEquals(preparation, this.entiteRef.getPreparation());
        Assertions.assertEquals(nombrePersonne, this.entiteRef.getNombrePersonnes());

        for (int i = 0; i < composants.size(); i += 1) {
            Assertions.assertEquals(composants.get(i).getIngredient(),
                    this.entiteRef.getComposants().get(i).getIngredient());
            Assertions.assertEquals(composants.get(i).getCommentaire(),
                    this.entiteRef.getComposants().get(i).getCommentaire());
            Assertions.assertEquals(composants.get(i).getQuantite(),
                    this.entiteRef.getComposants().get(i).getQuantite());
            Assertions.assertEquals(composants.get(i).getUnite(),
                    this.entiteRef.getComposants().get(i).getUnite());
        }
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

        Composant composant2 = new ComposantBase(composantRef2.getIdentifiant(),
                composantRef2.getIngredient());
        composant2.setCommentaire(composantRef2.getCommentaire());
        composant2.setQuantite(composantRef2.getQuantite());
        composant2.setUnite(composantRef2.getUnite());

        composant2.setCommentaire("Composant modifié");
        composant2.setQuantite(34.6);
        composant2.setUnite(new UniteBase(new IdentifiantBase(UUID.randomUUID().toString())));

        Composant composant4 = new ComposantBase(new IdentifiantBase(UUID.randomUUID().toString()),
                new IngredientBase(new IdentifiantBase(UUID.randomUUID().toString())));
        composant4.setCommentaire("commentaire 4");
        composant4.setQuantite(45.7);
        composant4.setUnite(new UniteBase(new IdentifiantBase(UUID.randomUUID().toString())));;

        List<Composant> composants = new ArrayList<>();
        composants.add(composant2);
        composants.add(composant4);

        Recette entite = new RecetteBase(identifiantRef);
        entite.setNom(nom);
        entite.setDetail(detail);
        entite.setPreparation(preparation);
        entite.setNombrePersonnes(nombrePersonne);

        entite.getComposants().add(composant2);
        entite.getComposants().add(composant4);

        this.entiteRef.update(entite);

        Assertions.assertEquals(identifiantRef, this.entiteRef.getIdentifiant());
        Assertions.assertEquals(nom, this.entiteRef.getNom());
        Assertions.assertEquals(detail, this.entiteRef.getDetail());
        Assertions.assertEquals(preparation, this.entiteRef.getPreparation());
        Assertions.assertEquals(nombrePersonne, this.entiteRef.getNombrePersonnes());

        Assertions.assertEquals(composants.size(), this.entiteRef.getComposants().size());
        for (int i = 0; i < composants.size(); i += 1) {
            Assertions.assertNotSame(composants.get(i),
                    this.entiteRef.getComposants().get(i));
            
            Assertions.assertEquals(composants.get(i).getIngredient(),
                    this.entiteRef.getComposants().get(i).getIngredient());
            Assertions.assertEquals(composants.get(i).getCommentaire(),
                    this.entiteRef.getComposants().get(i).getCommentaire());
            Assertions.assertEquals(composants.get(i).getQuantite(),
                    this.entiteRef.getComposants().get(i).getQuantite());
            Assertions.assertEquals(composants.get(i).getUnite(),
                    this.entiteRef.getComposants().get(i).getUnite());
        }

    }

    @Test
    public void testUpdateNull() {
        this.entiteRef.update(null);

        Assertions.assertEquals(identifiantRef, this.entiteRef.getIdentifiant());
        Assertions.assertEquals(nomRef, this.entiteRef.getNom());
        Assertions.assertEquals(detailRef, this.entiteRef.getDetail());
        Assertions.assertEquals(preparationRef, this.entiteRef.getPreparation());
        Assertions.assertEquals(nombrePersonneRef, this.entiteRef.getNombrePersonnes());

        Assertions.assertEquals(composantsRef.size(), this.entiteRef.getComposants().size());
        for (int i = 0; i < composantsRef.size(); i += 1) {
            Assertions.assertEquals(composantsRef.get(i).getIngredient(),
                    this.entiteRef.getComposants().get(i).getIngredient());
            Assertions.assertEquals(composantsRef.get(i).getCommentaire(),
                    this.entiteRef.getComposants().get(i).getCommentaire());
            Assertions.assertEquals(composantsRef.get(i).getQuantite(),
                    this.entiteRef.getComposants().get(i).getQuantite());
            Assertions.assertEquals(composantsRef.get(i).getUnite(),
                    this.entiteRef.getComposants().get(i).getUnite());
        }

    }

}
