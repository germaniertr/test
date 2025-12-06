package recette.datasource.db;

/**
 *
 * @author dominique huguenin (dominique.huguenin at rpn.ch)
 */
//CHECKSTYLE.OFF: TypeName
public final class SQL {

    public static final class VERROU_OPTIMISTE {

        public static final String CREATE_PROCEDURE
                = """
                  CREATE OR REPLACE FUNCTION F_maj_version() RETURNS TRIGGER AS $FUNCT$
                  BEGIN
                    IF (TG_OP = 'INSERT') THEN
                      NEW.version := 1;
                    ELSIF (TG_OP = 'UPDATE') THEN
                      NEW.version := OLD.version + 1;
                    END IF ;
                    RETURN NEW;
                  END;
                  $FUNCT$ LANGUAGE plpgsql;
                  """;

        public static final String DROP_PROCEDURE
                = "DROP FUNCTION IF EXISTS F_maj_version();\n";

    }

    public static final class UNITES {

        public static final String CREATE_TRIGGER_VERROU_OPTIMISTE
                = """
                  CREATE TRIGGER TR_BIUS_unites_verrou
                  BEFORE INSERT OR UPDATE
                  ON unites FOR EACH ROW
                  EXECUTE PROCEDURE F_maj_version()
                  ;
                  """;

        public static final String DROP_TRIGGER_VERROU_OPTIMISTE
                = """
                  DROP TRIGGER IF EXISTS TR_BIUS_unites_verrou ON unites
                  ;
                  """;

        public static final String DROP_TABLE
                = "DROP TABLE IF EXISTS unites CASCADE";

        public static final String CREATE_TABLE
                = """
                  CREATE TABLE IF NOT EXISTS unites (
                      uuid TEXT, -- aid
                      code TEXT NOT NULL,

                      version INTEGER DEFAULT 1,

                      CONSTRAINT pk_unites
                          PRIMARY KEY (uuid)
                  )
                  """;

        public static final String ALTER_TABLE
                = """
                  ALTER TABLE IF EXISTS unites
                      DROP CONSTRAINT IF EXISTS nid1_unites_code,
                      ADD CONSTRAINT nid1_unites_code
                              UNIQUE (code)
                  """;

        public static final String INSERT
                = """
                  INSERT INTO unites (uuid, code)
                  VALUES (?,?)
                  """;

        public static final String SELECTION
                = """
                  SELECT u.uuid,
                   u.version,
                   u.code
                  """;

        public static final String SELECT_BY_FILTRE
                = SELECTION
                + """
                  FROM unites u
                  WHERE to_tsvector('french', u.code) @@ websearch_to_tsquery('french', ?)
                  ORDER BY u.code
                  """;

        public static final String SELECT_BY_UUID
                = SELECTION
                + """
                  FROM unites u
                  WHERE u.uuid = ?
                  """;

        public static final String DELETE
                = """
                  DELETE FROM unites
                  """;

        public static final String DELETE_BY_UUID
                = DELETE
                + """
                  WHERE uuid = ? AND version = ?
                  """;

        public static final String UPDATE
                = """
                  UPDATE unites
                  SET  code = ?
                  WHERE uuid = ? AND version = ?
                  """;

        private UNITES() {
        }

        public static final class ATTRIBUTS {

            public static final String CODE = "code";

            private ATTRIBUTS() {
            }
        }

    }

    public static final class INGREDIENTS {

        public static final String CREATE_TRIGGER_VERROU_OPTIMISTE
                = """
                  CREATE TRIGGER TR_BIUS_ingredients_verrou
                  BEFORE INSERT OR UPDATE
                  ON ingredients FOR EACH ROW
                  EXECUTE PROCEDURE F_maj_version()
                  ;
                  """;

        public static final String DROP_TRIGGER_VERROU_OPTIMISTE
                = """
                  DROP TRIGGER IF EXISTS TR_BIUS_ingredients_verrou ON unites
                  ;
                  """;

        public static final String DROP_TABLE
                = "DROP TABLE IF EXISTS ingredients CASCADE";

        public static final String CREATE_TABLE
                = """
                  CREATE TABLE IF NOT EXISTS ingredients (
                      uuid TEXT, -- aid
                      nom TEXT NOT NULL,
                      detail TEXT,
                      recettes_uuid VARCHAR, -- aid

                      version INTEGER DEFAULT 1,

                      CONSTRAINT pk_ingredients
                          PRIMARY KEY (uuid)
                  )
                  """;

