using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace Controlled
{
    class LogHelper
    {
        public static String getFileName(String level, string dateStr)
        {
            String logFile = "log_" + dateStr + "." + level + ".txt";
            return logFile;
        }
        public static void writeLine(String level, string text)
        {
            if (!Directory.Exists("logs"))
            {
                Directory.CreateDirectory("logs");
            }
            String logFile = "logs/log_" + DateTime.Now.ToString("yyyy_MM_dd") + "." + level + ".txt";
            text += "\r\n";
            using (StreamWriter sw = new StreamWriter(logFile, true, Encoding.UTF8))
            {
                sw.Write(DateTime.Now.ToString("[yyyy-MM-dd HH:mm:ss] ") + text);
            }
        }
        public static void info(String text)
        {
            writeLine("info", text);
        }
        public static void error(String text)
        {
            writeLine("error", text);
        }
        public static void warn(String text)
        {
            writeLine("warn", text);
        }
        public static void debug(String text)
        {
            writeLine("debug", text);
        }
    }
}
