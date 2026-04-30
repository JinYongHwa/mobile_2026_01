package kr.ac.mjc.library;

import static android.view.View.GONE;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import kr.ac.mjc.library.dto.Notice;
import kr.ac.mjc.library.dto.NoticeResponse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends Fragment {

    RecyclerView noticeRv;
    NoticeAdapter noticeAdapter;
    ArrayList<Notice> noticeList=new ArrayList<>();
    Handler handler=new Handler();

    ProgressBar loadingPb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Progress 가져오기
        loadingPb=view.findViewById(R.id.loading_pb);

        //RecyclerView 초기화
        noticeRv=view.findViewById(R.id.notice_rv);
        noticeAdapter=new NoticeAdapter(noticeList);
        noticeRv.setAdapter(noticeAdapter);
        noticeRv.setLayoutManager(new LinearLayoutManager(view.getContext()));

        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .method("GET",null)
                .url("https://lib.mjc.ac.kr/pyxis-api/1/bulletin-boards/1/bulletins?max=20&offset=0")
                .build();

        client.newCall(request).enqueue(new Callback() {

            //오류가났을때(인터넷문제 혹은 서버문제)
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("HomeFragment",e.getMessage());
            }
            //결과가 제대로 왔을때
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String result=response.body().string();
                NoticeResponse noticeResponse=new Gson().fromJson(result, NoticeResponse.class);

                for(Notice notice:noticeResponse.getData().getList()){
                    Log.d("HomeFragment",notice.getTitle());
                }

                noticeList.addAll(noticeResponse.getData().getList());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        noticeAdapter.notifyDataSetChanged();
                        loadingPb.setVisibility(GONE);
                    }
                });


//
//                Log.d("HomeFragment",result);
            }
        });


    }
}
