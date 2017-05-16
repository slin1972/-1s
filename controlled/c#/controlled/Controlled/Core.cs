using System;
using System.Collections.Generic;
using System.Text;

namespace Controlled
{
    class Global
    {
        public static string ROOT_URL = "https://1s.vernachen.com/api/v1";
        public static string deviceNo = SoftReg.getMNum();
        public static string ip = SoftReg.GetIpAddress();
        public static string cpu = SoftReg.getCPUInfo();
        public static string ram = SoftReg.GetPhisicalMemory() +"M";
        public static string os = Environment.OSVersion.Platform +" "+ Environment.OSVersion.VersionString;
        public static string name = Environment.MachineName;
        public static string av = "1";
        private static int seq = 1;



        internal static object getSeq()
        {
            return seq++;
        }
    }
    class Core
    {
        
    }
    class CommonResult
    {
        public int code;
        public string msg;
        public Dictionary<string, object> data { get; set; }

    }
}
