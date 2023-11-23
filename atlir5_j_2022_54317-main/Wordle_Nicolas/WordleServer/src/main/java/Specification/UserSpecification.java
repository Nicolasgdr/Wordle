package Specification;

/**
 *
 * @author jlc
 */
public class UserSpecification {

    private int id;
    private String name;

    /**
     *
     * @param id
     * @param name
     */
    public UserSpecification(int id, String name) {
        this.id = 0;
        this.name = name;
    }

    /**
     *
     * @param id
     */
    public UserSpecification(int id) {
        this.id = id;
    }

    public UserSpecification(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

}
