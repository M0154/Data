using System;
using System.Web.Services;

[WebService(Namespace = "http://localhost/MyWebServices/")]
public class FirstService : WebService {
    [WebMethod]
    public int Add(int a, int b) {
        return a + b;
    }

    [WebMethod]
    public string SayHello() {
        return "Hello World";
    }
}