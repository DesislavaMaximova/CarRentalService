package bg.tu_varna.si.rentacarapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

import bg.tu.varna.si.model.Car;
import bg.tu.varna.si.model.Company;
import bg.tu.varna.si.model.User;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyHolder> {


    private Context context;

    private List<Company> companies = new LinkedList<>();
    private OnItemClickListener onItemClickListener;

    interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CompanyAdapter(Context context, List<Company> companies) {
        this.context = context;
        this.companies = companies;
    }

    @NonNull
    @Override
    public CompanyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new CompanyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyHolder holder, int position) {
        Company current = companies.get(position);
        String companyName = current.getName();
        int carCount = 0;
        int emplCount = 0;
        if (current.getCars() == null) {
            carCount = 0;
        } else {
            List<Car> cars = current.getCars();
            carCount = cars.size();
        }
        if (current.getEmployees() == null) {
            emplCount = 0;
        } else {
            List<User> employees = current.getEmployees();
            emplCount = employees.size();
        }
        holder.textViewCompanyName.setText(companyName);
        holder.textViewFleetCount.setText("Fleet: " + carCount);
        holder.textViewEmployeesCount.setText("Employees: " + emplCount);
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }

    public class CompanyHolder extends RecyclerView.ViewHolder {
        public TextView textViewCompanyName;
        public TextView textViewFleetCount;
        public TextView textViewEmployeesCount;

        public CompanyHolder(@NonNull View itemView) {
            super(itemView);
            textViewCompanyName = itemView.findViewById(R.id.company_name_text);
            textViewFleetCount = itemView.findViewById(R.id.fleet_count_text);
            textViewEmployeesCount = itemView.findViewById(R.id.employees_count);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null){
                        int position = getAdapterPosition();
                        if(position !=RecyclerView.NO_POSITION) {
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            });
        }

    }
}
