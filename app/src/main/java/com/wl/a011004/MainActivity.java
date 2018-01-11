//網路存取
//由於要上網路，所以要在manifests 的application 外 給網路權限:  <uses-permission android:name="android.permission.INTERNET" />
package com.wl.a011004;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void click1(View v)
    {
        new Thread()//連上網本身是危險的，無法再主執行序做，所以不能寫在onCreate裡,且會有多個exception
        {
            @Override
            public void run()
            {
                super.run();
                String str_url = "http://rate.bot.com.tw/xrt?Lang=zh-TW";
                URL url = null;
                try
                {
                    url = new URL(str_url);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();
                    InputStream inputStream = conn.getInputStream();
                    InputStreamReader isr = new InputStreamReader(inputStream);
                    BufferedReader br = new BufferedReader(isr);

                    StringBuilder sb=new StringBuilder(); //
                    String str;//
                    while((str=br.readLine())!=null)// 將所有網頁的字串 放在 StringBuilder
                    {
                        sb.append(str);
                    }

                    String str1 = sb.toString();//
                    Log.d("NET", str1);//
                    int index1=str1.indexOf("日圓 (JPY)");//  搜尋  日圓 (JPY) 這個字眼 的位置
                    int index2 = str1.indexOf("本行現金賣出", index1);// 從index1這個位置開始往後找  本行現金賣出 這個字眼
                    int index3 = str1.indexOf("0.2679", index2);// 當時匯率為0.2679 所以這行查 從 index2這個位置開始往後找  0.2679這個字眼 的位置
                    Log.d("NET", "index1:" + index1 + "index2:" + index2 + "index3:" + index3);// 秀出位置，算出 index2~ index3的 距離後 ，↑上面那行就不用寫了，這行也不用寫了  ，而我們在第一次跑的時候顯示log: index1:27145index2:27546index3:27602 ，可以看出lindex2和lindex3 相差56，所以才有下一行的56，而我們要取多少位元就56再+多少
                    String data1 = str1.substring(index2+56, index2+61);// 把 位置+距離  寫在這 第一個引數， 位置+距離+要抓的資料的字串位元數  寫在 第二個引數
                    Log.d("NET", data1);//

                    br.close();
                    isr.close();
                    inputStream.close();
                }
                catch (MalformedURLException e)
                {
                    e.printStackTrace();
                }
                catch (ProtocolException e)
                {
                    e.printStackTrace();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

            }
        }.start();
    }
}
