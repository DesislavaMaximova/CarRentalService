package bg.tu_varna.si.rentacarapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import bg.tu.varna.si.model.Client;
import bg.tu_varna.si.rentacarapp.Cars;
import bg.tu_varna.si.rentacarapp.Clients;
import bg.tu_varna.si.rentacarapp.R;
import bg.tu_varna.si.rentacarapp.service.ApiService;
import bg.tu_varna.si.rentacarapp.service.CompanyId;
import bg.tu_varna.si.rentacarapp.service.JwtHandler;
import bg.tu_varna.si.rentacarapp.service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Client_new extends AppCompatActivity {
    public static final String EXTRA_CLIENT_ID = "clientId";
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText telephone;
    EditText driversLicence;
    RatingBar clientRating;
    TextView ratingText;
    ApiService apiService;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_new);

        firstName = findViewById(R.id.client_first_name);
        lastName = findViewById(R.id.client_last_name);
        email = findViewById(R.id.client_email);
        telephone = findViewById(R.id.client_telephone);
        driversLicence = findViewById(R.id.client_drivers_licence);
        clientRating = findViewById(R.id.client_rating);
        clientRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float value, boolean b) {
                setClientRating(value);
            }
        });

        ratingText = findViewById(R.id.client_rating_text);
        FloatingActionButton fabBack = findViewById(R.id.fab_client_back);
        FloatingActionButton fabCheck = findViewById(R.id.fab_client_check);
        Intent intent = getIntent();
        Long clientId = intent.getLongExtra(EXTRA_CLIENT_ID, -1);
        Log.d("------------ClientId:", clientId.toString() );
        apiService = RetrofitService.cteateService(ApiService.class);

        if (clientId != -1) {
            setTitle("Update client's profile ");
            getClient(CompanyId.getCompanyId(),clientId);
            fabCheck.setOnClickListener(v -> {
                putClient(CompanyId.getCompanyId(), clientId, fillClient());
                handler.postDelayed(updateTimeTask, 1500);
            });
        } else {
            setTitle("New car");
            fabCheck.setOnClickListener(v -> {
                postClient();
                handler.postDelayed(updateTimeTask, 1500);
            });
        }
        fabBack.setOnClickListener(v -> {
            setResult(RESULT_OK);
            finish();
        });
    }


    private void postClient() {
        Call<Client> call = apiService.createClient(JwtHandler.getJwt(), CompanyId.getCompanyId(), fillClient());
        call.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Client_new.this, "New client added to client list", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                Toast.makeText(Client_new.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private Runnable updateTimeTask = new Runnable() {
        public void run() {
            Intent intent = new Intent(Client_new.this, Clients.class);
            startActivity(intent);
        }
    };

    private Client fillClient() {
        Client client = new Client();
        client.setFirstName(firstName.getText().toString());
        Log.d("First name: ", String.valueOf(firstName));
        client.setLastName(lastName.getText().toString());
        client.setEmail(email.getText().toString());
        client.setDriversLicense(driversLicence.getText().toString());
        client.setTelephone(telephone.getText().toString());
        client.setRating(clientRating.getRating());
        Log.d("Rating", String.valueOf(clientRating));

        return client;
    }

    private void getClient(long companyId, long clientId) {
        Call<Client> call = apiService.getClient(JwtHandler.getJwt(), companyId, clientId);
        call.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Client_new.this, "Client ready to be updated", Toast.LENGTH_LONG).show();
                    firstName.setText(response.body().getFirstName());
                    Log.d("----Client's first name", response.body().getFirstName());
                    lastName.setText(response.body().getLastName());
                    email.setText(response.body().getEmail());
                    telephone.setText(response.body().getTelephone());
                    driversLicence.setText(response.body().getDriversLicense());
                    setClientRating(response.body().getRating());
                }
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                Toast.makeText(Client_new.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void putClient(long companyId, long clientId, Client client) {
        Call<Client> call = apiService.updateClient(JwtHandler.getJwt(), companyId, clientId, client);
        call.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Client_new.this, "Client updated!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                Toast.makeText(Client_new.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setClientRating(float rating) {

        clientRating.setRating(rating);

        switch (Math.round(rating)) {
            case (0):
                ratingText.setText("Poor");
                break;
            case (1):
                ratingText.setText("Bad");
                break;
            case (2):
                ratingText.setText("Moderate");
                break;
            case (3):
                ratingText.setText("Good");
                break;
            case (4):
                ratingText.setText("Very good");
                break;
            case (5):
                ratingText.setText("Awesome");
                break;
            default:
                ratingText.setText(" ");
                break;
        }
    }
}
