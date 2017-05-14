using System;
using System.Collections.Generic;
using System.Text;
using System.Net;
using System.IO;

namespace Controlled
{
    class Http
    {
        public static string request(string url)
        {
            return request(url, null, new string[] { "Content-Type: application/json" });
        }
        public static string request(string url, string body)
        {
            return request(url, body, new string[] { "Content-Type: application/json" });
        }
        public static string request(string url, string body, string[] headers)
        {
            string retString = string.Empty;
            try
            {
                HttpWebRequest request = (HttpWebRequest)WebRequest.Create(url);
                
                foreach(String header in headers){
                    string [] kv = header.Split(new char[]{':'});
                    string k = kv[0].Trim();
                    string v = kv[1].Trim();
                    if(k.Equals("Content-Type")){
                        request.ContentType = v;
                    }
                }
                if (body != null)
                {
                    request.Method = "POST";

                    byte[] byteArray = Encoding.Default.GetBytes(body);
                    request.ContentLength = byteArray.Length;
                    Stream newStream = request.GetRequestStream();
                    newStream.Write(byteArray, 0, byteArray.Length);//写入参数
                    newStream.Close();
                }
                else
                {
                    request.Method = "GET";
                }
                HttpWebResponse response = (HttpWebResponse)request.GetResponse();
                StreamReader sr = new StreamReader(response.GetResponseStream(), Encoding.Default);
                retString = sr.ReadToEnd();
                sr.Close();
                response.Close();
            }
            catch (Exception ex)
            {
                throw ex;
            }
            return retString;
        }

        internal static string download(string url)
        {
            WebClient client = new WebClient();
            string URLAddress = url;
            string receivePath = "";
            string fileName = System.IO.Path.GetFileName(URLAddress);
            client.DownloadFile(URLAddress, receivePath + fileName);
            return fileName;
        }
    }
}
