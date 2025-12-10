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
import recette.datasource.RecetteRef;
import recette.domain.Ingredient;
import recette.domain.IngredientBase;
import recette.domain.Recette;
import recette.domain.RecetteBase;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class IngredientJsonTest {

    private Identifiant identifiantRef;
    private long versionRef;
    private Audit auditRef;
    private String nomRef;
    private String detailRef;
    private Recette recetteRef;
    private Ingredient entiteRef;
    private File fichierEntite;

    public IngredientJsonTest() {
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

        nomRef = "nom de référence";
        detailRef = "détail de référence";

        recetteRef = new RecetteRef(RecetteBase.builder()
                .identifiant(IdentifiantBase.builder().build())
                .build());

        entiteRef = IngredientBase.builder()
                .identifiant(identifiantRef)
                .version(versionRef)
                .audit(auditRef)
                .nom(nomRef)
                .detail(detailRef)
                .recette(recetteRef)
                .build();

        fichierEntite = new File("target/ingredient.json");

    }

    @Test
    public void testSerialisationDeserialisation() throws IOException {
        //Sérialisation
        try (OutputStream os = new FileOutputStream(fichierEntite)) {
            JsonbConfig config = new JsonbConfig()
                    .withFormatting(true)
                    .withStrictIJSON(true);
            Jsonb jsonb = JsonbBuilder.create(config);
            jsonb.toJson(new IngredientJson(entiteRef), os);
        }

        //dé-sérialisation
        Ingredient entite;
        try (InputStream is = new FileInputStream(fichierEntite)) {
            JsonbConfig config = new JsonbConfig()
                    .withFormatting(true)
                    .withStrictIJSON(true);
            Jsonb jsonb = JsonbBuilder.create(config);
            entite = jsonb.fromJson(is,
                    IngredientJson.class);
        }

        Assertions.assertNotSame(this.entiteRef, entite);
        Assertions.assertEquals(this.entiteRef, entite);
        Assertions.assertEquals(this.entiteRef.hashCode(), entite.hashCode());

        Assertions.assertEquals(this.entiteRef.getIdentifiant(), entite.getIdentifiant());
        Assertions.assertEquals(this.entiteRef.getVersion(), entite.getVersion());
        testAudit(this.entiteRef.getAudit(), entite.getAudit());

        Assertions.assertEquals(this.entiteRef.getNom(), entite.getNom());
        Assertions.assertEquals(this.entiteRef.getDetail(), entite.getDetail());
        Assertions.assertEquals(this.entiteRef.getRecette().getIdentifiant(), entite.getRecette().getIdentifiant());        
        Assertions.assertEquals(this.entiteRef.getRecette(), entite.getRecette());

    }

    private void testAudit(Audit ref, Audit audit) {
        Assertions.assertEquals(ref, audit);
        Assertions.assertEquals(ref.getUserCreation(),
                audit.getUserCreation());
        Assertions.assertEquals(ref.getUserModification(),
                audit.getUserModification());
    }

}
