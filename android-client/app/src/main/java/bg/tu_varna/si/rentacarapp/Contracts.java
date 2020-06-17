package bg.tu_varna.si.rentacarapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;
import java.util.List;

import bg.tu.varna.si.model.Contract;
import bg.tu_varna.si.rentacarapp.activities.ContractNew;
import bg.tu_varna.si.rentacarapp.activities.MainActivity;
import bg.tu_varna.si.rentacarapp.activities.UpdateContract;
import bg.tu_varna.si.rentacarapp.adapter.ClientAdapter;
import bg.tu_varna.si.rentacarapp.adapter.CompanyAdapter;
import bg.tu_varna.si.rentacarapp.adapter.ContractAdapter;
import bg.tu_varna.si.rentacarapp.repositories.ContractRepository;
import bg.tu_varna.si.rentacarapp.service.CompanyId;
import bg.tu_varna.si.rentacarapp.viewModels.ClientViewModel;
import bg.tu_varna.si.rentacarapp.viewModels.ContractViewModel;

public class Contracts extends AppCompatActivity implements CompanyAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private ContractViewModel contractViewModel;
    private ContractAdapter contractAdapter;
    List<Contract> contracts = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contracts);
        FloatingActionButton fabAdd = findViewById(R.id.fab_recycleView_contracts);
        recyclerView = findViewById(R.id.recyclerView_contracts);
        recyclerView.hasFixedSize();
        contractViewModel = new ViewModelProvider(this).get(ContractViewModel.class);
        contractViewModel.init(CompanyId.getCompanyId());
        contractViewModel.getAllContactsObservable().observe(this, response -> {
            contracts.clear();
            contracts.addAll(response.getContracts());
            Log.d("Contracts count:", String.valueOf(contracts.size()));
            contractAdapter.notifyDataSetChanged();
        });
        setUpRecycleView();

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Contracts.this, ContractNew.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onItemClick(int position) {
        Contract clickedContract = contracts.get(position);
        Intent intent = new Intent(Contracts.this, UpdateContract.class);
        intent.putExtra("contractId", clickedContract.getId());
        startActivity(intent);

    }

    private void setUpRecycleView() {
        if (contractAdapter == null) {
            contractAdapter = new ContractAdapter(Contracts.this, contracts);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(contractAdapter);
            recyclerView.setNestedScrollingEnabled(true);
            contractAdapter.setOnItemClickListener(Contracts.this);
        } else {
            contractAdapter.notifyDataSetChanged();
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        int id = item.getItemId();
        if (id == R.id.menu_logout) {
            intent = new Intent(this, MainActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }
}