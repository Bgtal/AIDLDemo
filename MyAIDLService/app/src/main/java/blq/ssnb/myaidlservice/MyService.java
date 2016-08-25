package blq.ssnb.myaidlservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import blq.ssnb.aidl.IExecuteFunction;
import blq.ssnb.aidl.MInfo;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private IExecuteFunction.Stub mBinder = new IExecuteFunction.Stub() {

        @Override
        public MInfo getServiceIn(MInfo info) throws RemoteException {
            return prx("in",info,1111);
        }

        @Override
        public MInfo getServiceOut(MInfo info) throws RemoteException {
            return prx("out",info,2222);
        }

         @Override
        public MInfo getServiceInOut(MInfo info) throws RemoteException {
            return prx("inout",info,3333);
        }
    };

    private MInfo prx(String tag ,MInfo info,int age){
        L("-----"+tag+"-start--------");
        printInfo(tag+"-bef",info);
        info.setName("我是"+tag+"标识");
        info.setAge(age);
        printInfo(tag+"-aft",info);

        MInfo newInfo = new MInfo();
        newInfo.setName("我是"+tag+"标识");
        newInfo.setAge(age);
        printInfo(tag+"-new",newInfo);
        L("-----"+tag+"-end--------");
        return newInfo;
    }


    private void printInfo(String tag, MInfo info) {
        L(tag+"-msg:"+(info==null?"null":info.toString()));
        L(tag+"-address:"+(info==null?"null":Integer.toHexString(info.hashCode())));
    }
    private void L(String msg){
        Log.e("Service","=======>>>>>>>>>>>"+msg);
    }
}
