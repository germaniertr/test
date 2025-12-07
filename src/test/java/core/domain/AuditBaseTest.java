package core.domain;

import java.time.Instant;
import java.util.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author dominique huguenin (dominique.huguenin at rpn.ch)
 */
public class AuditBaseTest {

    private Audit auditRef;
    private Instant dateCreationRef;
    private String userCreationRef;
    private Instant dateModificationRef;
    private String userModificationRef;
    private static final Logger LOG
            = Logger.getLogger(AuditBaseTest.class.getName());

    /**
     *
     */
    public AuditBaseTest() {
    }

    /**
     *
     */
    @BeforeEach
    public void setUp() {
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
    }

    /**
     *
     */
    @Test
    public void testGet() {
        Assertions.assertEquals(this.dateCreationRef,
                auditRef.getDateCreation());
        Assertions.assertEquals(this.userCreationRef,
                auditRef.getUserCreation());
        Assertions.assertEquals(this.dateModificationRef,
                auditRef.getDateModification());
        Assertions.assertEquals(this.userModificationRef,
                auditRef.getUserModification());
    }

    /**
     *
     */
    @Test
    public void testEquals() {
        Audit audit = AuditBase.builder()
                .dateCreation(this.dateCreationRef)
                .dateModification(this.dateModificationRef)
                .build();

        Assertions.assertEquals(this.auditRef,
                audit);
        Assertions.assertNotSame(this.auditRef,
                audit);
    }

    /**
     *
     */
    @Test
    public void testToString() {
        LOG.info(auditRef.toString());
    }

}
