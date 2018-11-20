package tw.brad.mynetwork1;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private TextView source;
    private StringBuffer sb;
    private UIHandler uiHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uiHandler = new UIHandler();
        source = findViewById(R.id.source);
    }

    public void test1(View view) {
        sb = new StringBuffer();

        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("https://www.sakura.com.tw/");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    BufferedReader reader =
                            new BufferedReader(
                                    new InputStreamReader(
                                            conn.getInputStream()));
                    String line = null; int i=1;
                    while ( (line = reader.readLine()) != null){
                        Log.v("brad", i + ":" + line);
                        sb.append(line + "\n");
                        i++;
                    }

                    reader.close();
                    uiHandler.sendEmptyMessage(0);


                } catch (Exception e) {
                    Log.v("brad", e.toString());
                }
            }
        }.start();
        Log.v("brad", "Main");
    }
    public void test2(View view) {

    }
    public void test3(View view) {

    }

    private class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            source.setText(sb);
        }
    }
}
