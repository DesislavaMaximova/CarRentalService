package bg.tu_varna.si.rentacarapp.service;

public final class CompanyId {
    private CompanyId() {

    }
    private static long companyId = -1;
    public static void setCompanyId(long newCompanyId){
        if(newCompanyId != -1){
            companyId = newCompanyId;
        }
    }
    public static long getCompanyId(){
        return companyId;
    }
}
