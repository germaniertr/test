package recette.domain;

import core.domain.Identifiant;
import core.domain.IdentifiantBase;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class DemoData {
//CHECKSTYLE.OFF: TypeName
//CHECKSTYLE.OFF: MethodLength

    private final Map<Identifiant, Unite> unites;
    private final Map<Identifiant, Recette> recettes;
    private final Map<Identifiant, Ingredient> ingredients;

    public DemoData() {
        this.unites = new HashMap<>();
        this.ingredients = new HashMap<>();
        this.recettes = new HashMap<>();
    }

    public void initialisation() {
        this.recettes.clear();
        this.ingredients.clear();
        this.unites.clear();

        this.initUnites();
        this.initIngredients();
        this.initRecettes();

        Ingredient sauceTomate
                = this.ingredients.get(
                        IdentifiantBase.builder()
                                .uuid(INGREDIENTS.SAUCE_TOMATES.UUID)
                                .build());

        sauceTomate.setRecette(this.recettes.get(
                IdentifiantBase.builder()
                        .uuid(RECETTES.SAUCE_TOMATES.UUID)
                        .build()));
    }

    public Map<Identifiant, Unite> getUnites() {
        return unites;
    }

    public Map<Identifiant, Recette> getRecettes() {
        return recettes;
    }

    public Map<Identifiant, Ingredient> getIngredients() {
        return ingredients;
    }

    public static final class UNITES {

        public static final class C_S {

            public static final String UUID
                    = "DEMO0000-0000-0000-0002-000000000001";

            public static final String CODE
                    = "c.s";
        }

        public static final class C_C {

            public static final String UUID
                    = "DEMO0000-0000-0000-0002-000000000002";

            public static final String CODE = "c.c";
        }

        public static final class G {

            public static final String UUID
                    = "DEMO0000-0000-0000-0002-000000000003";

            public static final String CODE
                    = "g";
        }

        public static final class BRINS {

            public static final String UUID
                    = "DEMO0000-0000-0000-0002-000000000004";

            public static final String CODE
                    = "brins";
        }

    }

    public static final class INGREDIENTS {

        public static final class AUBERGINE {

            public static final String UUID
                    = "DEMO0000-0000-0000-0001-000000000001";

            public static final String NOM = "aubergine";

            public static final String DETAIL = "description d'une aubergine";
        }

        public static final class HUILE {

            public static final String UUID
                    = "DEMO0000-0000-0000-0001-000000000002";

            public static final String NOM = "huile";
        }

        public static final class SEL {

            public static final String UUID
                    = "DEMO0000-0000-0000-0001-000000000003";

            public static final String NOM = "sel";
        }

        public static final class POIVRE {

            public static final String UUID
                    = "DEMO0000-0000-0000-0001-000000000004";

            public static final String NOM = "poivre";
        }

        public static final class CITRON_BIO {

            public static final String UUID
                    = "DEMO0000-0000-0000-0001-000000000005";

            public static final String NOM = "citron bio";

        }

        public static final class TOMATE_CERISE {

            public static final String UUID
                    = "DEMO0000-0000-0000-0001-000000000006";

            public static final String NOM = "tomate cerise";
        }

        public static final class FETA {

            public static final String UUID
                    = "DEMO0000-0000-0000-0001-000000000007";

            public static final String NOM = "feta";
        }

        public static final class THYM {

            public static final String UUID
                    = "DEMO0000-0000-0000-0001-000000000008";

            public static final String NOM = "thym";
        }

        public static final class MURE {

            public static final String UUID
                    = "DEMO0000-0000-0000-0001-000000000009";

            public static final String NOM = "mûre";
        }

        public static final class SAUCE_TOMATES {

            public static final String UUID
                    = "DEMO0000-0000-0000-0001-000000000010";

            public static final String NOM
                    = "sauce au tomates";

            public static final String DETAIL
                    = "Sauce tomate - avec des herbes aromatique";
        }

    }

    public static final class RECETTES {

        public static final class AUBERGINES_AU_FOUR {

            public static final String UUID
                    = "DEMO0000-0000-0000-0003-000000000001";

            public static final String NOM
                    = "Aubergines au four";

            public static final String DETAIL
                    = "Ces aubergines gratinées garnies de tomates, feta et "
                    + "mûres sont un régal pour l’oeil et les papilles";

            public static final String PREPARATION
                    = """
                    Préchauffer le four à 220C.

                    Partager les aubergines dans la longueur et, à l'aide d'un couteau, inciser
                    en croisillons sur env. 2 cm de profondeur, disposer sur une plaque chemisée
                    de papier cuisson. Arroser d'un filet d'huile, saler, poivrer.

                    Cuisson: env. 25 min au milieu du four. Retirer.

                    Râper le zeste du citron, presser 2 c.s. de jus, mélanger avec l'huile
                    dans un grand bol, saler, poivrer. Couper les tomates en deux, émietter la feta,
                    effeuiller le thym, incorporer àla sauce avec les mûres, répartir sur les
                    aubergines.
                    """;

            public static final Integer NOMBRE_PERSONNES
                    = 4;

            public static final class C_1 {

                public static final String UUID
                        = "DEMO0000-0000-0000-0004-000000000001";

                public static final int ORDRE = 1;

                public static final String INGREDIENT_UUID
                        = INGREDIENTS.AUBERGINE.UUID;

                public static final Double QUANTITE = 4.0;

                public static final String COMMENTAIRE = "d'env. 250g";

            }

            public static final class C_2 {

                public static final String UUID
                        = "DEMO0000-0000-0000-0004-000000000002";

                public static final int ORDRE = 2;

                public static final String INGREDIENT_UUID
                        = INGREDIENTS.HUILE.UUID;

                public static final Double QUANTITE = 4.0;

                public static final String UNITE_UUID = UNITES.C_S.UUID;
            }

            public static final class C_3 {

                public static final String UUID
                        = "DEMO0000-0000-0000-0004-000000000003";

                public static final int ORDRE = 3;

                public static final String INGREDIENT_UUID
                        = INGREDIENTS.SEL.UUID;

                public static final Double QUANTITE = 0.75;

                public static final String UNITE_UUID = UNITES.C_C.UUID;

            }

            public static final class C_4 {

                public static final String UUID
                        = "DEMO0000-0000-0000-0004-000000000004";

                public static final int ORDRE = 4;

                public static final String INGREDIENT_UUID
                        = INGREDIENTS.POIVRE.UUID;

                public static final String COMMENTAIRE = "un peu";

            }

            public static final class C_5 {

                public static final String UUID
                        = "DEMO0000-0000-0000-0004-000000000005";

                public static final int ORDRE = 5;

                public static final String INGREDIENT_UUID
                        = INGREDIENTS.CITRON_BIO.UUID;

                public static final Double QUANTITE = 1.0;
            }

            public static final class C_6 {

                public static final String UUID
                        = "DEMO0000-0000-0000-0004-000000000006";

                public static final int ORDRE = 6;

                public static final String INGREDIENT_UUID
                        = INGREDIENTS.HUILE.UUID;

                public static final Double QUANTITE = 3.0;

                public static final String UNITE_UUID = UNITES.C_S.UUID;
            }

            public static final class C_7 {

                public static final String UUID
                        = "DEMO0000-0000-0000-0004-000000000007";

                public static final int ORDRE = 7;

                public static final String INGREDIENT_UUID
                        = INGREDIENTS.SEL.UUID;

                public static final Double QUANTITE = 0.25;

                public static final String UNITE_UUID = UNITES.C_C.UUID;

            }

            public static final class C_8 {

                public static final String UUID
                        = "DEMO0000-0000-0000-0004-000000000008";

                public static final int ORDRE = 8;

                public static final String INGREDIENT_UUID
                        = INGREDIENTS.POIVRE.UUID;

                public static final String COMMENTAIRE = "un peu";

            }

            public static final class C_9 {

                public static final String UUID
                        = "DEMO0000-0000-0000-0004-000000000009";

                public static final int ORDRE = 9;

                public static final String INGREDIENT_UUID
                        = INGREDIENTS.TOMATE_CERISE.UUID;

                public static final Double QUANTITE = 250.0;

                public static final String UNITE_UUID = UNITES.G.UUID;
            }

            public static final class C_10 {

                public static final String UUID
                        = "DEMO0000-0000-0000-0004-000000000010";

                public static final int ORDRE = 10;

                public static final String INGREDIENT_UUID
                        = INGREDIENTS.FETA.UUID;

                public static final Double QUANTITE = 200.0;

                public static final String UNITE_UUID = UNITES.G.UUID;
            }

            public static final class C_11 {

                public static final String UUID
                        = "DEMO0000-0000-0000-0004-000000000011";

                public static final int ORDRE = 11;

                public static final String INGREDIENT_UUID
                        = INGREDIENTS.THYM.UUID;

                public static final Double QUANTITE = 4.0;

                public static final String UNITE_UUID = UNITES.BRINS.UUID;
            }

            public static final class C_12 {

                public static final String UUID
                        = "DEMO0000-0000-0000-0004-000000000012";

                public static final int ORDRE = 12;

                public static final String INGREDIENT_UUID
                        = INGREDIENTS.MURE.UUID;

                public static final Double QUANTITE = 250.0;

                public static final String UNITE_UUID = UNITES.G.UUID;
            }
        }

        public static final class POIRES_AUX_AMANDES {

            public static final String UUID
                    = "DEMO0000-0000-0000-0003-000000000002";

            public static final String NOM
                    = "Poires aux amandes en chemise";

            public static final String DETAIL
                    = "Une poire habillée de lanières feuilletées et fourrée "
                    + "d’amandes. Difficile de résister!";

            public static final Integer NOMBRE_PERSONNES
                    = 4;
        }

        public static final class POIVRONS_AU_FOUR {

            public static final String UUID
                    = "DEMO0000-0000-0000-0003-000000000003";

            public static final String NOM
                    = "Poivrons au four et cuisses de poulet";

            public static final String DETAIL
                    = "Super pratique à faire au four: poulet au paprika sur "
                    + "poivrons avec feta et noix de cajou.";

            public static final Integer NOMBRE_PERSONNES
                    = 4;
        }

        public static final class SAUCE_TOMATES {

            public static final String UUID
                    = "DEMO0000-0000-0000-0003-000000000004";

            public static final String NOM
                    = "Sauce aux tomates";

            public static final String DETAIL
                    = "Sauce tomate - avec des herbes aromatiques";

            public static final Integer NOMBRE_PERSONNES
                    = 4;
        }
    }

    private void initUnites() {
        Identifiant i = IdentifiantBase.builder()
                .uuid(UNITES.C_S.UUID)
                .build();
        Unite e = UniteBase.builder()
                .identifiant(i)
                .code(UNITES.C_S.CODE)
                .build();

        this.unites.put(i, e);

        i = IdentifiantBase.builder()
                .uuid(UNITES.C_C.UUID)
                .build();
        e = UniteBase.builder()
                .identifiant(i)
                .code(UNITES.C_C.CODE)
                .build();

        this.unites.put(i, e);

        i = IdentifiantBase.builder()
                .uuid(UNITES.G.UUID)
                .build();
        e = UniteBase.builder()
                .identifiant(i)
                .code(UNITES.G.CODE)
                .build();

        this.unites.put(i, e);

        i = IdentifiantBase.builder()
                .uuid(UNITES.BRINS.UUID)
                .build();
        e = UniteBase.builder()
                .identifiant(i)
                .code(UNITES.BRINS.CODE)
                .build();

        this.unites.put(i, e);

    }

    private void initIngredients() {

        Identifiant i = IdentifiantBase.builder()
                .uuid(INGREDIENTS.AUBERGINE.UUID)
                .build();
        Ingredient e = IngredientBase.builder()
                .identifiant(i)
                .nom(INGREDIENTS.AUBERGINE.NOM)
                .detail(INGREDIENTS.AUBERGINE.DETAIL)
                .build();

        this.ingredients.put(i, e);

        i = IdentifiantBase.builder()
                .uuid(INGREDIENTS.HUILE.UUID)
                .build();
        e = IngredientBase.builder()
                .identifiant(i)
                .nom(INGREDIENTS.HUILE.NOM)
                .build();

        this.ingredients.put(i, e);

        i = IdentifiantBase.builder()
                .uuid(INGREDIENTS.SEL.UUID)
                .build();
        e = IngredientBase.builder()
                .identifiant(i)
                .nom(INGREDIENTS.SEL.NOM)
                .build();

        this.ingredients.put(i, e);

        i = IdentifiantBase.builder()
                .uuid(INGREDIENTS.POIVRE.UUID)
                .build();
        e = IngredientBase.builder()
                .identifiant(i)
                .nom(INGREDIENTS.POIVRE.NOM)
                .build();

        this.ingredients.put(i, e);

        i = IdentifiantBase.builder()
                .uuid(INGREDIENTS.CITRON_BIO.UUID)
                .build();
        e = IngredientBase.builder()
                .identifiant(i)
                .nom(INGREDIENTS.CITRON_BIO.NOM)
                .build();

        this.ingredients.put(i, e);

        i = IdentifiantBase.builder()
                .uuid(INGREDIENTS.TOMATE_CERISE.UUID)
                .build();
        e = IngredientBase.builder()
                .identifiant(i)
                .nom(INGREDIENTS.TOMATE_CERISE.NOM)
                .build();

        this.ingredients.put(i, e);

        i = IdentifiantBase.builder()
                .uuid(INGREDIENTS.TOMATE_CERISE.UUID)
                .build();
        e = IngredientBase.builder()
                .identifiant(i)
                .nom(INGREDIENTS.TOMATE_CERISE.NOM)
                .build();

        this.ingredients.put(i, e);

        i = IdentifiantBase.builder()
                .uuid(INGREDIENTS.FETA.UUID)
                .build();
        e = IngredientBase.builder()
                .identifiant(i)
                .nom(INGREDIENTS.FETA.NOM)
                .build();

        this.ingredients.put(i, e);

        i = IdentifiantBase.builder()
                .uuid(INGREDIENTS.THYM.UUID)
                .build();
        e = IngredientBase.builder()
                .identifiant(i)
                .nom(INGREDIENTS.THYM.NOM)
                .build();

        this.ingredients.put(i, e);

        i = IdentifiantBase.builder()
                .uuid(INGREDIENTS.MURE.UUID)
                .build();
        e = IngredientBase.builder()
                .identifiant(i)
                .nom(INGREDIENTS.MURE.NOM)
                .build();

        this.ingredients.put(i, e);

        i = IdentifiantBase.builder()
                .uuid(INGREDIENTS.SAUCE_TOMATES.UUID)
                .build();
        e = IngredientBase.builder()
                .identifiant(i)
                .nom(INGREDIENTS.SAUCE_TOMATES.NOM)
                .detail(INGREDIENTS.SAUCE_TOMATES.DETAIL)
                .build();

        this.ingredients.put(i, e);
    }

    private void initRecettes() {

        Identifiant i = IdentifiantBase.builder()
                .uuid(RECETTES.AUBERGINES_AU_FOUR.UUID)
                .build();
        Recette e = RecetteBase.builder()
                .identifiant(i)
                .nom(RECETTES.AUBERGINES_AU_FOUR.NOM)
                .detail(RECETTES.AUBERGINES_AU_FOUR.DETAIL)
                .preparation(RECETTES.AUBERGINES_AU_FOUR.PREPARATION)
                .nombrePersonnes(RECETTES.AUBERGINES_AU_FOUR.NOMBRE_PERSONNES)
                .composant(
                        ComposantBase.builder()
                                .identifiant(IdentifiantBase.builder()
                                        .uuid(RECETTES.AUBERGINES_AU_FOUR.C_1.UUID)
                                        .build())
                                .ingredient(this.ingredients.get(IdentifiantBase.builder()
                                        .uuid(RECETTES.AUBERGINES_AU_FOUR.C_1.INGREDIENT_UUID)
                                        .build()))
                                .quantite(RECETTES.AUBERGINES_AU_FOUR.C_1.QUANTITE)
                                .commentaire(RECETTES.AUBERGINES_AU_FOUR.C_1.COMMENTAIRE)
                                .build()
                )
                .composant(
                        ComposantBase.builder()
                                .identifiant(IdentifiantBase.builder()
                                        .uuid(RECETTES.AUBERGINES_AU_FOUR.C_2.UUID)
                                        .build())
                                .ingredient(this.ingredients.get(IdentifiantBase.builder()
                                        .uuid(RECETTES.AUBERGINES_AU_FOUR.C_2.INGREDIENT_UUID)
                                        .build()))
                                .quantite(RECETTES.AUBERGINES_AU_FOUR.C_2.QUANTITE)
                                .unite(this.unites.get(IdentifiantBase.builder()
                                        .uuid(RECETTES.AUBERGINES_AU_FOUR.C_2.UNITE_UUID)
                                        .build()))
                                .build()
                )
                .composant(
                        ComposantBase.builder()
                                .identifiant(IdentifiantBase.builder()
                                        .uuid(RECETTES.AUBERGINES_AU_FOUR.C_3.UUID)
                                        .build())
                                .ingredient(this.ingredients.get(IdentifiantBase.builder()
                                        .uuid(RECETTES.AUBERGINES_AU_FOUR.C_3.INGREDIENT_UUID)
                                        .build()))
                                .quantite(RECETTES.AUBERGINES_AU_FOUR.C_3.QUANTITE)
                                .unite(this.unites.get(IdentifiantBase.builder()
                                        .uuid(RECETTES.AUBERGINES_AU_FOUR.C_3.UNITE_UUID)
                                        .build()))
                                .build()
                )
                .composant(
                        ComposantBase.builder()
                                .identifiant(IdentifiantBase.builder()
                                        .uuid(RECETTES.AUBERGINES_AU_FOUR.C_4.UUID)
                                        .build())
                                .ingredient(this.ingredients.get(IdentifiantBase.builder()
                                        .uuid(RECETTES.AUBERGINES_AU_FOUR.C_4.INGREDIENT_UUID)
                                        .build()))
                                .commentaire(RECETTES.AUBERGINES_AU_FOUR.C_4.COMMENTAIRE)
                                .build()
                )
                .composant(
                        ComposantBase.builder()
                                .identifiant(IdentifiantBase.builder()
                                        .uuid(RECETTES.AUBERGINES_AU_FOUR.C_5.UUID)
                                        .build())
                                .ingredient(this.ingredients.get(IdentifiantBase.builder()
                                        .uuid(RECETTES.AUBERGINES_AU_FOUR.C_5.INGREDIENT_UUID)
                                        .build()))
                                .quantite(RECETTES.AUBERGINES_AU_FOUR.C_5.QUANTITE)
                                .build()
                )
                .composant(
                        ComposantBase.builder()
                                .identifiant(IdentifiantBase.builder()
                                        .uuid(RECETTES.AUBERGINES_AU_FOUR.C_6.UUID)
                                        .build())
                                .ingredient(this.ingredients.get(IdentifiantBase.builder()
                                        .uuid(RECETTES.AUBERGINES_AU_FOUR.C_6.INGREDIENT_UUID)
                                        .build()))
                                .quantite(RECETTES.AUBERGINES_AU_FOUR.C_6.QUANTITE)
                                .unite(this.unites.get(IdentifiantBase.builder()
                                        .uuid(RECETTES.AUBERGINES_AU_FOUR.C_6.UNITE_UUID)
                                        .build()))
                                .build()
                )
                .composant(
                        ComposantBase.builder()
                                .identifiant(IdentifiantBase.builder()
                                        .uuid(RECETTES.AUBERGINES_AU_FOUR.C_7.UUID)
                                        .build())
                                .ingredient(this.ingredients.get(IdentifiantBase.builder()
                                        .uuid(RECETTES.AUBERGINES_AU_FOUR.C_7.INGREDIENT_UUID)
                                        .build()))
                                .quantite(RECETTES.AUBERGINES_AU_FOUR.C_7.QUANTITE)
                                .unite(this.unites.get(IdentifiantBase.builder()
                                        .uuid(RECETTES.AUBERGINES_AU_FOUR.C_7.UNITE_UUID)
                                        .build()))
                                .build()
                )
                .composant(
                        ComposantBase.builder()
                                .identifiant(IdentifiantBase.builder()
                                        .uuid(RECETTES.AUBERGINES_AU_FOUR.C_8.UUID)
                                        .build())
                                .ingredient(this.ingredients.get(IdentifiantBase.builder()
                                        .uuid(RECETTES.AUBERGINES_AU_FOUR.C_8.INGREDIENT_UUID)
                                        .build()))
                                .commentaire(RECETTES.AUBERGINES_AU_FOUR.C_8.COMMENTAIRE)
                                .build()
                )
                .composant(
                        ComposantBase.builder()
                                .identifiant(IdentifiantBase.builder()
                                        .uuid(RECETTES.AUBERGINES_AU_FOUR.C_9.UUID)
                                        .build())
                                .ingredient(this.ingredients.get(IdentifiantBase.builder()
                                        .uuid(RECETTES.AUBERGINES_AU_FOUR.C_9.INGREDIENT_UUID)
                                        .build()))
                                .quantite(RECETTES.AUBERGINES_AU_FOUR.C_9.QUANTITE)
                                .unite(this.unites.get(IdentifiantBase.builder()
                                        .uuid(RECETTES.AUBERGINES_AU_FOUR.C_9.UNITE_UUID)
                                        .build()))
                                .build()
                )
                .composant(
                        ComposantBase.builder()
                                .identifiant(IdentifiantBase.builder()
                                        .uuid(RECETTES.AUBERGINES_AU_FOUR.C_10.UUID)
                                        .build())
                                .ingredient(this.ingredients.get(IdentifiantBase.builder()
                                        .uuid(RECETTES.AUBERGINES_AU_FOUR.C_10.INGREDIENT_UUID)
                                        .build()))
                                .quantite(RECETTES.AUBERGINES_AU_FOUR.C_10.QUANTITE)
                                .unite(this.unites.get(IdentifiantBase.builder()
                                        .uuid(RECETTES.AUBERGINES_AU_FOUR.C_10.UNITE_UUID)
                                        .build()))
                                .build()
                )
                .composant(
                        ComposantBase.builder()
                                .identifiant(IdentifiantBase.builder()
                                        .uuid(RECETTES.AUBERGINES_AU_FOUR.C_11.UUID)
                                        .build())
                                .ingredient(this.ingredients.get(IdentifiantBase.builder()
                                        .uuid(RECETTES.AUBERGINES_AU_FOUR.C_11.INGREDIENT_UUID)
                                        .build()))
                                .quantite(RECETTES.AUBERGINES_AU_FOUR.C_11.QUANTITE)
                                .unite(this.unites.get(IdentifiantBase.builder()
                                        .uuid(RECETTES.AUBERGINES_AU_FOUR.C_11.UNITE_UUID)
                                        .build()))
                                .build()
                )
                .composant(
                        ComposantBase.builder()
                                .identifiant(IdentifiantBase.builder()
                                        .uuid(RECETTES.AUBERGINES_AU_FOUR.C_12.UUID)
                                        .build())
                                .ingredient(this.ingredients.get(IdentifiantBase.builder()
                                        .uuid(RECETTES.AUBERGINES_AU_FOUR.C_12.INGREDIENT_UUID)
                                        .build()))
                                .quantite(RECETTES.AUBERGINES_AU_FOUR.C_12.QUANTITE)
                                .unite(this.unites.get(IdentifiantBase.builder()
                                        .uuid(RECETTES.AUBERGINES_AU_FOUR.C_12.UNITE_UUID)
                                        .build()))
                                .build()
                )
                .build();

        this.recettes.put(i, e);

        i = IdentifiantBase.builder()
                .uuid(RECETTES.POIRES_AUX_AMANDES.UUID)
                .build();
        e = RecetteBase.builder()
                .identifiant(i)
                .nom(RECETTES.POIRES_AUX_AMANDES.NOM)
                .detail(RECETTES.POIRES_AUX_AMANDES.DETAIL)
                .nombrePersonnes(RECETTES.POIRES_AUX_AMANDES.NOMBRE_PERSONNES)
                .build();

        this.recettes.put(i, e);

        i = IdentifiantBase.builder()
                .uuid(RECETTES.POIVRONS_AU_FOUR.UUID)
                .build();
        e = RecetteBase.builder()
                .identifiant(i)
                .nom(RECETTES.POIVRONS_AU_FOUR.NOM)
                .detail(RECETTES.POIVRONS_AU_FOUR.DETAIL)
                .nombrePersonnes(RECETTES.POIVRONS_AU_FOUR.NOMBRE_PERSONNES)
                .build();

        this.recettes.put(i, e);

        i = IdentifiantBase.builder()
                .uuid(RECETTES.SAUCE_TOMATES.UUID)
                .build();
        e = RecetteBase.builder()
                .identifiant(i)
                .nom(RECETTES.SAUCE_TOMATES.NOM)
                .detail(RECETTES.SAUCE_TOMATES.DETAIL)
                .nombrePersonnes(RECETTES.SAUCE_TOMATES.NOMBRE_PERSONNES)
                .build();

        this.recettes.put(i, e);
    }
//CHECKSTYLE.ON: TypeName
//CHECKSTYLE.ON: MethodLength
}
