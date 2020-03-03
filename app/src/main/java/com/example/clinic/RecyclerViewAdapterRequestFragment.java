package com.example.clinic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterRequestFragment extends RecyclerView.Adapter<RecyclerViewAdapterRequestFragment.ViewHolder> {
    private static ClickListener clickListener;
    Context context;
    ArrayList<doctor> docinfo=new ArrayList<>();

    public RecyclerViewAdapterRequestFragment(Context context,ArrayList<doctor> docinfo) {
    this.context=context;
    this.docinfo=docinfo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.requestfragment_doctorlist, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.name.setText(docinfo.get(position).getFname().toUpperCase()+" "+docinfo.get(position).getLname().toUpperCase());
        holder.email.setText(docinfo.get(position).getEmail().toString());
        holder.phoneno.setText("+91"+ docinfo.get(position).getPhoneno().toString());
        holder.specialization.setText(docinfo.get(position).getSpecialist().toString());


    }

    @Override
    public int getItemCount() {
        return docinfo.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
    TextView name,email,phoneno,specialization;

        public ViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name_requestfragment);
            email=itemView.findViewById(R.id.email_requestfragment);
            phoneno=itemView.findViewById(R.id.phonenumber_requestfragment);
            specialization=itemView.findViewById(R.id.specialist_requestfragment);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onItemLongClick(getAdapterPosition(), view);
            return false;
        }
    }
    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
    public void setOnItemClickListener(ClickListener clickListener) {
        RecyclerViewAdapterRequestFragment.clickListener = clickListener;
    }

}
