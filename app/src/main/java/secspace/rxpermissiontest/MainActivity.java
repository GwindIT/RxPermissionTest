package secspace.rxpermissiontest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    Button mBtnGetIMEI;

    TextView mTvShowIEMI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnGetIMEI = findViewById(R.id.btn_get_imei);
        mTvShowIEMI = findViewById(R.id.tv_imei);

        mBtnGetIMEI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
            }
        });
    }


    private void test() {
        Disposable subscribe = RxPermissions.getInstance(this).request(new String[]{"android.permission.READ_PHONE_STATE", "android.permission.WRITE_SECURE_SETTINGS"}).subscribe(new Consumer<Boolean>() {
            public void accept(Boolean bool) {
                String imei = getIMEI(MainActivity.this);
                mTvShowIEMI.setText(imei);
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) {
                String imei = getIMEI(MainActivity.this);
                mTvShowIEMI.setText(imei);
            }
        });
    }


    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String imei = telephonyManager.getDeviceId();
        System.out.println("iMei:" + imei);
        return imei;
    }
}
