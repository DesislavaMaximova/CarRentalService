package bg.tu_varna.si.rentacarapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import bg.tu.varna.si.model.Car;
import bg.tu.varna.si.model.Contract;
import bg.tu_varna.si.rentacarapp.R;

public class ContractAdapter extends RecyclerView.Adapter<ContractAdapter.ContractViewHolder> {
    private Context context;
    private List<Contract> contracts;
    private CompanyAdapter.OnItemClickListener onItemClickListener;


    public void setOnItemClickListener(CompanyAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ContractAdapter(Context context, List<Contract> contracts) {
        this.context = context;
        this.contracts = contracts;
    }

//    public void updateContractList(List<Contract> newContractList) {
//        contracts = newContractList;
//        notifyDataSetChanged();
//    }

    @NonNull
    @Override
    public ContractAdapter.ContractViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contract_item, parent, false);
        return new ContractViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContractAdapter.ContractViewHolder holder, int position) {
        Contract currentContract = contracts.get(position);
        holder.contractNumber.setText(String.valueOf(currentContract.getId()));
        holder.carRegistrationNumber.setText(currentContract.getCar().getRegNumber());
        holder.clientName.setText(currentContract.getClient().getFirstName() + currentContract.getClient().getLastName());
        holder.endDate.setText(currentContract.getEnd().toString());
        holder.startDate.setText(currentContract.getStart().toString());
    }

    @Override
    public int getItemCount() {
        return contracts.size();
    }


    public class ContractViewHolder extends RecyclerView.ViewHolder {
        public TextView contractNumber;
        public TextView startDate;
        public TextView clientName;
        public TextView carRegistrationNumber;
        public TextView endDate;

        public ContractViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.findViewById(R.id.contract_number_view);
            itemView.findViewById(R.id.client_name_text);
            itemView.findViewById(R.id.car_text);
            itemView.findViewById(R.id.item_start);
            itemView.findViewById(R.id.item_end);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }
}
