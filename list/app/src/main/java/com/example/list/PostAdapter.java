package com.example.list;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    ArrayList<Post> posts=new ArrayList<Post>();    //RecyclerView가 그려야되는 데이터
    OnPostClickListener listener;

    public PostAdapter(ArrayList<Post> posts){
        this.posts=posts;
    }
    public void setOnPostClickListener(OnPostClickListener listener){
        this.listener=listener;
    }




    //ViewHolder를 생성한다
    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("PostAdapter","onCreateViewHolder");
        //item_post.xml 레이아웃 가져오기
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }
    
    //Post 클래스에 있는 데이터를 item_post.xml 에 설정한다
    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        Log.d("PostAdapter","onBindViewHolder"+position);
        Post post=posts.get(position);
        holder.bind(post);
    }

    //RecyclerView 가 몇개의 데이터를 그려야되는지 반환
    @Override
    public int getItemCount() {
        Log.d("PostAdapter","getItemCount");
        return posts.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profileIv;
        TextView nameTv;
        ImageView postIv;
        TextView bodyTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileIv=itemView.findViewById(R.id.profile_iv);
            nameTv=itemView.findViewById(R.id.name_tv);
            postIv=itemView.findViewById(R.id.post_iv);
            bodyTv=itemView.findViewById(R.id.body_tv);

        }
        public void bind(Post post){    //레이아웃에 데이터를 연결한다
            nameTv.setText(post.getName());
            bodyTv.setText(post.getBody());
            Glide.with(postIv.getContext())
                    .load(post.getPostUrl())    //URL 의 이미지를 가져와서
                    .into(postIv);              //postIv 에 그린다

            Glide.with(profileIv.getContext())
                    .load(post.getProfileUrl())
                    .into(profileIv);
            nameTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onPostNameClick(post);
                }
            });
        }
    }


    interface OnPostClickListener{
        public void onPostNameClick(Post post);
    }

}
