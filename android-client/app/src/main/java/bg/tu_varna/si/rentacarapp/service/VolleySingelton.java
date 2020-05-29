package bg.tu_varna.si.rentacarapp.service;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingelton {
    private static VolleySingelton INSTANCE;
    private RequestQueue requestQueue;

    private VolleySingelton(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized VolleySingelton getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new VolleySingelton(context);
        }
        return INSTANCE;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }


    public <T> void addToRequestQue(Request<T> request) {
        getRequestQueue().add(request);
    }
}