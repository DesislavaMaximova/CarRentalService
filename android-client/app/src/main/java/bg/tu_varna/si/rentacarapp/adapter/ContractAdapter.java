package bg.tu_varna.si.rentacarapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import bg.tu.varna.si.model.Contract;
import bg.tu_varna.si.rentacarapp.R;

public class ContractAdapter extends RecyclerView.Adapter<ContractAdapter.ContractViewHolder> {
    public static final String DATE_PATTERN = "dd/MM/yyyy";
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

    public void updateContractList(List<Contract> newContractList) {
        contracts = newContractList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ContractViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contract_item, parent, false);
        return new ContractViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContractAdapter.ContractViewHolder holder, int position) {
        Contract currentContract = contracts.get(position);
        Long contractId = currentContract.getId();
        String contractRegNum = currentContract.getCar().getRegNumber();
        String clientName = currentContract.getClient().getFirstName() + " " + currentContract.getClient().getLastName();
        String startDate = new SimpleDateFormat(DATE_PATTERN).format(currentContract.getStart());
        String endDate = new SimpleDateFormat(DATE_PATTERN).format(currentContract.getEnd());
        holder.contractNumber.setText(contractId.toString());
        holder.carRegistrationNumber.setText(contractRegNum);
        holder.clientName.setText(clientName);
        holder.endDate.setText(endDate);
        holder.startDate.setText(startDate);
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
            contractNumber = itemView.findViewById(R.id.contract_number_view);
            clientName = itemView.findViewById(R.id.client_name_text);
            carRegistrationNumber = itemView.findViewById(R.id.car_text);
            startDate = itemView.findViewById(R.id.item_start);
            endDate = itemView.findViewById(R.id.item_end);

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
