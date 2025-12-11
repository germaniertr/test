package recette.serialisation.json;

import core.domain.Audit;
import core.domain.AuditBase;
import core.domain.Identifiant;
import core.domain.IdentifiantBase;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Instant;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import recette.domain.Composant;
import recette.domain.ComposantBase;
import recette.domain.IngredientBase;
import recette.domain.Recette;
import recette.domain.RecetteBase;
import recette.domain.UniteBase;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class RecetteJsonTest {

    private Identifiant identifiantRef;
    private long versionRef;
    private Audit auditRef;
    private String nomRef;
    private String detailRef;
    private String preparationRef;
    private int nombrePersonneRef;
    private Composant composantRef1;
    private Composant composantRef2;
    private Composant composantRef3;
    private ArrayList<Object> composantsRef;
    private Recette entiteRef;
    private File fichierEntite;
    private File fichierEntiteVide;

    public RecetteJsonTest() {
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
                .ingredient(
                        IngredientBase.builder()
                                .identifiant(
                                        IdentifiantBase.builder()
                                                .build())
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
                        .identifiant(
                                IdentifiantBase.builder()
                                        .build())
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
                        .identifiant(
                                IdentifiantBase.builder()
                                        .build())
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
                .version(versionRef)
                .audit(auditRef)
                .nom(nomRef)
                .detail(detailRef)
                .preparation(preparationRef)
                .nombrePersonnes(nombrePersonneRef)
                .composant(composantRef1)
                .composant(composantRef2)
                .composant(composantRef3)
                .build();

        fichierEntite = new File("target/recette.json");
        fichierEntiteVide = new File("target/recette-vide.json");

    }

    @Test
    public void testSerialisationDeserialisation() throws IOException {
        //Sérialisation
        try (OutputStream os = new FileOutputStream(fichierEntite)) {
            JsonbConfig config = new JsonbConfig()
                    .withFormatting(true)
                    .withStrictIJSON(true);
            Jsonb jsonb = JsonbBuilder.create(config);
            jsonb.toJson(new RecetteJson(entiteRef), os);
        }

        //dé-sérialisation
        Recette entite;
        try (InputStream is = new FileInputStream(fichierEntite)) {
            JsonbConfig config = new JsonbConfig()
                    .withFormatting(true)
                    .withStrictIJSON(true);
            Jsonb jsonb = JsonbBuilder.create(config);
            entite = jsonb.fromJson(is,
                    RecetteJson.class);
        }

        Assertions.assertNotSame(this.entiteRef, entite);
        Assertions.assertEquals(this.entiteRef, entite);
        Assertions.assertEquals(this.entiteRef.hashCode(), entite.hashCode());

        Assertions.assertEquals(this.entiteRef.getIdentifiant(),
                entite.getIdentifiant());
        Assertions.assertEquals(entiteRef.getVersion(), entite.getVersion());
        testAudit(this.entiteRef.getAudit(), entite.getAudit());

        Assertions.assertEquals(this.entiteRef.getNom(),
                entite.getNom());
        Assertions.assertEquals(this.entiteRef.getDetail(),
                entite.getDetail());
        Assertions.assertEquals(this.entiteRef.getPreparation(),
                entite.getPreparation());
        Assertions.assertEquals(this.entiteRef.getNombrePersonnes(),
                entite.getNombrePersonnes());

        Assertions.assertEquals(this.entiteRef.getComposants().size(), entite.getComposants().size());
        for (int i = 0; i < this.entiteRef.getComposants().size(); i += 1) {
            Assertions.assertNotSame(entiteRef.getComposants().get(i),
                    entite.getComposants().get(i));

            Assertions.assertEquals(entiteRef.getComposants().get(i).getIngredient(),
                    entite.getComposants().get(i).getIngredient());
            Assertions.assertEquals(entiteRef.getComposants().get(i).getCommentaire(),
                    entite.getComposants().get(i).getCommentaire());
            Assertions.assertEquals(entiteRef.getComposants().get(i).getQuantite(),
                    entite.getComposants().get(i).getQuantite());
            Assertions.assertEquals(entiteRef.getComposants().get(i).getUnite(),
                    entite.getComposants().get(i).getUnite());
        }
    }
    
    @Test
    public void testSerialisationDeserialisationRecetteVide() throws IOException {
        //Sérialisation
        Recette recetteVide = RecetteBase.builder().build();
        try (OutputStream os = new FileOutputStream(fichierEntiteVide)) {
            JsonbConfig config = new JsonbConfig()
                    .withFormatting(true)
                    .withStrictIJSON(true);
            Jsonb jsonb = JsonbBuilder.create(config);
            jsonb.toJson(new RecetteJson(recetteVide), os);
        }

        //dé-sérialisation
        Recette entite;
        try (InputStream is = new FileInputStream(fichierEntiteVide)) {
            JsonbConfig config = new JsonbConfig()
                    .withFormatting(true)
                    .withStrictIJSON(true);
            Jsonb jsonb = JsonbBuilder.create(config);
            entite = jsonb.fromJson(is,
                    RecetteJson.class);
        }

        Assertions.assertNotSame(recetteVide, entite);
        Assertions.assertEquals(recetteVide, entite);

    }
    

    private void testAudit(Audit ref, Audit audit) {
        Assertions.assertEquals(ref, audit);
        Assertions.assertEquals(ref.getUserCreation(),
                audit.getUserCreation());
        Assertions.assertEquals(ref.getUserModification(),
                audit.getUserModification());
    }

}
