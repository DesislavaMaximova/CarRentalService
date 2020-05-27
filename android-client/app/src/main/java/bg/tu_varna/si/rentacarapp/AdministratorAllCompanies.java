package bg.tu_varna.si.rentacarapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import bg.tu.varna.si.model.Company;
import bg.tu.varna.si.model.CompanyList;
import bg.tu_varna.si.rentacarapp.repositories.CompanyService;
import bg.tu_varna.si.rentacarapp.viewModels.CompanyViewModel;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
