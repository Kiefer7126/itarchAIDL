package com.example.hourg.itarchaidl;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    private ISampleService sampleServiceIf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(ISampleService.class.getName());
        bindService(
                intent,
                sampleServiceConn,
                BIND_AUTO_CREATE
        );

        EditText editA = (EditText)findViewById(R.id.editText);
        editA.setOnKeyListener(new EditListener());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(sampleServiceConn);
    }

    private class EditListener implements OnKeyListener{

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            EditText editA = (EditText)findViewById(R.id.editText);

            String rslt;
            try{
                int a = Integer.parseInt(editA.getText().toString());

                rslt = sampleServiceIf.nabeatu(a);

            }
            catch(NumberFormatException e){
                rslt = "数値を入れてください";
            }

            catch(RemoteException e){
                rslt = "RemoteException";
            }

            TextView sumText = (TextView)findViewById(R.id.textView);
            sumText.setText(String.valueOf(rslt));

            return false;
        }
    }

    private ServiceConnection sampleServiceConn = new ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            sampleServiceIf = ISampleService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            sampleServiceIf = null;
        }
    };
}
