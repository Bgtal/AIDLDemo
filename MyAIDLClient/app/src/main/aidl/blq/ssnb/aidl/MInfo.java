package blq.ssnb.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作者: SSNB
 * 日期: 2016/8/22
 * 描述:
 * 添加描述
 * ================================================
 */
public class MInfo implements Parcelable {

    private String name;
    private int age;

    public MInfo(){

    }
    protected MInfo(Parcel in) {
        readFromParcel(in);
    }

    public static final Creator<MInfo> CREATOR = new Creator<MInfo>() {
        @Override
        public MInfo createFromParcel(Parcel in) {
            return new MInfo(in);
        }

        @Override
        public MInfo[] newArray(int size) {
            return new MInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "MInfo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
    public void readFromParcel(Parcel parcel){
        name = parcel.readString();
        age = parcel.readInt();
    }
}
