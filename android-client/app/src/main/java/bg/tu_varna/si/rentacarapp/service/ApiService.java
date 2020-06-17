package bg.tu_varna.si.rentacarapp.service;

import bg.tu.varna.si.model.Car;
import bg.tu.varna.si.model.CarList;
import bg.tu.varna.si.model.Client;
import bg.tu.varna.si.model.ClientList;
import bg.tu.varna.si.model.Contract;
import bg.tu.varna.si.model.ContractList;
import bg.tu.varna.si.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @GET("/api/{companyId}/cars")
    Call<CarList> getAllCars(@Header("Authorization") String bearerToken, @Path("companyId") long companyId);

    @POST("/api/{companyId}/cars")
    Call<Car> createCar(@Header("Authorization") String bearerToken,
                        @Path("companyId") long companyId, @Body Car car);

    @GET("/api/{companyId}/cars/{carId}")
    Call<Car> getCar(@Header("Authorization") String bearerToken, @Path("companyId") long companyId,
                     @Path("carId") long carId);

    @PUT("/api/{companyId}/cars/{carId}")
    Call<Car> updateCar(@Header("Authorization") String bearerToken, @Path("companyId") long companyId,
                        @Path("carId") long carId,
                        @Body Car car);

    @DELETE("/api/{companyId}/cars/{carID}")
    Call<Void> deleteCar(@Header("Authorization") String bearerToken, @Path("companyId") long companyId,
                         @Path("carId") long carId);

    @GET("/api/{companyId}/contracts")
    Call<ContractList> getAllContracts(@Header("Authorization") String bearerToken, @Path("companyId") long companyId);

    @GET("/api/{companyId}/contracts/{contractId}")
    Call<Contract> getContract(@Header("Authorization") String bearerToken, @Path("companyId") long companyId,
                               @Path("contractId") long contractId);

    @POST("/api/{companyId}/contracts")
    Call<Contract> createContract(@Header("Authorization") String bearerToken,
                                  @Path("companyId") long companyId,
                                  @Body Contract contract);

    @PUT("/api/{companyId}/contracts/{contractId}")
    Call<Contract> updateContract(@Header("Authorization") String bearerToken, @Path("companyId") long companyId,
                                  @Path("contractId") long contractId,
                                  @Body Contract contract);

    @DELETE("/api/{companyId}/contracts/{contractId}")
    Call<Void> deleteContract(@Header("Authorization") String bearerToken, @Path("companyId") long companyId,
                              @Path("contractId") long contractId);


    @GET("/api/{companyId}/clients")
    Call<ClientList> getAllClients(@Header("Authorization") String bearerToken,
                                   @Path("companyId") long companyId);

    @GET("/api/{companyId}/clients/{clientId}")
    Call<Client> getClient(@Header("Authorization") String bearerToken,
                           @Path("companyId") long companyId,
                           @Path("clientId") long clientId);

    @POST("/api/{companyId}/clients")
    Call<Client> createClient(@Header("Authorization") String bearerToken,
                              @Path("companyId") long companyId,
                              @Body Client client);

    @PUT("/api/{companyId}/clients/{clientId}")
    Call<Client> updateClient(@Header("Authorization") String bearerToken,
                              @Path("companyId") long companyId,
                              @Path("clientId") long clientId,
                              @Body Client client);

    @GET("/api/users/{userId}")
    Call<User> getUser(@Header("Authorization") String bearerToken,
                       @Path("userId") long idEmployee);

    @GET("/api/{companyId}/queries/availableCars")
    Call<CarList> getAvailableCars(@Header("Authorization") String bearerToken,
                                   @Path("companyId") long companyId);
}
