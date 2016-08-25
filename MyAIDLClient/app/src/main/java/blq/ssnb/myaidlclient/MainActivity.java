package blq.ssnb.myaidlclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import blq.ssnb.aidl.ICallBack;
import blq.ssnb.aidl.IExecuteFunction;
import blq.ssnb.aidl.MInfo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindEvent();
    }

    int index =0;
    private void bindEvent() {
        findViewById(R.id.btn_connection).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                connectionServie();
            }
        });
        findViewById(R.id.btn_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 1;
                connectionServie();
            }
        });

        findViewById(R.id.btn_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 2;
                connectionServie();
            }
        });

        findViewById(R.id.btn_inout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 3;
                connectionServie();
            }
        });
        findViewById(R.id.btn_unbind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                index = 4;
//                connectionServie();
            }
        });
    }

    private void connectionServie(){
        Intent intent = new Intent();
        intent.setAction("blq.ssnb.action");
        intent.setPackage("blq.ssnb.myaidlservice");
        bindService(intent,connection,BIND_AUTO_CREATE);
    }
    private IExecuteFunction executeFunction;
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            L("onServiceConnected:"+name);
            executeFunction = IExecuteFunction.Stub.asInterface(service);
            if(executeFunction!=null){
                Toast.makeText(MainActivity.this,"连接成功",Toast.LENGTH_SHORT).show();
                if(index==0){
                    unbindService(connection);
                }else if(index == 1){
                    L("-----in-start--------");
                    sendInfoIn();
                    L("-----in-end--------");
                }else if(index == 2){
                    L("-----out-start--------");
                    sendInfoOut();
                    L("-----out-end--------");
                }else if(index == 3){
                    L("-----inout-start--------");
                    sendInfoInOut();
                    L("-----inout-end--------");
                }else if(index == 4){
                    unbind();
                }
            }
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            executeFunction = null;
            L("onServiceDisconnected:"+name);
        }
    };

    int indexIn = 0;
    private void sendInfoIn() {
        try {
            if(executeFunction!=null){
                String msg ;
                MInfo inInfo = new MInfo();
                if(indexIn%2==0){
                    msg ="有数据";
                    inInfo.setName("客户端in");
                    inInfo.setAge(7777);
                }else{
                    msg ="无数据";
                }
                indexIn++;
                printInfo("in-"+msg+"-bef",inInfo);
                MInfo newInfo = executeFunction.getServiceIn(inInfo);
                printInfo("in-"+msg+"-aft",inInfo);
                printInfo("in-"+msg+"-return",newInfo);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        unbind();
    }

    int indexOut=0;
    private void sendInfoOut() {
        try {
            if(executeFunction!=null){
                String msg ;
                MInfo outInfo = new MInfo();
                if(indexOut%2==0){
                    msg ="有数据";
                    outInfo.setName("客户端out");
                    outInfo.setAge(8888);
                }else{
                    msg ="无数据";
                }
                indexOut++;
                printInfo("out-"+msg+"-bef",outInfo);
                MInfo newInfo = executeFunction.getServiceOut(outInfo);
                printInfo("out-"+msg+"-aft",outInfo);
                printInfo("out-"+msg+"-return",newInfo);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        unbind();
    }

    int indexInOut=0;
    private void sendInfoInOut() {
        try {
            if(executeFunction!=null){
                String msg ;
                MInfo inOutInfo = new MInfo();
                if(indexInOut%2==0){
                    msg ="有数据";
                    inOutInfo.setName("客户端inout");
                    inOutInfo.setAge(9999);
                }else{
                    msg ="无数据";
                }
                indexInOut++;
                printInfo("inout-"+msg+"-bef",inOutInfo);
                MInfo newInfo = executeFunction.getServiceInOut(inOutInfo);
                printInfo("inout-"+msg+"-aft",inOutInfo);
                printInfo("inout-"+msg+"-return",newInfo);

            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        unbind();
    }

    private void unbind() {
        index=0;
        unbindService(connection);
    }

    private void printInfo(String tag, MInfo info) {
        L(tag+"-msg:"+info.toString());
        L(tag+"-address:"+Integer.toHexString(info.hashCode()));
    }

 /*   static int i = 0;
    private void sendInfo() {
        MInfo info = new MInfo();
        if(i%2==0){
            info.setName("ssnb:"+i);
            info.setAge(20+i);
            L("有数据");
        }else {
            L("无数据");
        }
        i++;
        try {
            MInfo serviceInfo = executeFunction.getServiceInfo(info,callBack);
            printInfo("inout-info",info);
            MInfo inInfo = executeFunction.getServiceIn(info);
            printInfo("in-info",info);
            unbindService(connection);
            printInfo("inout-return",serviceInfo);
            printInfo("in-return",inInfo);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }*/

    ICallBack.Stub callBack = new ICallBack.Stub() {
        @Override
        public void sendMsg(String msg) throws RemoteException {
            Toast.makeText(MainActivity.this,"服务端返回:"+msg,Toast.LENGTH_SHORT).show();
        }
    };

    private void L(String msg){
        Log.e("客户端","=======>>>>>>>>>>>"+msg);
    }
}
