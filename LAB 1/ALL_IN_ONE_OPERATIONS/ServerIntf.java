import java.rmi.*;

public interface ServerIntf extends Remote {
    public double Addition(double num1, double num2) throws RemoteException;
    public double Subtraction(double num1, double num2) throws RemoteException;
    public double Multiplication(double num1, double num2) throws RemoteException;
    public double Division(double num1, double num2) throws RemoteException;

    public int CountVowels(String input) throws RemoteException;
    public double FahrenheitToCelsius(double fahrenheit) throws RemoteException;
    public boolean CompareStrings(String str1, String str2) throws RemoteException;
    public double MilesToKm(double miles) throws RemoteException;
    public long Factorial(int num) throws RemoteException;
    public double PowerOfTwo(double num) throws RemoteException;
    public String EchoName(String name) throws RemoteException;
}
