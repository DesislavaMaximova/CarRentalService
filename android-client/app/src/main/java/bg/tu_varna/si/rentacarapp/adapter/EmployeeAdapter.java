package bg.tu_varna.si.rentacarapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

import bg.tu.varna.si.model.User;
import bg.tu.varna.si.model.UserList;
import bg.tu_varna.si.rentacarapp.CompanyEmployees;
import bg.tu_varna.si.rentacarapp.R;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeHolder> {
    private Context context;
    private List<User> employees;
    private CompanyAdapter.OnItemClickListener onItemClickListener;


    public void setOnItemClickListener(CompanyAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public EmployeeAdapter(Context context, List<User> employees) {
        this.context = context;
        this.employees = employees;
    }
    public void updateEmployeeList(List<User> newEmployees) {
        this.employees = newEmployees;
        this.notifyDataSetChanged();
    }
    @NonNull
    @Override
    public EmployeeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employees_item, parent, false);
        return new EmployeeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeHolder holder, int position) {
        User current = employees.get(position);
        String employeeFirstName = current.getFirstName();
        String employeeLastName = current.getLastName();
        String employeeEmail = current.getEmail();

        holder.textViewFirstName.setText(employeeFirstName);
        holder.textViewLastName.setText(employeeLastName);
        holder.textViewEmail.setText(employeeEmail);
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }


    public class EmployeeHolder extends RecyclerView.ViewHolder {
        public TextView textViewFirstName;
        public TextView textViewLastName;
        public TextView textViewEmail;

        public EmployeeHolder(@NonNull View itemView) {
            super(itemView);
            textViewFirstName = itemView.findViewById(R.id.employee_first_name);
            textViewLastName = itemView.findViewById(R.id.employee_last_name);
            textViewEmail = itemView.findViewById(R.id.employee_email);
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
