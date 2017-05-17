using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace Controlled
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            timer1.Start();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            string body = textBox2.Text;
            if (body.Equals(""))
            {
                body = null;
            }
            string response = Http.request(textBox1.Text, body);
            textBox3.Text = response;
            CommonResult commonResult = response.FromJson<CommonResult>();
            MessageBox.Show("1111");
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            String url = Global.ROOT_URL + "/heart";

            Dictionary<string, Object> dict = new Dictionary<string, object>();
            dict.Add("device_no", Global.deviceNo);
            dict.Add("ip", Global.ip);
            dict.Add("cpu", Global.cpu);
            dict.Add("ram", Global.ram);
            dict.Add("os", Global.os);
            dict.Add("av", Global.av);
            dict.Add("name", Global.name);
            dict.Add("seq", Global.getSeq());

            String body = dict.ToJson();
            string response = null ;
            response = Http.request(url, body);
            textBox3.Text = response;
            CommonResult result = response.FromJson<CommonResult>();

            if (result.code == 0)
            {
                //if (result.update != null)
                //{
                    //download latest version application and start new vesion application
                //    string fileName = Http.download(result.update);

                //    timer1.Stop();
               // }
                if (result.data != null)
                {
                    List<object> scriptList = (List<object>)result.data["scripts"];
                    if (scriptList != null)
                    {
                        foreach (Dictionary<String, object> script in scriptList)
                        {
                            if (script["script"] != null)
                            {
                                executeScript((int)script["id"], script["script"].ToString());
                            }
                        }
                    }
                }
            }
        }
        
        private void executeScript(int id, string script)
        {
            string result = Script.execute(script);

            textBox4.Text = result;
            result = Convert.ToBase64String(Encoding.GetEncoding("utf-8").GetBytes(result));
            Dictionary<string, Object> dict = new Dictionary<string, object>();
            dict.Add("device_no", Global.deviceNo);
            dict.Add("script", script);
            dict.Add("result", result);
            dict.Add("id", id);

            Http.request(Global.ROOT_URL + "/script_response", dict.ToJson());
        }

        private void button2_Click(object sender, EventArgs e)
        {
            executeScript(1, textBox5.Text);
        }

        private void Form1_Shown(object sender, EventArgs e)
        {
            this.Visible = false;
        }
    }



}
