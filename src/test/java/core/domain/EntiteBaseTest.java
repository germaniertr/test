package core.domain;

import java.time.Instant;
import java.util.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author dominique huguenin <dominique.huguenin at rpn.ch>
 */
public class EntiteBaseTest {

    private static final Logger LOG = Logger.getLogger(EntiteBaseTest.class.getName());

    private Entite entiteRef;
    private Identifiant idRef;
    private String uuidRef;
    private Long versionRef;
    private Instant dateCreationRef;
    private String userCreationRef;
    private Instant dateModificationRef;
    private String userModificationRef;
    private Audit auditRef;

    @BeforeEach
    public void setUp() {
        uuidRef = "12345678-1234-1234-123456789012";
        versionRef = 123L;

        idRef = IdentifiantBase.builder()
                .uuid(uuidRef)
                .build();
        
        dateCreationRef = Instant.MIN;
        userCreationRef = "user creation";
        dateModificationRef = Instant.now();
        userModificationRef = "user modification";

        auditRef = AuditBase.builder()
                .dateCreation(this.dateCreationRef)
                .userCreation(this.userCreationRef)
                .dateModification(this.dateModificationRef)
                .userModification(this.userModificationRef)
                .build();
        

        entiteRef = new EntiteBase(idRef, versionRef, auditRef) {
            @Override
            public void update(Entite entite) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }

    @Test
    public void testGet() {
        Assertions.assertEquals(idRef, entiteRef.getIdentifiant());
        Assertions.assertEquals(versionRef, entiteRef.getVersion());
        
        Assertions.assertEquals(
                auditRef.getDateCreation(),
                this.entiteRef.getAudit().getDateCreation());
        Assertions.assertEquals(
                auditRef.getUserCreation(),
                this.entiteRef.getAudit().getUserCreation());
        Assertions.assertEquals(
                auditRef.getDateModification(),
                this.entiteRef.getAudit().getDateModification());
        Assertions.assertEquals(
                auditRef.getUserModification(),
                this.entiteRef.getAudit().getUserModification());
        
    }

    @Test
    public void testToString() {
        //Ceci n'est pas un test!
        LOG.info(this.entiteRef.toString());
    }

    @Test
    public void testEquals() {
        Entite entite = new EntiteBase(idRef, versionRef, auditRef) {
            @Override
            public void update(Entite entite) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };

        Assertions.assertEquals(entiteRef, entite);
        Assertions.assertEquals(entiteRef.hashCode(), entite.hashCode());
        Assertions.assertEquals(entiteRef.equals(entite),
                entite.equals(entiteRef));

    }

    @Test
    public void testEqualsSame() {
        Assertions.assertSame(this.entiteRef, this.entiteRef);
        Assertions.assertEquals(this.entiteRef, this.entiteRef);
        Assertions.assertEquals(this.entiteRef.hashCode(), this.entiteRef.hashCode());
    }

    @Test
    public void testEqualsNull() {
        Entite entite = null;

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
