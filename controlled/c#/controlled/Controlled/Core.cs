﻿using System;
using System.Collections.Generic;
using System.Text;

namespace Controlled
{
    class Global
    {
        public static string ROOT_URL = "https://1s.vernachen.com/api/v1";
        public static string deviceNo = "123456";
        public static string ip = "192.168.0.1";
        public static string cpu = "123456";
        public static string ram = "123456";
        public static string os = "123456";
        public static string name = "123456";
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
