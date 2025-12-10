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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import recette.domain.Composant;
import recette.domain.ComposantBase;
import recette.domain.Ingredient;
import recette.domain.IngredientBase;
import recette.domain.Unite;
import recette.domain.UniteBase;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class ComposantJsonTest {

    private Identifiant identifiantRef;
    private long versionRef;
    private Audit auditRef;
    private double quantiteRef;
    private String commentaireRef;
    private Ingredient ingredientRef;
    private Unite uniteRef;
    private Composant entiteRef;
    private File fichierEntite;

    public ComposantJsonTest() {
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

        quantiteRef = 2.5;
        commentaireRef = "commentaire";

        ingredientRef
                = IngredientBase.builder()
                        .identifiant(
                                IdentifiantBase.builder()
                                        .build())
                        .build();

        ingredientRef.setNom("nom ingrédient");
        ingredientRef.setDetail("description ingrédient");

        uniteRef = UniteBase.builder()
                .identifiant(IdentifiantBase.builder().build())
                .code("c.c")
                .build();

        entiteRef = ComposantBase.builder()
                .identifiant(identifiantRef)
                .version(versionRef)
                .audit(auditRef)
                .ingredient(ingredientRef)
                .commentaire(commentaireRef)
                .quantite(quantiteRef)
                .unite(uniteRef)
                .build();

        fichierEntite = new File("target/composant.json");

    }

    @Test
    public void testSerialisationDeserialisation() throws IOException {
        //Sérialisation
        try (OutputStream os = new FileOutputStream(fichierEntite)) {
            JsonbConfig config = new JsonbConfig()
                    .withFormatting(true)
                    .withStrictIJSON(true);
            Jsonb jsonb = JsonbBuilder.create(config);
            jsonb.toJson(new ComposantJson(entiteRef), os);
        }

        //dé-sérialisation
        Composant entite;
        try (InputStream is = new FileInputStream(fichierEntite)) {
            JsonbConfig config = new JsonbConfig()
                    .withFormatting(true)
                    .withStrictIJSON(true);
            Jsonb jsonb = JsonbBuilder.create(config);
            entite = jsonb.fromJson(is,
                    ComposantJson.class);
        }

        Assertions.assertNotSame(this.entiteRef, entite);
        Assertions.assertEquals(this.entiteRef, entite);
        Assertions.assertEquals(this.entiteRef.hashCode(), entite.hashCode());

        Assertions.assertEquals(this.entiteRef.getIdentifiant(), entite.getIdentifiant());
        Assertions.assertEquals(this.entiteRef.getVersion(), entite.getVersion());
        testAudit(this.entiteRef.getAudit(), entite.getAudit());

        Assertions.assertEquals(this.entiteRef.getQuantite(), entite.getQuantite());
        Assertions.assertEquals(this.entiteRef.getCommentaire(), entite.getCommentaire());
        Assertions.assertEquals(this.entiteRef.getIngredient(), entite.getIngredient());
        Assertions.assertEquals(this.entiteRef.getUnite(), entite.getUnite());

    }

    private void testAudit(Audit ref, Audit audit) {
        Assertions.assertEquals(ref, audit);
        Assertions.assertEquals(ref.getUserCreation(),
                audit.getUserCreation());
        Assertions.assertEquals(ref.getUserModification(),
                audit.getUserModification());
    }

}
