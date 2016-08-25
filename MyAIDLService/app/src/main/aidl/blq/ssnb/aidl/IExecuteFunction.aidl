// IExecuteFunction.aidl
package blq.ssnb.aidl;

// Declare any non-default types here with import statements
import blq.ssnb.aidl.ICallBack;
import blq.ssnb.aidl.MInfo;

interface IExecuteFunction {
    MInfo getServiceIn(in MInfo info);
    MInfo getServiceOut(out MInfo info);
    MInfo getServiceInOut(inout MInfo info);
}
