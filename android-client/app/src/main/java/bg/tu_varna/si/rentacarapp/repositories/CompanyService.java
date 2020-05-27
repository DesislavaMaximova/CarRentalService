package bg.tu_varna.si.rentacarapp.repositories;

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

public interface CompanyService {

    @GET("/admin/companies")
    Call<CompanyList> getAllCompanies();

    @GET("companies/{id}")
    Call<Company> getCompany(@Header("Authorization") String bearerToken);

    @POST("/admin/companies")
    Call<Company> createCompany(@Header("Authorization") String bearerToken, @Body Company company);

    @PUT("admin/companies/{id}")
    Call<Company> updateCompany(@Header("Authorization") String bearerToken, @Path("id") long id, @Body Company company);

    @DELETE("admin/companies/{id}")
    Call<?> deleteCompany(@Header("Authorization") String bearerToken, @Path("id") long id);

    @GET("/admin/companies/{id}/users")
    Call<UserList> getAllEmployees(@Header("Authorization") String bearerToken, @Path("id") long id);

    @GET("/admin/companies/{id}/users/{id}")
    Call<User> getEmployee (@Header("Authorization") String bearerToken, @Path("id") long idCompany,
                            @Path("id") long idEmployee);

    @PUT("/admin/companies/{id}/users/{id}")
    Call<User> udpateEmployee(@Header("Authorization") String bearerToken, @Path("id") long idCompany,
                              @Path("id") long idEmployee,
                              @Body UserRequest aut);

    @POST("/admin/companies/{id}/users/{id}")
    Call<User> createEmployee(@Header("Authorization") String bearerToken, @Path("id") long id,
                              @Path("id") long idEmployee,
                              @Body UserRequest aut);

    @DELETE("/admin/companies/{id}/users/{id}")
    Call <?> deleteEmployee(@Header("Authorization") String bearerToken, @Path("id") long id,
                            @Path("id") long idEmployee);


}

