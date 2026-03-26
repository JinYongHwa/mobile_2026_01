package kc.ac.mjc.background;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;       //다운로드 상황 보여주는 위젯

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        progressBar=findViewById(R.id.progress_bar);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //권한 체크하기
        int status=checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS);
        if (status != PackageManager.PERMISSION_GRANTED) {  //사용자가 권한 동의 한적 없을때
            //백그라운드 작업 알림 동의받기
            requestPermissions(new String[]{
                    Manifest.permission.POST_NOTIFICATIONS
            },1000);
        }



        //다운로드버튼 레이아웃에서 가져오기
        Button downloadBtn=findViewById(R.id.download_btn);
        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   //다운로드 버튼 클릭시 실행
                Intent intent=new Intent(MainActivity.this,DownloadService.class);
                startService(intent);   //DownloadService 실행
                finish();   //현재의 화면 종료
            }
        });

        //방송을 구독하도록 하는 코드
        DownloadReceiver receiver=new DownloadReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction("download_progress");      //download_progress 방송만 듣도록 필터링
        registerReceiver(receiver,filter,Context.RECEIVER_NOT_EXPORTED);
    }

    class DownloadReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            int progress=intent.getIntExtra("progress",0);
            Log.d("MainActivity","progress:"+progress);
            progressBar.setProgress(progress);
        }
    }
}