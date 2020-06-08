package bg.tu_varna.si.rentacarapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import bg.tu.varna.si.model.Client;
import bg.tu.varna.si.model.User;
import bg.tu_varna.si.rentacarapp.R;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewHolder> {
    private Context context;
    private List<Client> clients;
    private CompanyAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(CompanyAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ClientAdapter(Context context, List<Client> clients) {
        this.context = context;
        this.clients = clients;

    }

    public void updateClientList(List<Client> newClientList) {
        this.clients = newClientList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employees_item, parent, false);
        return new ClientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {
        Client currentClient = clients.get(position);
        String firstName = currentClient.getFirstName();
        String lastName = currentClient.getLastName();
        String phone = currentClient.getTelephone();
        float rating = currentClient.getRating();

        holder.clientName.setText(firstName + " " + lastName);
        holder.phone.setText(phone);
        holder.ratingBar.setRating(rating);
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

    public class ClientViewHolder extends RecyclerView.ViewHolder {
        public TextView clientName;
        public TextView phone;
        public RatingBar ratingBar;

        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);
            clientName = itemView.findViewById(R.id.view_client_name);
            phone = itemView.findViewById(R.id.view_telephone);
            ratingBar = itemView.findViewById(R.id.view_client_rating);
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
