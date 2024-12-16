package JsonPackage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonClass {
	
	public static String countryCodeNumber(String countryName, String number) {
		try {
			@SuppressWarnings("deprecation")
			URL url = new URL("https://jsonmock.hackerrank.com/api/countries?name="+ countryName); 
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			
			int responseCode = connection.getResponseCode();
			if(responseCode != 200) {
				throw new RuntimeException("HttpResponseCode: " + responseCode);
			}
			else {
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
 
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.toString());
                
                JsonNode dataNode = rootNode.path("data");
                if (dataNode.isEmpty()) {
                    return "-1";
                }
                JsonNode firstCountry = dataNode.get(0);
                JsonNode callingCodeNode = firstCountry.path("callingCodes");
                if (!callingCodeNode.isArray() || callingCodeNode.isEmpty()) {
                    return "Calling code not available for this country";
                }
                String callingCodes = callingCodeNode.get(0).asText();
                
                
                return "+" +callingCodes + " " + number;
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return "-1";
		
	}
	public static void main(String[] args) {
		System.out.println(countryCodeNumber("India", "934822394"));
		
	}

}
