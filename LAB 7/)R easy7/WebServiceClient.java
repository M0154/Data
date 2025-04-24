import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WebServiceClient {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter first number: ");
        double x = scanner.nextDouble();

        System.out.print("Enter second number: ");
        double y = scanner.nextDouble();

        System.out.print("Enter operation (add/sub/mul/div): ");
        String op = scanner.next();

        try {
            // Send request to the /calculate endpoint
            String urlCalc = "http://localhost:8080/calculate?op=" + op + "&x=" + x + "&y=" + y;
            String calcResponse = sendGetRequest(urlCalc);
            System.out.println(" Calculation Service Response: " + calcResponse);

            // Get time from service
            URL timeURL = new URL("http://localhost:8080/time");
            String timeResponse = sendGetRequest(timeURL.toString());
            System.out.println(" Time Service Response: " + timeResponse);

            // Reverse string
            String text = "Distributed";
            URL reverseURL = new URL("http://localhost:8080/reverse?text=" + text);
            String reverseResponse = sendGetRequest(reverseURL.toString());
            System.out.println(" Reverse Service Response: " + reverseResponse);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper method for sending GET requests
    public static String sendGetRequest(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
}
