package recette.domain;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public interface Unite {

    String getUUID();

    void update(Unite unite);

    String getCode();

    void setCode(String code);

}
