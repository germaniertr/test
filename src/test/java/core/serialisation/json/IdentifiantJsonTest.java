package core.serialisation.json;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import core.domain.Identifiant;
import core.domain.IdentifiantBase;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Dominique Huguenin (dominique.huguenin@rpn.ch)
 */
public class IdentifiantJsonTest {

    private Identifiant entiteRef;
    private String uuidRef;
    private File fichierEntite;

    public IdentifiantJsonTest() {
    }

    @BeforeEach
    public void setUp() {
        uuidRef = UUID.randomUUID().toString();
        entiteRef = IdentifiantBase.builder()
                .uuid(uuidRef)
                .build();

        fichierEntite
                = new File("target/identitifant.json");
    }

    @Test
    public void testSerialisationDeserialisation() throws IOException {
        //Sérialisation
        try (OutputStream os = new FileOutputStream(fichierEntite)) {
            JsonbConfig config = new JsonbConfig()
                    .withFormatting(true)
                    .withStrictIJSON(true);
            Jsonb jsonb = JsonbBuilder.create(config);
            jsonb.toJson(new IdentifiantJson(entiteRef), os);
        }

        //dé-sérialisation
        Identifiant entite;
        try (InputStream is = new FileInputStream(fichierEntite)) {
            JsonbConfig config = new JsonbConfig()
                    .withFormatting(true)
                    .withStrictIJSON(true);
            Jsonb jsonb = JsonbBuilder.create(config);
            entite = jsonb.fromJson(is,
                    IdentifiantJson.class);
        }

        Assertions.assertEquals(this.entiteRef.getUUID(),
                entite.getUUID());

    }
}