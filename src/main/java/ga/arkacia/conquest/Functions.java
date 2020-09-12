package ga.arkacia.conquest;

public class Functions {
    public static String idGen(String displayName) {
        return displayName.replace(" ", "_").toLowerCase();
    }
}
