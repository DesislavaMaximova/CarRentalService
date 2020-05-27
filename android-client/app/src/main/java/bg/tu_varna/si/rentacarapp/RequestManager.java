package bg.tu_varna.si.rentacarapp;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

public class RequestManager {
    private Context context;

    public RequestManager(Context context) {
        this.context = context;
    }

    public void sendVolleyRequest() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Constants.BACKEND_ADDRESS, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                showData(String.valueOf(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                showData(String.valueOf(error));
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new LinkedHashMap<>();
                header.put("Authorization", "Bearer " + JwtHandler.getJwt());
                return header;
            }
        };
        VolleySingelton.getInstance(context).addToRequestQue(jsonObjectRequest);
    }

    void showData(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
