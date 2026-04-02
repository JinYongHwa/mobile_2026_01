package kc.ac.mjc.broadcast;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    BatteryReceiver batteryReceiver;
    ImageView imageIv;
    Button imagePickBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        imagePickBtn=findViewById(R.id.image_pick_btn);
        imageIv=findViewById(R.id.image_iv);
        //권한이 있는지 확인
        int status=checkSelfPermission(Manifest.permission.RECEIVE_SMS);
        if(status!=PERMISSION_GRANTED){ //사용자가 권한을 허용한 상태가 아닐때
            
            //사용자에게 권한 요청하기
            requestPermissions(new String[]{
                Manifest.permission.RECEIVE_SMS
            },1);
        }

        imagePickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //사진선택 클릭시
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,1000);
            }
        });



    }

    @Override
    protected void onStart() {      //앱이 시작할때 or 다시 활성화될때
        super.onStart();
        batteryReceiver=new BatteryReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver,intentFilter);
    }

    @Override
    protected void onPause() {      //잠깐 백그라운드로 앱이 갔을때
        super.onPause();
        unregisterReceiver(batteryReceiver);
    }

    class BatteryReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            int battery=intent.getIntExtra(BatteryManager.EXTRA_LEVEL,100);
            //배터리가 변경됐을때
            if(battery<50){
                Toast.makeText(context,"배터리가 부족합니다 "+battery+"%", Toast.LENGTH_SHORT).show();
            }

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //사진선택버튼을 클릭하고나서 사용자가 사진을 선택 했을때만 처리
        if(requestCode==1000&&resultCode==RESULT_OK){
            Uri imagePath=data.getData();   //사용자가 선택한 이미지의 경로
            imageIv.setImageURI(imagePath);
        }
    }
}