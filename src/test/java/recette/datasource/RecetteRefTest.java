package recette.datasource;

import core.domain.Identifiant;
import core.domain.IdentifiantBase;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import recette.domain.Recette;
import recette.domain.RecetteBase;
import recette.domain.UniteBaseTest;

/**
 *
 * @author dom
 */
public class RecetteRefTest {

    private static final Logger LOG = Logger.getLogger(UniteBaseTest.class.getName());

    private Identifiant identifiantRef;
    private Recette entiteRef;

    public RecetteRefTest() {
    }

    @BeforeEach
    public void setUp() {
        identifiantRef = IdentifiantBase.builder().build();

        entiteRef = new RecetteRef(RecetteBase.builder()
                .identifiant(identifiantRef)
                .build());

    }

    @Test
    public void testToString() {
        //Ceci n'est pas un test!
        LOG.info(this.entiteRef.toString());
    }

    @Test
    public void testGet() {
        Assertions.assertEquals(identifiantRef, this.entiteRef.getIdentifiant());
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


}
