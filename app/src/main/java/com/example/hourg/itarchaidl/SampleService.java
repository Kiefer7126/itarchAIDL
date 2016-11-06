package com.example.hourg.itarchaidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class SampleService extends Service {
    private ISampleService.Stub sampleServiceIf = new ISampleService.Stub(){
        @Override
        public String nabeatu(int num) throws RemoteException {
            //3の倍数の時にあほになる
            if (num % 3 == 0)
            {
                return "(☝ ՞ 3՞)☝ < " + num + " wwWWWwwwww";
            }

            return "" + num;
        }
    };
    @Override
    public IBinder onBind(Intent intent) {
            return sampleServiceIf;
   }
}
