package bg.tu_varna.si.rentacarapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;
import java.util.List;

import bg.tu.varna.si.model.Company;
import bg.tu_varna.si.rentacarapp.activities.CompanyNew;
import bg.tu_varna.si.rentacarapp.adapter.CompanyAdapter;
import bg.tu_varna.si.rentacarapp.viewModels.CompanyViewModel;

public class AdministratorAllCompanies extends AppCompatActivity implements CompanyAdapter.OnItemClickListener {
    public static final String EXTRA_COMPANY_ID = "companyId";
    //    private String url = Constants.BACKEND_ADDRESS + Constants.ADMIN + "companies";
    private RecyclerView recyclerView;
    private List<Company> companyList = new LinkedList<>();
    private CompanyAdapter companyAdapter;
    private CompanyViewModel companyViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerView);
        companyViewModel = new ViewModelProvider(this).get(CompanyViewModel.class);
        companyViewModel.init();
        companyViewModel.getCompanyRepository().observe(this, response -> {

            companyList.addAll(response.getListCompany());
            companyAdapter.notifyDataSetChanged();

            Log.d("DEBUG", "" + companyAdapter.getItemCount());


        });
        setUpRecyclerView();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdministratorAllCompanies.this, CompanyNew.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Company clickedCompany = companyList.get(position);

        Intent intent = new Intent(this, CompanyEmployees.class);
        intent.putExtra(EXTRA_COMPANY_ID, clickedCompany.getId());
        startActivity(intent);

    }

    void setUpRecyclerView() {
        if (companyAdapter == null) {
            companyAdapter = new CompanyAdapter(AdministratorAllCompanies.this, companyList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(companyAdapter);
            // recyclerView.setItemAnimator(new DefaultItemAnimator());
            //recyclerView.setNestedScrollingEnabled(true);
            companyAdapter.setOnItemClickListener(AdministratorAllCompanies.this);
        } else {
            companyAdapter.notifyDataSetChanged();
        }
    }


}
