using System;
using System.Web.Services;

[WebService(Namespace = "http://tempuri.org/")]
public class FirstService : WebService
{
    [WebMethod]
    public string HelloWorld()
    {
        return "Hello from Web Service!";
    }

    [WebMethod]
    public int Add(int a, int b)
    {
        return a + b;
    }
}