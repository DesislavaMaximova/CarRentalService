package bg.tu_varna.si.rentacarapp.service;

import bg.tu.varna.si.model.Company;
import bg.tu.varna.si.model.CompanyList;
import bg.tu.varna.si.model.User;
import bg.tu.varna.si.model.UserList;
import bg.tu.varna.si.model.UserRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AdminService {

    @GET("/admin/companies")
    Call<CompanyList> getAllCompanies(@Header("Authorization") String bearerToken);

    @GET("admin/companies/{id}")
    Call<Company> getCompany(@Header("Authorization") String bearerToken, @Path("id") long id);

    @POST("/admin/companies")
    Call<Company> createCompany(@Header("Authorization") String bearerToken, @Body Company company);

    @PUT("/admin/companies/{id}")
    Call<Company> updateCompany(@Header("Authorization") String bearerToken, @Path("id") long id, @Body Company company);

    @DELETE("admin/companies/{id}")
    Call<Void> deleteCompany(@Header("Authorization") String bearerToken, @Path("id") long id);

    @GET("/admin/companies/{id}/users")
    Call<UserList> getAllEmployees(@Header("Authorization") String bearerToken, @Path("id") long id);


    @GET("/admin/users/{userId}")
    Call<User> getUser(@Header("Authorization") String bearerToken,
                       @Path("userId") long idEmployee);

    @PUT("/admin/companies/{compantId}/users/{userId}")
    Call<User> udpateEmployee(@Header("Authorization") String bearerToken, @Path("companyId") long idCompany,
                              @Path("userId") long idEmployee,
                              @Body User user);

    @POST("/admin/companies/{companyId}/users")
    Call<User> createEmployee(@Header("Authorization") String bearerToken, @Path("companyId") long id,
                              @Body User user);

    @DELETE("/admin/users/{userId}")
    Call <Void> deleteEmployee(@Header("Authorization") String bearerToken,
                            @Path("userId") long idEmployee);


}

