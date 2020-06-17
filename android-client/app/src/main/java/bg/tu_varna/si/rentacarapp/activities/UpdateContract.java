package bg.tu_varna.si.rentacarapp.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import bg.tu.varna.si.model.Car;
import bg.tu.varna.si.model.Client;
import bg.tu.varna.si.model.Contract;
import bg.tu.varna.si.model.User;
import bg.tu_varna.si.rentacarapp.R;
import bg.tu_varna.si.rentacarapp.service.ApiService;
import bg.tu_varna.si.rentacarapp.service.CompanyId;
import bg.tu_varna.si.rentacarapp.service.JwtHandler;
import bg.tu_varna.si.rentacarapp.viewModels.CarViewModel;
import bg.tu_varna.si.rentacarapp.viewModels.ClientViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.O)
public class UpdateContract extends AppCompatActivity {
    public static final String DATE_PATTERN = "dd/MM/yyyy";

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN);

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
    EditText clientEditText;
    EditText carEditText;
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
    private Handler handler = new Handler();
    Contract contract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contract);
        FloatingActionButton fabFinish = findViewById(R.id.fab_contract_finish_update);
        operatorTextView = findViewById(R.id.contract_operator);
        carEditText = findViewById(R.id.contract_car_update);
        clientEditText = findViewById(R.id.contract_client_update);
        startEditText = findViewById(R.id.start_date_update);
        startReportEditText = findViewById(R.id.start_report_update);
        endEditText = findViewById(R.id.end_date_update);
        endReportEditText = findViewById(R.id.end_status_update);
        priceEditText = findViewById(R.id.final_price_update);
        insideDamage = findViewById(R.id.check_contract_insideDamage);
        outsideDamage = findViewById(R.id.check_contract_outsideDamage);
        engineDamage = findViewById(R.id.check_contract_engineDamage);
        transmissionDamage = findViewById(R.id.check_contract_TransmissionDamage);
        Intent intent = getIntent();
        contractId = intent.getLongExtra("contractId", -1);

        FloatingActionButton fabCheck = findViewById(R.id.fab_contract_check_update);
        FloatingActionButton fabBack = findViewById(R.id.fab_contract_back_update);

        getContract(CompanyId.getCompanyId(), contractId);

        fabFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    private void getContract(long companyId, long contractId) {
        Call<Contract> call = apiService.getContract(JwtHandler.getJwt(), companyId, contractId);
        call.enqueue(new Callback<Contract>() {
            @Override
            public void onResponse(Call<Contract> call, Response<Contract> response) {
                Contract contract = new Contract();
                contract.setActive(response.body().isActive());
                contract.setOperator(response.body().getOperator());
                operatorTextView.setText(response.body().getOperator().getFirstName() + " " +
                        response.body().getOperator().getLastName());
                contract.setCar(response.body().getCar());
                carEditText.setText(response.body().getCar().getRegNumber());
                contract.setClient(response.body().getClient());
                clientEditText.setText(response.body().getClient().getFirstName() + " " +
                        response.body().getClient().getFirstName());
                contract.setId(response.body().getId());
                contract.setPrice(response.body().getPrice());
                priceEditText.setText(String.valueOf(response.body().getPrice()));
                contract.setStatusOnStart(response.body().getStatusOnStart());
                startReportEditText.setText(response.body().getStatusOnStart().getDescription());
                contract.setStatusOnEnd(response.body().getStatusOnEnd());
                endReportEditText.setText(response.body().getStatusOnEnd().getDescription());
                contract.setStart(response.body().getStart());
                String startDate = new SimpleDateFormat(DATE_PATTERN).format(response.body().getStart());
                startEditText.setText(startDate);
                contract.setEnd(response.body().getEnd());
                String endDate = new SimpleDateFormat(DATE_PATTERN).format(response.body().getEnd());
                endEditText.setText(endDate);

            }

            @Override
            public void onFailure(Call<Contract> call, Throwable t) {

            }
        });
    }
}