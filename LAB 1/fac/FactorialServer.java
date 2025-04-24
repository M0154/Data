import java.rmi.Naming;

public class FactorialServer {

    public FactorialServer() {
        try {
            Factorial f = new FactorialImpl();
            Naming.rebind("rmi://localhost/FactorialService", f);
            System.out.println("FactorialService is ready.");
        } catch (Exception e) {
            System.out.println("Server Exception: " + e);
        }
    }

    public static void main(String[] args) {
        new FactorialServer();
    }
}
