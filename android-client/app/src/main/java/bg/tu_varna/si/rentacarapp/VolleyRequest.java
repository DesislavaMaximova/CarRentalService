package bg.tu_varna.si.rentacarapp;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;

public class VolleyRequest {
    private static VolleyRequest INSTANCE;
    private RequestQueue requestQueue;
    private Context context;
    private ImageLoader imageLoader;
    private IVolley iVolley;

    private VolleyRequest(Context context,IVolley iVolley){
        this.context = context;
        this.iVolley = iVolley;
    }
}
