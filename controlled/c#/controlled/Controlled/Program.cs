using Microsoft.Win32;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Runtime.InteropServices;
using System.Windows.Forms;

namespace Controlled
{
    static class Program
    {
        /// <summary>
        /// 应用程序的主入口点。
        /// </summary>
        [STAThread]
        static void Main()
        {

            string exePath = System.Environment.CurrentDirectory;
            LogHelper.info("start..." + exePath);
            string exeFileName = Process.GetCurrentProcess().MainModule.FileName.Replace(exePath+@"\", "");
            IniFile iniFile = new IniFile(exePath + "/c.ini");
            bool r = isRunning();
            //判断是否是最终目录
            string rpath = iniFile.IniReadValue("config", "rpath");
            //判断是否是刚复制的最终路径
            string rpath_1 = iniFile.IniReadValue("config", "rpath_1");

            //如果进程已经存在
            if (r)
            {
                if (!"Y".Equals(rpath_1))
                {
                    Application.Exit();
                    return;
                }
                else
                {
                    iniFile.IniWriteValue("config", "rpath_1", "N");
                }
            }
            else
            {
                //最终目录
                if ("Y".Equals(rpath))
                {
                    iniFile.IniWriteValue("config", "rpath_1", "N");
                }
                else
                {
                    String cmd = "";
                    String targetExe = null;
                    //移动这个exe到d 盘，如果d盘不存在  移动到C盘
                    if (Directory.Exists(@"D:\"))
                    {
                        targetExe = @"D:\" + exeFileName;
                        System.IO.File.Copy(exePath + @"\" + exeFileName, targetExe, true);
                        iniFile = new IniFile(@"D:\" + "c.ini");
                        cmd = "d:&&cd d:&&"+exeFileName;
                    }
                    else
                    {
                        targetExe = @"C:\" + exeFileName;
                        System.IO.File.Copy(exePath + @"\" + exeFileName, targetExe, true);
                        iniFile = new IniFile(@"C:\" + "c.ini");
                    }
                    
                    if (targetExe != null)
                    {
                        iniFile.IniWriteValue("config", "rpath", "Y");
                        iniFile.IniWriteValue("config", "rpath_1", "Y");
                        LogHelper.error(targetExe);
                        Script.execute(cmd);
                        Application.Exit();
                        return;
                    }
                }
            }

            iniFile.IniWriteValue("config", "register", "0");
            String s = iniFile.IniReadValue("config", "register");

            string exeName = "1scontrolled";
            if (!"0".Equals(s))
            {
                string path = Application.ExecutablePath;
                RegistryKey rk = Registry.LocalMachine;
                RegistryKey rk2 = rk.CreateSubKey(@"Software\Microsoft\Windows\CurrentVersion\Run");
                object aset = rk2.GetValue(exeName);
                if (aset == null)
                {
                    rk2.SetValue(exeName, path);
                }
                rk2.Close();
                rk.Close();
            }
            else //取消开机自启动  
            {
                string path = Application.ExecutablePath;
                RegistryKey rk = Registry.LocalMachine;
                RegistryKey rk2 = rk.CreateSubKey(@"Software\Microsoft\Windows\CurrentVersion\Run");
                 
                object aset = rk2.GetValue(exeName);
                if (aset != null)
                {
                    rk2.DeleteValue(exeName, false);
                }
                rk2.Close();
                rk.Close();
            } 
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Form1());
        }
        static bool isRunning(){
            string strProcessName = System.Diagnostics.Process.GetCurrentProcess().ProcessName;
            ////获取版本号 
            //CommonData.VersionNumber = Application.ProductVersion; 
            //检查进程是否已经启动，已经启动则显示报错信息退出程序。 
            if (System.Diagnostics.Process.GetProcessesByName(strProcessName).Length > 1)
            {

                return true;
            }
            return false;
        }
    }
}
