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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;
import java.util.List;

import bg.tu.varna.si.model.Car;
import bg.tu.varna.si.model.Client;
import bg.tu_varna.si.rentacarapp.activities.CarNew;
import bg.tu_varna.si.rentacarapp.activities.Client_new;
import bg.tu_varna.si.rentacarapp.adapter.CarAdapter;
import bg.tu_varna.si.rentacarapp.adapter.ClientAdapter;
import bg.tu_varna.si.rentacarapp.adapter.CompanyAdapter;
import bg.tu_varna.si.rentacarapp.service.CompanyId;
import bg.tu_varna.si.rentacarapp.viewModels.CarViewModel;
import bg.tu_varna.si.rentacarapp.viewModels.ClientViewModel;

public class Clients extends AppCompatActivity implements CompanyAdapter.OnItemClickListener {
    public static final String EXTRA_CLIENT_ID = "clientId";
    private RecyclerView clientsRecycleView;
    private ClientViewModel clientViewModel;
    private ClientAdapter clientAdapter;
    List<Client> clients = new LinkedList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients);
        FloatingActionButton fabAdd = findViewById(R.id.fab_recycleView_clients);

        clientsRecycleView = findViewById(R.id.recyclerView_clients);
        clientsRecycleView.setHasFixedSize(true);
        clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);
        clientViewModel.init(CompanyId.getCompanyId());

        clientViewModel.getAllClientsObservable().observe(this,response ->{
            clients.clear();
            clients.addAll(response.getClients());
            clientAdapter.notifyDataSetChanged();
        });
        setUpRecycleView();

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Clients.this, Client_new.class);
                startActivity(intent);
            }
        });
    }

    private void setUpRecycleView() {
        if (clientAdapter == null) {
            clientAdapter = new ClientAdapter(Clients.this, clients);
            clientsRecycleView.setLayoutManager(new LinearLayoutManager(this));
            clientsRecycleView.setAdapter(clientAdapter);
            clientsRecycleView.setItemAnimator(new DefaultItemAnimator());
            clientsRecycleView.setNestedScrollingEnabled(true);
            clientAdapter.setOnItemClickListener(Clients.this);
        } else {
            clientAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(int position) {
        Client clickedClient = clients.get(position);
        Log.d("Clicked client:", String.valueOf(clickedClient));
        Intent intent = new Intent(Clients.this, Client_new.class);
        intent.putExtra(EXTRA_CLIENT_ID,clickedClient.getId());
        Log.d("Clients - clientId: ", String.valueOf(clickedClient.getId()));
        startActivity(intent);
    }
}
