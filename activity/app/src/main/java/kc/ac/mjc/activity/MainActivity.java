package kc.ac.mjc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Button startBtn=findViewById(R.id.start_btn);

        //버튼 클릭
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameEt=findViewById(R.id.name_et);
                String name=nameEt.getText().toString();    //사용자가 입력한 값 저장
                Log.d("MainActivity",name);
                Intent intent=new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("name",name);   //intent에 name 이라는 키로 값 저장
//                startActivity(intent);
                startActivityForResult(intent,1000);
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.d("MainActivity","onCreate()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity","onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity","onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity","onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity","onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity","onDestroy()");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){  //SecondActivity 에대한 결과 값이면
            if(resultCode==RESULT_OK){
                String rename=data.getStringExtra("rename");    //SecondActivity 에서 보내준 이름
                EditText nameEt=findViewById(R.id.name_et);
                nameEt.setText(rename); //변경된 이름으로 변경
            }
        }
    }
}