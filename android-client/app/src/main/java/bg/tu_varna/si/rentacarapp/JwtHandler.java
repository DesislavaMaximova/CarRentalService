package bg.tu_varna.si.rentacarapp;

public final class JwtHandler {
    private JwtHandler() {

    }
    private static String jwt = "";
    public static void setJwt(String newJwt){
        if(newJwt !=null){
            jwt = newJwt;
        }
    }
    public static String getJwt(){
        return jwt;
    }
}
