package recette.datasource.db;

/**
 *
 * @author dominique huguenin (dominique.huguenin at rpn.ch)
 */
//CHECKSTYLE.OFF: TypeName
public final class SQL {

    public static final class UNITES {

        public static final String DROP_TABLE
                = "DROP TABLE IF EXISTS unites CASCADE";

        public static final String CREATE_TABLE
                = """
                  CREATE TABLE IF NOT EXISTS unites (
                      uuid TEXT, -- aid
                      code TEXT NOT NULL,

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

        private UNITES() {
        }
    }

    public static final class INGREDIENTS {

        public static final String DROP_TABLE
                = "DROP TABLE IF EXISTS ingredients CASCADE";

        public static final String CREATE_TABLE
                = """
                  CREATE TABLE IF NOT EXISTS ingredients (
                      uuid TEXT, -- aid
                      nom TEXT NOT NULL,
                      detail TEXT,
                      recettes_uuid VARCHAR, -- aid

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

        private INGREDIENTS() {
        }
    }

    public static final class RECETTES {

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

        private RECETTES() {
        }
    }

    public static final class COMPOSANTS {

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

        private COMPOSANTS() {
        }
    }
//CHECKSTYLE.ON: TypeName
}
