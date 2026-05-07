package kr.ac.mjc.library;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import kr.ac.mjc.library.dto.Book;
import kr.ac.mjc.library.dto.BookResponse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchFragment extends Fragment {

    ArrayList<Book> books=new ArrayList<>();
    BookAdapter adapter;
    RecyclerView bookRv;
    Handler handler=new Handler();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText keywordEt=view.findViewById(R.id.keyword_et);
        Button searchBtn=view.findViewById(R.id.search_btn);
        bookRv=view.findViewById(R.id.book_rv);
        adapter=new BookAdapter(books);
        bookRv.setAdapter(adapter);
        bookRv.setLayoutManager(new LinearLayoutManager(getContext()));

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //검색 버튼 클릭시
                String keyword=keywordEt.getText().toString();
                if(keyword.equals("")){ //검색어를 입력하지 않았을때
                    Toast.makeText(getContext(),"검색어를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                search(keyword);
            }
        });
    }
    //실제 검색 함수
    public void search(String keyword){
        OkHttpClient client=new OkHttpClient();
        String url="https://lib.mjc.ac.kr/pyxis-api/1/collections/1/search?all=k|a|"+keyword+"&abc=";
        Request request=new Request.Builder()
                .url(url)
                .method("GET",null)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String result=response.body().string();
                BookResponse bookResponse=new Gson().fromJson(result, BookResponse.class);
                for(Book book:bookResponse.getData().getList()){
                    Log.d("SearchFragment",book.getTitleStatement());
                }
                books.clear();
                books.addAll(bookResponse.getData().getList());
                //메인스레드에서 리사이클러뷰 다시그리기
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });

                Log.d("SearchFragment",result);
            }
        });
    }
}
