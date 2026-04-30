package kr.ac.mjc.library;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kr.ac.mjc.library.dto.Notice;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {

    ArrayList<Notice> noticeList;

    public NoticeAdapter(ArrayList<Notice> noticeList){
        this.noticeList=noticeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //레이아웃 뭐가져올건지
        View view=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notice,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //레이아웃에 데이터 넣기
        Notice notice=noticeList.get(position);
        holder.bind(notice);
    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTv;
        TextView dateTv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv=itemView.findViewById(R.id.title_tv);
            dateTv=itemView.findViewById(R.id.date_tv);
        }
        public void bind(Notice notice){
            titleTv.setText(notice.getTitle());
            dateTv.setText(notice.getDateCreated());
        }
    }
}
