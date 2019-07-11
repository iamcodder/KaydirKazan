package com.patronusstudio.kaydirkazan.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.patronusstudio.kaydirkazan.R;

import java.util.List;

public class SoruActivityAdapterJ extends RecyclerView.Adapter<SoruActivityAdapterJ.viewHolderJ> {

    private List<soruModelJ> list;
    private Context mContext;

    public SoruActivityAdapterJ(List<soruModelJ> list,Context mContext){
        this.list=list;
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public viewHolderJ onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View rootView= LayoutInflater.from(mContext).inflate(R.layout.soru_karti,parent,false);

        return new viewHolderJ(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderJ holder, int position) {

        holder.soru_kartı_ana.setText(list.get(position).getSoru());
        holder.soru_kartı_sag.setText(list.get(position).getSagCevap());
        holder.soru_kartı_sol.setText(list.get(position).getSolCevap());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewHolderJ extends RecyclerView.ViewHolder {

        TextView soru_kartı_ana,soru_kartı_sol,soru_kartı_sag;

        viewHolderJ(@NonNull View itemView) {
            super(itemView);
            soru_kartı_ana=itemView.findViewById(R.id.soru_karti_tasarimi_ANA_SORU);
            soru_kartı_sol=itemView.findViewById(R.id.soru_karti_tasarimi_SOL_CEVAP);
            soru_kartı_sag=itemView.findViewById(R.id.soru_karti_tasarimi_SAG_CEVAP);
        }
    }
}
