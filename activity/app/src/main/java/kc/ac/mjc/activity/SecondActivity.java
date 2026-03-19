package kc.ac.mjc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.d("SecondActivity","onCreate()");
        Intent intent=getIntent();  //MainActivity 에서 보낸 intent 받아옴
        String name=intent.getStringExtra("name");  //MainActivity 에서 넣은 이름 꺼내기
        TextView nameTv=findViewById(R.id.name_tv);
        nameTv.setText(name);

        Button backBtn=findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        EditText renameEt=findViewById(R.id.rename_et);
        String rename=renameEt.getText().toString(); //변경하고자 하는 이름
        Intent intent = new Intent();
        intent.putExtra("rename",rename);
        setResult(RESULT_OK,intent);    //액티비티의 결과값을 저장
        finish();   //액티비티 종료
    }
}
