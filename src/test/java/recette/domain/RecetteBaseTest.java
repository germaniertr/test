package recette.domain;

import core.domain.Audit;
import core.domain.AuditBase;
import core.domain.Identifiant;
import core.domain.IdentifiantBase;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
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

    private Identifiant identifiantRef;
    private String nomRef;
    private String detailRef;
    private String preparationRef;
    private Integer nombrePersonneRef;
    private Recette entiteRef;
    private Composant composantRef1;
    private Composant composantRef2;
    private Composant composantRef3;
    private List<Composant> composantsRef;
    private Long versionRef;
    private Audit auditRef;

    public RecetteBaseTest() {
    }

    @BeforeEach
    public void setUp() {
        identifiantRef = IdentifiantBase.builder().build();
        versionRef = 123L;
        auditRef = AuditBase.builder()
                .dateCreation(Instant.now())
                .userCreation("user creation")
                .dateModification(Instant.now().plusSeconds(60))
                .userModification("user modification")
                .build();

        nomRef = "nom recette";
        detailRef = "description recette";
        preparationRef = "preparation recette";
        nombrePersonneRef = 4;

        composantRef1 = ComposantBase.builder()
                .identifiant(IdentifiantBase.builder().build())
                .ingredient(IngredientBase.builder()
                        .identifiant(IdentifiantBase.builder().build())
                        .build())
                .commentaire("commentaire 1")
                .quantite(123.0)
                .unite(UniteBase.builder()
                        .identifiant(IdentifiantBase.builder().build())
                        .build())
                .build();

        composantRef2 = ComposantBase.builder()
                .identifiant(IdentifiantBase.builder().build())
                .ingredient(IngredientBase.builder()
                        .identifiant(IdentifiantBase.builder().build())
                        .build())
                .commentaire("commentaire 2")
                .quantite(34.6)
                .unite(UniteBase.builder()
                        .identifiant(IdentifiantBase.builder().build())
                        .build())
                .build();

        composantRef3 = ComposantBase.builder()
                .identifiant(IdentifiantBase.builder().build())
                .ingredient(IngredientBase.builder()
                        .identifiant(IdentifiantBase.builder().build())
                        .build())
                .commentaire("commentaire 3")
                .quantite(45.7)
                .unite(UniteBase.builder()
                        .identifiant(IdentifiantBase.builder().build())
                        .build())
                .build();

        composantsRef = new ArrayList<>();
        composantsRef.add(composantRef1);
        composantsRef.add(composantRef2);
        composantsRef.add(composantRef3);

        entiteRef = RecetteBase.builder()
                .identifiant(identifiantRef)
                .audit(auditRef)
                .version(versionRef)
                .nom(nomRef)
                .detail(detailRef)
                .preparation(preparationRef)
                .nombrePersonnes(nombrePersonneRef)
                .composant(composantRef1)
                .composant(composantRef2)
                .composant(composantRef3)
                .build();
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
        Assertions.assertEquals(versionRef, this.entiteRef.getVersion());
        testAudit(this.auditRef, this.entiteRef.getAudit());

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

    private void testAudit(Audit ref, Audit audit) {
        Assertions.assertEquals(ref, audit);
        Assertions.assertEquals(ref.getUserCreation(),
                audit.getUserCreation());
        Assertions.assertEquals(ref.getUserModification(),
                audit.getUserModification());
    }

    @Test
    public void testSet() {
        String nom = "nouveau nom";
        String detail = "nouveau détail";
        String preparation = "nouvelle preparation";
        Integer nombrePersonne = 40;

        Composant composant2 = ComposantBase.builder()
                .composant(this.entiteRef.getComposants().get(1))
                .commentaire("Composant modifié")
                .quantite(34.6)
                .unite(UniteBase.builder()
                        .identifiant(IdentifiantBase.builder().build())
                        .build())
                .build();

        Composant composant4 = ComposantBase.builder()
                .identifiant(IdentifiantBase.builder().build())
                .ingredient(IngredientBase.builder()
                        .identifiant(IdentifiantBase.builder().build())
                        .build())
                .commentaire("commentaire 4")
                .quantite(45.7)
                .unite(UniteBase.builder()
                        .identifiant(IdentifiantBase.builder().build())
                        .build())
                .build();

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
        Assertions.assertEquals(versionRef, this.entiteRef.getVersion());
        testAudit(this.auditRef, this.entiteRef.getAudit());

        Assertions.assertEquals(nom, this.entiteRef.getNom());
        Assertions.assertEquals(detail, this.entiteRef.getDetail());
        Assertions.assertEquals(preparation, this.entiteRef.getPreparation());
        Assertions.assertEquals(nombrePersonne, this.entiteRef.getNombrePersonnes());

        Assertions.assertEquals(composants.size(), this.entiteRef.getComposants().size());
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
        Recette entite = RecetteBase.builder()
                .identifiant(identifiantRef)
                .build();

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

        Composant composant2 = ComposantBase.builder()
                .identifiant(IdentifiantBase.builder().build())
                .ingredient(IngredientBase.builder()
                        .identifiant(IdentifiantBase.builder().build())
                        .build())
                .commentaire("Composant modifié")
                .quantite(34.6)
                .unite(UniteBase.builder()
                        .identifiant(IdentifiantBase.builder().build())
                        .build())
                .build();

        Composant composant4 = ComposantBase.builder()
                .identifiant(IdentifiantBase.builder().build())
                .ingredient(IngredientBase.builder()
                        .identifiant(IdentifiantBase.builder().build())
                        .build())
                .commentaire("commentaire 4")
                .quantite(45.7)
                .unite(UniteBase.builder()
                        .identifiant(IdentifiantBase.builder().build())
                        .build())
                .build();

        List<Composant> composants = new ArrayList<>();
        composants.add(composant2);
        composants.add(composant4);

        Recette entite = RecetteBase.builder()
                .identifiant(identifiantRef)
                .nom(nom)
                .detail(detail)
                .preparation(preparation)
                .nombrePersonnes(nombrePersonne)
                .composant(composant2)
                .composant(composant4)
                .build();

        this.entiteRef.update(entite);

        Assertions.assertEquals(identifiantRef, this.entiteRef.getIdentifiant());
        Assertions.assertEquals(versionRef, this.entiteRef.getVersion());
        testAudit(this.auditRef, this.entiteRef.getAudit());

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

    @Test
    public void testClone() {
        Recette entite = RecetteBase.builder()
                .recette(entiteRef)
                .build();

        Assertions.assertNotSame(this.entiteRef, entite);
        Assertions.assertEquals(this.entiteRef, entite);
        Assertions.assertEquals(this.entiteRef.hashCode(), entite.hashCode());

        Assertions.assertEquals(this.entiteRef.getIdentifiant(),
                entite.getIdentifiant());
        Assertions.assertEquals(this.entiteRef.getVersion(),
                entite.getVersion());
        testAudit(this.auditRef, this.entiteRef.getAudit());

        Assertions.assertEquals(this.entiteRef.getNom(),
                entite.getNom());
        Assertions.assertEquals(this.entiteRef.getDetail(),
                entite.getDetail());
        Assertions.assertEquals(this.entiteRef.getPreparation(),
                entite.getPreparation());
        Assertions.assertEquals(this.entiteRef.getNombrePersonnes(),
                entite.getNombrePersonnes());

        for (int i = 0; i < composantsRef.size(); i += 1) {
            Assertions.assertNotSame(composantsRef.get(i),
                    entite.getComposants().get(i));

            Assertions.assertEquals(composantsRef.get(i).getIngredient(),
                    entite.getComposants().get(i).getIngredient());
            Assertions.assertEquals(composantsRef.get(i).getCommentaire(),
                    entite.getComposants().get(i).getCommentaire());
            Assertions.assertEquals(composantsRef.get(i).getQuantite(),
                    entite.getComposants().get(i).getQuantite());
            Assertions.assertEquals(composantsRef.get(i).getUnite(),
                    entite.getComposants().get(i).getUnite());
        }
    }

    @Test
    public void testCloneIllegalArgument() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> {
                    RecetteBase.builder().recette((Recette) null).build();
                });
    }
}
