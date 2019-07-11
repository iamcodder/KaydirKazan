package com.patronusstudio.kaydirkazan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.patronusstudio.kaydirkazan.Model.userModelJ;
import com.patronusstudio.kaydirkazan.R;

import java.util.List;

public class SiralamaAdapter extends RecyclerView.Adapter<SiralamaAdapter.viewHolder> {

    private Context mContext;
    private List<userModelJ> list;

    SiralamaAdapter(Context mContext,List<userModelJ> list){
        this.mContext=mContext;
        this.list=list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View rootView= LayoutInflater.from(mContext).inflate(R.layout.act_siralama_ogeleri,parent,false);

        return new viewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{

        private ImageView kisi_resmi;
        private TextView kisi_ismi,kisi_puani;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            kisi_resmi=itemView.findViewById(R.id.act_siralama_ogeleri_resim);
            kisi_ismi=itemView.findViewById(R.id.act_siralama_ogeleri_isim);
            kisi_puani=itemView.findViewById(R.id.act_siralama_ogeleri_puan);
        }
    }
}
