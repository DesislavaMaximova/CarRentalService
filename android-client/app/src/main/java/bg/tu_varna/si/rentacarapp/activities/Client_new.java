package bg.tu_varna.si.rentacarapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import bg.tu_varna.si.rentacarapp.R;

public class Client_new extends AppCompatActivity {
EditText firstName;
EditText lastName;
EditText email;
EditText telephone;
EditText driversLicence;
RatingBar clientRating;
TextView rating;
FloatingActionButton fabCheck;
FloatingActionButton fabBack;
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
        rating = findViewById(R.id.client_rating_text);
        fabBack = findViewById(R.id.fab_client_back);
        fabCheck=findViewById(R.id.fab_client_check);

        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               setResult(RESULT_OK);
                finish();
            }
        });

fabCheck.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        rating.setText(String.valueOf(clientRating.getRating()) + "stars");
    }
});

    }

}
