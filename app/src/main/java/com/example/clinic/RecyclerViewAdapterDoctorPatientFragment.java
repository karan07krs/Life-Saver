package com.example.clinic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterDoctorPatientFragment extends RecyclerView.Adapter<RecyclerViewAdapterDoctorPatientFragment.ViewHolder> {

    Context context;

    public DetailsAdapterListener onClickListener;
    ArrayList<String> patientemail=new ArrayList<>();
    ArrayList<String> patientname=new ArrayList<>();
    ArrayList<String> slotarray=new ArrayList<>();
    ArrayList<String> datearry=new ArrayList<>();

    public RecyclerViewAdapterDoctorPatientFragment(DetailsAdapterListener listener,Context context, ArrayList<String> patientemail, ArrayList<String> patientname, ArrayList<String> datearry, ArrayList<String> slotarray) {
        this.context=context;
        this.patientemail=patientemail;
        this.patientname=patientname;
        this.slotarray=slotarray;
        this.datearry=datearry;
        this.onClickListener = listener;
    }

    @Override
    public RecyclerViewAdapterDoctorPatientFragment.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctorpatientfragment_listview, parent, false);

        RecyclerViewAdapterDoctorPatientFragment.ViewHolder viewHolder = new RecyclerViewAdapterDoctorPatientFragment.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterDoctorPatientFragment.ViewHolder holder, int position) {

        holder.patientid.setText(patientemail.get(position));
        holder.patientnametext.setText(patientname.get(position));
        holder.datetext.setText(datearry.get(position));
        holder.slottext.setText(slotarray.get(position));

    }

    @Override
    public int getItemCount() {
        return patientemail.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        TextView patientid,patientnametext,datetext,slottext,confirmbutton;

        public ViewHolder(View itemView) {
            super(itemView);

            patientid=itemView.findViewById(R.id.patientlist_doctoremial);
            patientnametext=itemView.findViewById(R.id.patientlist_doctorname);
            datetext=itemView.findViewById(R.id.patientlist_date);
            slottext=itemView.findViewById(R.id.patientlist_slot);
            confirmbutton=itemView.findViewById(R.id.confirm_request);
            confirmbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(v, getAdapterPosition());
                }
            });

        }



    }
    public interface DetailsAdapterListener {

        void onClick(View v, int position);


    }


}