        public static final String ALTER_TABLE
                = """
                  ALTER TABLE IF EXISTS ingredients
                      DROP CONSTRAINT IF EXISTS fk1_ingredients_recettes,
                      ADD CONSTRAINT fk1_ingredients_recettes
                              FOREIGN KEY (recettes_uuid)
                              REFERENCES recettes (uuid)  DEFERRABLE,

                      DROP CONSTRAINT IF EXISTS u1_ingredients_nom,
                      ADD CONSTRAINT u1_ingredients_nom
                              UNIQUE (nom),

                      DROP CONSTRAINT IF EXISTS u2_ingredients_recettes_uuid,
                      ADD CONSTRAINT u2_ingredients_recettes_uuid
                              UNIQUE (recettes_uuid)
                    """;
        public static final String INSERT
                = """
                  INSERT INTO ingredients ( uuid, nom, detail, recettes_uuid)
                  VALUES (?,?,?,?)
                  """;

        public static final String SELECTION
                = """
                  SELECT i.uuid,
                         i.nom,
                         i.detail,
                         i.recettes_uuid
                   """;

        public static final String SELECT_BY_FILTRE
                = SELECTION
                + """
                  FROM ingredients i
                  WHERE to_tsvector('french', coalesce(i.nom, ''))
                        || to_tsvector('french', coalesce(i.detail, ''))
                        @@ websearch_to_tsquery('french', ?)
                  """;

        public static final String SELECT_BY_UUID
                = SELECTION
                + """
                  FROM ingredients i
                  WHERE i.uuid = ?
                  """;

        public static final String DELETE
                = """
                  DELETE FROM ingredients
                  """;

        public static final String DELETE_BY_UUID
                = DELETE
                + """
                  WHERE uuid = ?
                  """;

        public static final String UPDATE
                = """
                  UPDATE ingredients
                  SET  nom = ?,
                       detail = ?,
                       recettes_uuid = ?
                  WHERE uuid = ?
                  """;

        private INGREDIENTS() {
        }

        public static final class ATTRIBUTS {

            public static final String NOM = "nom";
            public static final String DETAIL = "detail";
            public static final String RECETTES_UUID = "recettes_uuid";

            private ATTRIBUTS() {
            }
        }
    }

    public static final class RECETTES {

        public static final String CREATE_TRIGGER_VERROU_OPTIMISTE
                = """
                  CREATE TRIGGER TR_BIUS_recettes_verrou
                  BEFORE INSERT OR UPDATE
                  ON recettes FOR EACH ROW
                  EXECUTE PROCEDURE F_maj_version()
                  ;
                  """;

        public static final String DROP_TRIGGER_VERROU_OPTIMISTE
                = """
                  DROP TRIGGER IF EXISTS TR_BIUS_recettes_verrou ON unites
                  ;
                  """;

        public static final String DROP_TABLE
                = "DROP TABLE IF EXISTS recettes CASCADE";

        public static final String CREATE_TABLE
                = """
                  CREATE TABLE IF NOT EXISTS recettes (
                      uuid TEXT, -- aid
                      nom TEXT NOT NULL,
                      detail TEXT,
                      preparation TEXT,
                      nombre_personnes INTEGER DEFAULT 4,

                      version INTEGER DEFAULT 1,

                      CONSTRAINT pk_recettes
                          PRIMARY KEY (uuid)
                  )
                  """;

        public static final String ALTER_TABLE
                = """
                  ALTER TABLE IF EXISTS recettes
                      DROP CONSTRAINT IF EXISTS u1_recettes_nom,
                      ADD CONSTRAINT u1_recettes_nom
                              UNIQUE (nom)
                  """;

        public static final String INSERT
                = """
                  INSERT INTO recettes (uuid, nom, detail,
                   preparation, nombre_personnes)
                  VALUES(?,?,?,?,?)
                  """;

        public static final String SELECTION
                = """
                  SELECT r.uuid,
                     r.nom, r.detail,
                     r.preparation,
                     r.nombre_personnes
                  """;

        public static final String SELECT_BY_FILTRE
                = SELECTION
                + """
                  FROM recettes r
                  WHERE to_tsvector('french', coalesce(r.nom, ''))
                       || to_tsvector('french', coalesce(r.detail, ''))
                       || to_tsvector('french', coalesce(r.preparation, ''))
                        @@ websearch_to_tsquery('french', ?)
                  """;

        public static final String SELECT_BY_UUID
                = SELECTION
                + """
                  FROM recettes r
                  WHERE r.uuid = ?
                  """;

