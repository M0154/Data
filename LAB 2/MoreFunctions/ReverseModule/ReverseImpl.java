package ReverseModule;

public class ReverseImpl extends ReversePOA {
    // Make the constructor public
    public ReverseImpl() {
        super();
        System.out.println("Reverse Object Created");
    }

    public String reverse_string(String name) {
        StringBuffer str = new StringBuffer(name);
        str.reverse();
        return "Server Send " + str;
    }

    public String to_upper(String name) {
        return "Server Send " + name.toUpperCase();
    }

    public String to_lower(String name) {
        return "Server Send " + name.toLowerCase();
    }

    public String capitalize(String name) {
        if (name == null || name.isEmpty()) return "Server Send " + name;
        return "Server Send " + Character.toUpperCase(name.charAt(0)) + name.substring(1).toLowerCase();
    }
}
