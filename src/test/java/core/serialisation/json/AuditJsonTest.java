package core.serialisation.json;

import core.domain.Audit;
import core.domain.AuditBase;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Instant;

/**
 *
 * @author Dominique Huguenin (dominique.huguenin@rpn.ch)
 */
public class AuditJsonTest {

    private File fichierEntite;
    private Instant dateCreationRef;
    private String userCreationRef;
    private Instant dateModificationRef;
    private String userModificationRef;
    private Audit entiteRef;

    public AuditJsonTest() {
    }

    @BeforeEach
    public void setUp() {
        dateCreationRef = Instant.MIN;
        userCreationRef = "user creation";
        dateModificationRef = Instant.now();
        userModificationRef = "user modification";

        entiteRef = AuditBase.builder()
                .dateCreation(this.dateCreationRef)
                .userCreation(this.userCreationRef)
                .dateModification(this.dateModificationRef)
                .userModification(this.userModificationRef)
                .build();
        fichierEntite
                = new File("target/audit.json");
    }

    @Test
    public void testSerialisationDeserialisation() throws IOException {
        //Sérialisation
        try (OutputStream os = new FileOutputStream(fichierEntite)) {
            JsonbConfig config = new JsonbConfig()
                    .withFormatting(true)
                    .withStrictIJSON(true);
            Jsonb jsonb = JsonbBuilder.create(config);
            jsonb.toJson(new AuditJson(entiteRef), os);
        }

        //dé-sérialisation
        Audit entite;
        try (InputStream is = new FileInputStream(fichierEntite)) {
            JsonbConfig config = new JsonbConfig()
                    .withFormatting(true)
                    .withStrictIJSON(true);
            Jsonb jsonb = JsonbBuilder.create(config);
            entite = jsonb.fromJson(is,
                    AuditJson.class);
        }

        Assertions.assertEquals(this.dateCreationRef,
                entite.getDateCreation());
        Assertions.assertEquals(this.userCreationRef,
                entite.getUserCreation());
        Assertions.assertEquals(this.dateModificationRef,
                entite.getDateModification());
        Assertions.assertEquals(this.userModificationRef,
                entite.getUserModification());

    }
}
