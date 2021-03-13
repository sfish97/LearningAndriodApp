package edu.asu.bsse.sfishbou.lab7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//help: https://www.youtube.com/watch?v=18VcnYN5_LM
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>{

    private Context context;
    private String[] list;
    private String[] values;

    public MyRecyclerAdapter(Context context, String[] list, String[] values){
        this.context = context;
        this.list = list;
        this.values = values;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(list[position]);
        holder.value.setText(values[position]);
    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder {
        TextView title;
        TextView value;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            this.title = (TextView) itemView.findViewById(R.id.recyclerTitle);
            this.value = (TextView) itemView.findViewById(R.id.recyclerTitleValue);
        }
    }
}
