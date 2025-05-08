import java.rmi.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            String serverURL = "rmi://localhost/Server";
            ServerIntf serverIntf = (ServerIntf) Naming.lookup(serverURL);

            System.out.print("Enter First Number: ");
            double num1 = sc.nextDouble();

            System.out.print("Enter Second Number: ");
            double num2 = sc.nextDouble();

            System.out.println("First Number Is: " + num1);
            System.out.println("Second Number Is: " + num2);

            System.out.println("--------------- Results ---------------");
            System.out.println("Addition Is: " + serverIntf.Addition(num1, num2));
            System.out.println("Subtraction Is: " + serverIntf.Subtraction(num1, num2));
            System.out.println("Multiplication Is: " + serverIntf.Multiplication(num1, num2));
            System.out.println("Division Is: " + serverIntf.Division(num1, num2));

            System.out.println("----------------------------------------");
            System.out.print("Enter a string to count vowels: ");
            sc.nextLine(); // consume leftover newline
            String inputString = sc.nextLine();
            System.out.println("Vowel Count: " + serverIntf.CountVowels(inputString));

            System.out.println("----------------------------------------");
            System.out.print("Enter Fahrenheit value: ");
            double fahrenheit = sc.nextDouble();
            System.out.println("Celsius: " + serverIntf.FahrenheitToCelsius(fahrenheit));

            System.out.println("----------------------------------------");
            System.out.print("Enter first string to compare: ");
            sc.nextLine(); // consume leftover newline
            String str1 = sc.nextLine();
            System.out.print("Enter second string to compare: ");
            String str2 = sc.nextLine();
            System.out.println("Strings are equal: " + serverIntf.CompareStrings(str1, str2));

            System.out.println("----------------------------------------");
            System.out.print("Enter distance in miles: ");
            double miles = sc.nextDouble();
            System.out.println("Distance in kilometers: " + serverIntf.MilesToKm(miles));

            System.out.println("----------------------------------------");
            System.out.print("Enter number for factorial: ");
            int factNum = sc.nextInt();
            System.out.println("Factorial: " + serverIntf.Factorial(factNum));

            System.out.println("----------------------------------------");
            System.out.print("Enter number to square (power of 2): ");
            double base = sc.nextDouble();
            System.out.println("Power of Two: " + serverIntf.PowerOfTwo(base));

            System.out.println("----------------------------------------");
            System.out.print("Enter your name: ");
            sc.nextLine(); // consume leftover newline
            String name = sc.nextLine();
            System.out.println(serverIntf.EchoName(name));
            
        } catch (Exception e) {
            System.out.println("Exception Occurred At Client! " + e.getMessage());
        }

        

    }
}
