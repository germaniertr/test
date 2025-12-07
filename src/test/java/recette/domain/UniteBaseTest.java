package recette.domain;

import core.domain.Audit;
import core.domain.AuditBase;
import core.domain.Identifiant;
import core.domain.IdentifiantBase;
import java.time.Instant;
import java.util.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class UniteBaseTest {

    private static final Logger LOG = Logger.getLogger(UniteBaseTest.class.getName());

    private Identifiant identifiantRef;
    private String codeRef;
    private Unite entiteRef;
    private Long versionRef;
    private Audit auditRef;

    public UniteBaseTest() {
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
    }

    @Test
    public void testGetSet() {
        String code = "code modifié";
        entiteRef.setCode(code);
        Assertions.assertEquals(identifiantRef, entiteRef.getIdentifiant());
        Assertions.assertEquals(versionRef, entiteRef.getVersion());
        testAudit(this.auditRef, this.entiteRef.getAudit());

        Assertions.assertEquals(code, entiteRef.getCode());
    }

    private void testAudit(Audit ref, Audit audit) {
        Assertions.assertEquals(ref, audit);
        Assertions.assertEquals(ref.getUserCreation(),
                audit.getUserCreation());
        Assertions.assertEquals(ref.getUserModification(),
                audit.getUserModification());
    }

    @Test
    public void testToString() {
        //Ceci n'est pas un test!
        LOG.info(this.entiteRef.toString());
    }

    @Test
    public void testEquals() {
        Unite entite = UniteBase.builder()
                .identifiant(identifiantRef)
                .build();
        Assertions.assertNotSame(this.entiteRef, entite);
        Assertions.assertEquals(this.entiteRef, entite);
        Assertions.assertEquals(this.entiteRef.hashCode(), entite.hashCode());
    }

    @Test
    public void testEqualsUniteSame() {
        Assertions.assertSame(this.entiteRef, this.entiteRef);
        Assertions.assertEquals(this.entiteRef, this.entiteRef);
        Assertions.assertEquals(this.entiteRef.hashCode(), entiteRef.hashCode());
    }

    @Test
    public void testEqualsUniteNull() {
        Unite entite = null;

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
        String code = "c.s";
        Unite entite = UniteBase.builder()
                .identifiant((Identifiant) null)
                .code(code)
                .build();

        this.entiteRef.update(entite);

        Assertions.assertEquals(identifiantRef, this.entiteRef.getIdentifiant());
        Assertions.assertEquals(versionRef, entiteRef.getVersion());
        testAudit(this.auditRef, this.entiteRef.getAudit());

        Assertions.assertEquals(code, this.entiteRef.getCode());
    }

    @Test
    public void testUpdateNull() {
        this.entiteRef.update(null);

        Assertions.assertEquals(identifiantRef, this.entiteRef.getIdentifiant());
        Assertions.assertEquals(this.codeRef, this.entiteRef.getCode());
    }

    @Test
    public void testClone() {
        Unite entite = UniteBase.builder()
                .unite(entiteRef)
                .build();

        Assertions.assertNotSame(this.entiteRef, entite);
        Assertions.assertEquals(this.entiteRef, entite);
        Assertions.assertEquals(this.entiteRef.hashCode(), entite.hashCode());

        Assertions.assertEquals(this.entiteRef.getIdentifiant(), entite.getIdentifiant());
        Assertions.assertEquals(this.entiteRef.getVersion(), entite.getVersion());
        testAudit(this.entiteRef.getAudit(), entite.getAudit());

        Assertions.assertEquals(this.entiteRef.getCode(), entite.getCode());

    }

    @Test
    public void testCloneIllegalArgument() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> {
                    UniteBase.builder()
                            .unite((Unite) null)
                            .build();
                });
    }

}
