package mycontroller;

/**
 * Responsible for the creation of Methods
 */
public class MethodFactory {

    private static MethodFactory instance = null;

    /**
     * MethodFactory getter
     * @return the instance of MethodFactory
     */
    public static MethodFactory getInstance() {
        if (instance == null) {
            instance = new MethodFactory();
        }
        return instance;
    }

    /**
     * Private constructor for singleton pattern
     */
    private MethodFactory() {}

    public MethodTemplate makeDirectMethod() {
        return new DirectMethod();
    }

    public MethodTemplate makeExploreMethod() {
        return new ExploreMethod();
    }




}
