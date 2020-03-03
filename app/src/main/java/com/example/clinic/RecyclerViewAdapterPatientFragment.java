package com.example.clinic;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterPatientFragment extends RecyclerView.Adapter<RecyclerViewAdapterPatientFragment.ViewHolder> {

    Context context;
    ArrayList<String> doctoremail=new ArrayList<>();
    ArrayList<String> doctorname=new ArrayList<>();
    ArrayList<String> slotarray=new ArrayList<>();
    ArrayList<String> status=new ArrayList<>();
    ArrayList<String> datearry=new ArrayList<>();

    public RecyclerViewAdapterPatientFragment(Context context, ArrayList<String> doctoremail, ArrayList<String> doctorname, ArrayList<String> datearry, ArrayList<String> slotarray, ArrayList<String> status) {
    this.context=context;
    this.doctoremail=doctoremail;
    this.doctorname=doctorname;
    this.slotarray=slotarray;
    this.datearry=datearry;
    this.status=status;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patientfragmemt_listview, parent, false);

        RecyclerViewAdapterPatientFragment.ViewHolder viewHolder = new RecyclerViewAdapterPatientFragment.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.doctorid.setText(doctoremail.get(position));
        holder.doctornametext.setText(doctorname.get(position));
        holder.datetext.setText(datearry.get(position));
        holder.slottext.setText(slotarray.get(position));
        if(status.get(position).equals("requested"))
            holder.statustext.setTextColor(Color.RED);
        else
            if (status.get(position).equals("confirm"))
                holder.statustext.setTextColor(Color.GREEN);

        holder.statustext.setText(status.get(position));

    }

    @Override
    public int getItemCount() {
        return status.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

TextView doctorid,doctornametext,datetext,slottext,statustext;

        public ViewHolder(View itemView) {
            super(itemView);

            doctorid=itemView.findViewById(R.id.patientlist_doctoremial);
            doctornametext=itemView.findViewById(R.id.patientlist_doctorname);
            datetext=itemView.findViewById(R.id.patientlist_date);
            slottext=itemView.findViewById(R.id.patientlist_slot);
            statustext=itemView.findViewById(R.id.patientlist_status);
        }

    }

}
