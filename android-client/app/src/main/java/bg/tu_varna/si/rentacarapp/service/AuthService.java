package bg.tu_varna.si.rentacarapp.service;

import bg.tu.varna.si.model.auth.AuthenticationRequest;
import bg.tu.varna.si.model.auth.AuthenticationResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST ("/auth/login")
    Call<AuthenticationResponse> login(@Body AuthenticationRequest credentials);
}
