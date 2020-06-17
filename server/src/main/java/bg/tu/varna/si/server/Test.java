package bg.tu.varna.si.server;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class Test {
	
    public static final String DATE_PATTERN = "dd/MM/yyyy";
    
    


	public static void main(String[] args) {
		
		String date = "16/06/2020";
		// TODO Auto-generated method stub
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
		
		LocalDate startDate = LocalDate.parse(date, formatter);
		
		System.out.println(startDate);

	}

}
