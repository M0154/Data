import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.LocalTime;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

public class WebServiceServer {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/", exchange -> {
            String msg = htmlPage("Welcome to the Web Service!", 
                "<ul>" +
                    "<li><a href='/time'> Get Current Time</a></li>" +
                    "<li><a href='/reverse?text=Distributed'> Reverse String</a></li>" +
                    "<li><a href='/calculate?op=add&x=10&y=20'> Calculate (10 + 20)</a></li>" +
                "</ul>");
            sendHTML(exchange, msg);
        });

        server.createContext("/time", exchange -> {
            String time = "Current Time: " + LocalTime.now();
            String msg = htmlPage(" Time Service", "<p>" + time + "</p>" + navBack());
            sendHTML(exchange, msg);
        });

        server.createContext("/reverse", exchange -> {
            String query = exchange.getRequestURI().getQuery();
            String text = "No input";

            if (query != null && query.startsWith("text=")) {
                text = query.substring(5);
            }

            String reversed = new StringBuilder(text).reverse().toString();
            String msg = htmlPage(" Reverse Service", "<p>Input: " + text + "<br>Reversed: " + reversed + "</p>" + navBack());
            sendHTML(exchange, msg);
        });

        server.createContext("/calculate", exchange -> {
            String query = exchange.getRequestURI().getQuery();
            String result;

            try {
                String[] params = query.split("&");
                String op = "", xStr = "", yStr = "";

                for (String param : params) {
                    if (param.startsWith("op=")) op = param.substring(3);
                    else if (param.startsWith("x=")) xStr = param.substring(2);
                    else if (param.startsWith("y=")) yStr = param.substring(2);
                }

                double x = Double.parseDouble(xStr);
                double y = Double.parseDouble(yStr);

                // Traditional switch statement for Java 8
                switch (op) {
                    case "add":
                        result = "Result of addition: " + (x + y);
                        break;
                    case "sub":
                        result = "Result of subtraction: " + (x - y);
                        break;
                    case "mul":
                        result = "Result of multiplication: " + (x * y);
                        break;
                    case "div":
                        if (y != 0) {
                            result = "Result of division: " + (x / y);
                        } else {
                            result = " Error: Division by zero";
                        }
                        break;
                    default:
                        result = " Error: Invalid operation";
                        break;
                }

            } catch (Exception e) {
                result = " Error: " + e.getMessage();
            }

            String msg = htmlPage(" Calculation Service", "<p>" + result + "</p>" + navBack());
            sendHTML(exchange, msg);
        });

        server.setExecutor(null);
        System.out.println(" Server started on port 8080...");
        server.start();
    }

    // Sends HTML content with 200 status
    private static void sendHTML(HttpExchange exchange, String html) throws IOException {
        byte[] bytes = html.getBytes();
        exchange.getResponseHeaders().add("Content-Type", "text/html");
        exchange.sendResponseHeaders(200, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }

    // Wrap content in HTML structure
    private static String htmlPage(String title, String body) {
        return "<html><head><title>" + title + "</title></head><body style='font-family:sans-serif;'>" +
                "<h2>" + title + "</h2>" + body + "</body></html>";
    }

    // Back button to go to homepage
    private static String navBack() {
        return "<p><a href='/' style='color:blue;'> Back to Home</a></p>";
    }
}
