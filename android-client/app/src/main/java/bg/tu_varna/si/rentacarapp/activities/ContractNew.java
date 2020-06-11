package bg.tu_varna.si.rentacarapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import bg.tu.varna.si.model.Car;
import bg.tu.varna.si.model.CarCategory;
import bg.tu.varna.si.model.CarList;
import bg.tu.varna.si.model.CarStatus;
import bg.tu.varna.si.model.CarType;
import bg.tu.varna.si.model.Client;
import bg.tu.varna.si.model.Contract;
import bg.tu.varna.si.model.Status;
import bg.tu.varna.si.model.User;
import bg.tu_varna.si.rentacarapp.R;
import bg.tu_varna.si.rentacarapp.service.ApiService;
import bg.tu_varna.si.rentacarapp.service.CompanyId;
import bg.tu_varna.si.rentacarapp.service.JwtHandler;
import bg.tu_varna.si.rentacarapp.service.RetrofitService;
import bg.tu_varna.si.rentacarapp.viewModels.CarViewModel;
import bg.tu_varna.si.rentacarapp.viewModels.ClientViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContractNew extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private final Calendar calendar = Calendar.getInstance();
    ApiService apiService;
    ArrayAdapter<Client> clientAdapter;
    ArrayAdapter<Car> carAdapter;
    List<Car> cars = new LinkedList<>();
    List<Client> clients = new LinkedList<>();
    private CarViewModel carViewModel;
    private ClientViewModel clientViewModel;
    TextView operatorTextView;
    private User user;
    String operatorName;
    EditText startEditText;
    EditText endEditText;
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
    Spinner clientSpinner;
    Spinner carSpinner;
    Client selectedClient;
    Car selectedCar;
    EditText endReportEditText;
    EditText startReportEditText;
    EditText priceEditText;
    double carPrice;
    CheckBox outsideDamage;
    CheckBox insideDamage;
    CheckBox engineDamage;
    CheckBox transmissionDamage;
    double contractPrice;
    long contractId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_new);
        long userId = MainActivity.userId;
        Log.d("Username: ", String.valueOf(userId));

        operatorTextView = findViewById(R.id.contract_operator);
        carSpinner = findViewById(R.id.contract_car);
        clientSpinner = findViewById(R.id.contract_client);
        startEditText = findViewById(R.id.start_date);
        startReportEditText = findViewById(R.id.start_report);
        endEditText = findViewById(R.id.end_date);
        endReportEditText = findViewById(R.id.end_status);
        priceEditText = findViewById(R.id.final_price);
        insideDamage = findViewById(R.id.check_contract_insideDamage);
        outsideDamage = findViewById(R.id.check_contract_outsideDamage);
        engineDamage = findViewById(R.id.check_contract_engineDamage);
        transmissionDamage = findViewById(R.id.check_contract_TransmissionDamage);

        FloatingActionButton fabCheck = findViewById(R.id.fab_contract_check);
        FloatingActionButton fabBack = findViewById(R.id.fab_contract_back);
        apiService = RetrofitService.cteateService(ApiService.class);
        Intent intent = getIntent();
        contractId  = intent.getLongExtra("contractId", -1);


        getOperator(userId);

        carViewModel = new ViewModelProvider(this).get(CarViewModel.class);
        carViewModel.init(CompanyId.getCompanyId());
        Log.d("CompanyId: ", String.valueOf(CompanyId.getCompanyId()));
        carViewModel.getAllCarsObservable().observe(this, response -> {
            cars.clear();
            cars.addAll(response.getCars());
            Log.d("Cars count: ", String.valueOf(cars.size()));
            carAdapter.notifyDataSetChanged();
        });

        carAdapter = new ArrayAdapter<Car>(this, android.R.layout.simple_spinner_item, cars);
        carAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carSpinner.setAdapter(carAdapter);
        carSpinner.setOnItemSelectedListener(this);
        Log.d("SelectedCar", String.valueOf(carSpinner.getSelectedItem()));

        clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);
        clientViewModel.init(CompanyId.getCompanyId());
        Log.d("CompanyId: ", String.valueOf(CompanyId.getCompanyId()));
        clientViewModel.getAllClientsObservable().observe(this, response -> {
            clients.clear();
            clients.addAll(response.getClients());
            Log.d("Clients count: ", String.valueOf(cars.size()));
            clientAdapter.notifyDataSetChanged();
        });
        clientAdapter = new ArrayAdapter<Client>(this, android.R.layout.simple_list_item_1, clients);
        clientSpinner.setAdapter(clientAdapter);
        clientSpinner.setOnItemSelectedListener(this);


        startEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(ContractNew.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                calendar.set(year, month, day);
                                String date = new SimpleDateFormat("MM/dd/yyyy").format(calendar.getTime());
                                startEditText.setText(date);
                            }
                        }, year, month, dayOfMonth);
                dpd.show();
            }
        });

        endEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(ContractNew.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                calendar.set(year, month, day);
                                String date = new SimpleDateFormat("MM/dd/yyyy").format(calendar.getTime());
                                endEditText.setText(date);
                            }
                        }, year, month, dayOfMonth);
                dpd.show();
            }
        });
        fabCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    postContract(CompanyId.getCompanyId(), fetchContractInfo());


                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });


    }


    public void createContract(long companyId, Contract contract) {
        Call<bg.tu.varna.si.model.Contract> call = apiService.createContract(JwtHandler.getJwt(), CompanyId.getCompanyId(), contract);
        call.enqueue(new Callback<Contract>() {
            @Override
            public void onResponse(Call<Contract> call, Response<Contract> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ContractNew.this, "Client updated!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Contract> call, Throwable t) {

            }
        });
    }

    public void getOperator(long operatorId) {
        Call<User> call = apiService.getUser(JwtHandler.getJwt(), MainActivity.userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("Operator", response.body().toString());

                operatorName = response.body().getFirstName() + " " + response.body().getLastName();
                operatorTextView.setText(operatorName);

                user = new User();
                user.setCompanyId(response.body().getCompanyId());
                user.setId(response.body().getId());
                user.setFirstName(response.body().getFirstName());
                user.setLastName(response.body().getLastName());
                user.setEmail(response.body().getEmail());
                user.setUsername(response.body().getUsername());
                user.setPassword(response.body().getPassword());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ContractNew.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.contract_client) {
            selectedClient = (Client) clientSpinner.getSelectedItem();
            Log.d("--------Client -----", String.valueOf(selectedClient.getId()));
        } else if (parent.getId() == R.id.contract_car) {
            selectedCar = (Car) carSpinner.getSelectedItem();
            Log.d("------Car -----: ", String.valueOf(selectedCar.getCarId()));

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void postContract(long companyId, Contract contract) {
        Call<Contract> call = apiService.createContract(JwtHandler.getJwt(), companyId, contract);
        call.enqueue(new Callback<Contract>() {
            @Override
            public void onResponse(Call<Contract> call, Response<Contract> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ContractNew.this, "New contract recorded!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Contract> call, Throwable t) {
                Toast.makeText(ContractNew.this, "The contract wasn't recorded!", Toast.LENGTH_LONG).show();
                Log.d("Contract error: ", t.getMessage().toString());
            }
        });
    }

    public Contract fetchContractInfo() throws ParseException {
        Contract contract = new Contract();
        contract.setOperator(user);
        contract.setCar(selectedCar);
        Log.d("----Selected client:", String.valueOf(selectedClient));
        contract.setClient(selectedClient);
        Log.d("----Selected client:", String.valueOf(selectedClient));

        contract.setCompanyId(CompanyId.getCompanyId());
        CarStatus carStatus = new CarStatus();
        carStatus.setStatus(Status.OK);
        carStatus.setDescription(startReportEditText.getText().toString());
        contract.setStatusOnStart(carStatus);

        Date startDate = new SimpleDateFormat("MM/dd/yyyy").parse(startEditText.getText().toString());
        contract.setStart(startDate);

        Date endDate = new SimpleDateFormat("MM/dd/yyyy").parse(endEditText.getText().toString());
        contract.setEnd(endDate);
        contractPrice = calculatePrice();
        Log.d("Car price:", String.valueOf(contractPrice));
        contract.setPrice(contractPrice);

        return contract;
    }

    public Double calculatePrice() {
        carPrice = selectedCar.getPriceForDay();
        Log.d("Base car price: ", String.valueOf(carPrice));

        double insideDamagePrice = 0;
        double outsideDamagePrice = 0;
        double engineDamagePrice = 0;
        double transmissionDamagePrice = 0;

        if (insideDamage.isChecked()) {
            insideDamagePrice = carPrice * 20;
        }
        if (outsideDamage.isChecked()) {
            outsideDamagePrice = carPrice * 30;

        }
        if (transmissionDamage.isChecked()) {
            transmissionDamagePrice = carPrice * 50;
        }

        if (engineDamage.isChecked()) {
            engineDamagePrice = carPrice * 100;
        }

        return contractPrice = carPrice + insideDamagePrice + outsideDamagePrice + engineDamagePrice + transmissionDamagePrice;
    }


}
