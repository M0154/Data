<%@ Page Language = "C#" %>
<script runat = "server">
void runSrvice_Click(Object sender, EventArgs e) {
    FirstService mySvc = new FirstService();
    Label1.Text = mySvc.SayHello();
    Label2.Text = mySvc.Add(Int32.Parse(txtNum1.Text), Int32.Parse(txtNum2.Text)).ToString();
}
</script>
<html>
<head> </head>
<body>
<form runat = "server">
<p>
<em>First Number to Add </em>:
<asp:TextBox id = "txtNum1" runat = "server" Width = "43px">4</asp:TextBox>
</p>
<p>
<em>Second Number To Add </em>:
<asp:TextBox id = "txtNum2" runat = "server" Width = "44px">5</asp:TextBox>
</p>
<p><strong><u>Web Service Result -</u></strong></p>
<asp:Label id="Label1" runat="server" /><br />
<asp:Label id="Label2" runat="server" /><br />
<asp:Button id="runService" Text="Run Service" OnClick="runSrvice_Click" runat="server" />
</form>
</body>
</html>