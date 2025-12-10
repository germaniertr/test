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
import recette.domain.Unite;
import recette.domain.UniteBase;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class UniteJsonTest {

    private File fichierEntite;
    private Identifiant identifiantRef;
    private long versionRef;
    private Audit auditRef;
    private String codeRef;
    private Unite entiteRef;

    public UniteJsonTest() {
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
        codeRef = "code référence";
        entiteRef = UniteBase.builder()
                .identifiant(identifiantRef)
                .version(versionRef)
                .audit(auditRef)
                .code(codeRef)
                .build();

        fichierEntite = new File("target/unite.json");

    }

    @Test
    public void testSerialisationDeserialisation() throws IOException {
        //Sérialisation
        try (OutputStream os = new FileOutputStream(fichierEntite)) {
            JsonbConfig config = new JsonbConfig()
                    .withFormatting(true)
                    .withStrictIJSON(true);
            Jsonb jsonb = JsonbBuilder.create(config);
            jsonb.toJson(new UniteJson(entiteRef), os);
        }

        //dé-sérialisation
        Unite entite;
        try (InputStream is = new FileInputStream(fichierEntite)) {
            JsonbConfig config = new JsonbConfig()
                    .withFormatting(true)
                    .withStrictIJSON(true);
            Jsonb jsonb = JsonbBuilder.create(config);
            entite = jsonb.fromJson(is,
                    UniteJson.class);
        }

        Assertions.assertNotSame(this.entiteRef, entite);
        Assertions.assertEquals(this.entiteRef, entite);
        Assertions.assertEquals(this.entiteRef.hashCode(), entite.hashCode());

        Assertions.assertEquals(this.entiteRef.getIdentifiant(), entite.getIdentifiant());
        Assertions.assertEquals(entiteRef.getVersion(), entite.getVersion());
        testAudit(this.entiteRef.getAudit(), entite.getAudit());

        Assertions.assertEquals(this.entiteRef.getCode(), entite.getCode());

    }

    private void testAudit(Audit ref, Audit audit) {
        Assertions.assertEquals(ref, audit);
        Assertions.assertEquals(ref.getUserCreation(),
                audit.getUserCreation());
        Assertions.assertEquals(ref.getUserModification(),
                audit.getUserModification());
    }

}