        public static final String DELETE
                = """
                  DELETE FROM recettes
                  """;

        public static final String DELETE_BY_UUID
                = DELETE
                + """
                  WHERE uuid = ?
                  """;

        public static final String UPDATE
                = """
                  UPDATE recettes
                  SET  nom = ?,
                       detail = ?,
                       preparation = ?,
                       nombre_personnes = ?
                  WHERE uuid = ?
                  """;

        private RECETTES() {
        }

        public static final class ATTRIBUTS {

            public static final String NOM = "nom";
            public static final String DETAIL = "detail";
            public static final String PREPARATION = "preparation";
            public static final String NOMBRE_PERSONNES = "nombre_personnes";
        }
    }

    public static final class COMPOSANTS {

        public static final String CREATE_TRIGGER_VERROU_OPTIMISTE
                = """
                  CREATE TRIGGER TR_BIUS_composants_verrou
                  BEFORE INSERT OR UPDATE
                  ON composants FOR EACH ROW
                  EXECUTE PROCEDURE F_maj_version()
                  ;
                  """;

        public static final String DROP_TRIGGER_VERROU_OPTIMISTE
                = """
                  DROP TRIGGER IF EXISTS TR_BIUS_composants_verrou ON unites
                  ;
                  """;

        public static final String DROP_TABLE
                = "DROP TABLE IF EXISTS composants CASCADE";

        public static final String CREATE_TABLE
                = """
                  CREATE TABLE IF NOT EXISTS composants (
                      uuid TEXT, -- aid

                      recettes_uuid VARCHAR NOT NULL, -- aid
                      ordre INTEGER NOT NULL,
                      quantite NUMERIC(8,2),
                      commentaire TEXT,
                      ingredients_uuid VARCHAR NOT NULL, -- aid
                      unites_uuid VARCHAR, -- aid

                      version INTEGER DEFAULT 1,

                      CONSTRAINT pk_composants
                          PRIMARY KEY (uuid)
                  )""";

        public static final String ALTER_TABLE
                = """
                  ALTER TABLE IF EXISTS composants
                      DROP CONSTRAINT IF EXISTS fk1_composants_recettes,
                      ADD CONSTRAINT fk1_composants_recettes
                              FOREIGN KEY (recettes_uuid)
                              REFERENCES recettes (uuid) ON DELETE CASCADE,

                      DROP CONSTRAINT IF EXISTS fk2_composants_ingredients,
                      ADD CONSTRAINT fk2_composants_ingredients
                              FOREIGN KEY (ingredients_uuid)
                              REFERENCES ingredients (uuid),

                      DROP CONSTRAINT IF EXISTS fk3_composants_unites,
                      ADD CONSTRAINT fk3_composants_unites
                              FOREIGN KEY (unites_uuid)
                              REFERENCES unites (uuid),

                      DROP CONSTRAINT IF EXISTS u1_composants_ordre,
                      ADD CONSTRAINT u1_composants_ordre
                              UNIQUE (recettes_uuid,ordre)
                  """;

        public static final String INSERT
                = """
                  INSERT INTO composants (uuid, recettes_uuid,
                                          ordre, quantite, commentaire,
                                          ingredients_uuid, unites_uuid)
                  VALUES(?,?,?,?,?,?,?)
                  """;

        public static final String SELECTION
                = """
                  SELECT c.uuid,
                  c.quantite,
                  c.commentaire,
                  c.ingredients_uuid,
                  c.unites_uuid
                  """;

        public static final String SELECT_BY_UUID_RECETTE
                = SELECTION
                + """
                  FROM composants c
                  WHERE c.recettes_uuid = ?
                  ORDER BY c.ordre\n
                  """;

        public static final String DELETE
                = """
                  DELETE FROM composants
                  """;

        public static final String DELETE_BY_UUID_RECETTES
                = DELETE
                + """
                  WHERE recettes_uuid = ?
                  """;

        private COMPOSANTS() {
        }

        public static final class ATTRIBUTS {

            public static final String QUANTITE = "quantite";
            public static final String COMMENTAIRE = "commentaire";
            public static final String INGREDIENTS_UUID = "ingredients_uuid";
            public static final String UNITES_UUID = "unites_uuid";

            private ATTRIBUTS() {
            }
        }
    }

    public static final class ENTITES {

        private ENTITES() {
        }

        public static final class ATTRIBUTS {

            public static final String UUID = "uuid";
            public static final String VERSION = "version";            

            private ATTRIBUTS() {
            }
        }
    }
//CHECKSTYLE.ON: TypeName
}
