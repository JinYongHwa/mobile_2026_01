package com.example.list;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView postRv;
    PostAdapter postAdapter;
    ArrayList<Post> posts=new ArrayList<Post>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        for(int i=0;i<100;i++){
            Post post=new Post();
            post.setName("홍길동"+i);
            post.setBody("안녕하세요"+i);
            post.setPostUrl("https://picsum.photos/500/500");
            post.setProfileUrl("https://picsum.photos/100/100");
            posts.add(post);
        }
        postRv=findViewById(R.id.post_rv);
        postAdapter=new PostAdapter(posts);
        postAdapter.setOnPostClickListener(new PostAdapter.OnPostClickListener() {
            @Override
            public void onPostNameClick(Post post) {
                Toast.makeText(MainActivity.this,post.getName(),Toast.LENGTH_SHORT).show();
            }
        });
        
        postRv.setAdapter(postAdapter); //RecyclerView 에 adapter 설정
        postRv.setLayoutManager(new LinearLayoutManager(this));
    }
}