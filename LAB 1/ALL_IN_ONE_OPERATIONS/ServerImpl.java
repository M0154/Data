import java.rmi.*;
import java.rmi.server.*;

public class ServerImpl extends UnicastRemoteObject implements ServerIntf {

    public ServerImpl() throws RemoteException {
        super();
    }

    public double Addition(double num1, double num2) throws RemoteException {
        return num1 + num2;
    }

    public double Subtraction(double num1, double num2) throws RemoteException {
        return num1 - num2;
    }

    public double Multiplication(double num1, double num2) throws RemoteException {
        return num1 * num2;
    }

    public double Division(double num1, double num2) throws RemoteException {
        if (num2 != 0) {
            return num1 / num2;
        } else {
            System.out.println("Cannot Divide A Number By Zero!");
            return 0;
        }
    }

    public int CountVowels(String input) throws RemoteException {
        int count = 0;
        input = input.toLowerCase();
        for (char c : input.toCharArray()) {
            if ("aeiou".indexOf(c) != -1) {
                count++;
            }
        }
        return count;
    }

    public double FahrenheitToCelsius(double fahrenheit) throws RemoteException {
        return (fahrenheit - 32) * 5.0 / 9.0;
    }

    public boolean CompareStrings(String str1, String str2) throws RemoteException {
        return str1.equals(str2);
    }

    public double MilesToKm(double miles) throws RemoteException {
        return miles * 1.60934;
    }

    public long Factorial(int num) throws RemoteException {
        long result = 1;
        for (int i = 1; i <= num; i++) {
            result *= i;
        }
        return result;
    }

    public double PowerOfTwo(double num) throws RemoteException {
        return Math.pow(num, 2);
    }

    public String EchoName(String name) throws RemoteException {
        return "Hello, " + name + "! Welcome to the RMI Server.";
    }
}
