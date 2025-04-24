using System;
using System.Windows.Forms;

namespace SvcConsumer
{
    public partial class Form1 : Form
    {
        MyServiceRef.FirstService service = new MyServiceRef.FirstService();

        public Form1()
        {
            InitializeComponent();
        }

        private void btnHello_Click(object sender, EventArgs e)
        {
            string response = service.HelloWorld();
            MessageBox.Show(response, "Service Response");
        }

        private void btnAdd_Click(object sender, EventArgs e)
        {
            int a = int.Parse(txtA.Text);
            int b = int.Parse(txtB.Text);
            int result = service.Add(a, b);
            MessageBox.Show("Result: " + result, "Add Result");
        }
    }
}