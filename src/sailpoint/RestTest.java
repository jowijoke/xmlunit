package sailpoint;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

 
 
 
 
 
public class RestTest {
    private static final String iiqIP = "localhost";
    private static final int iiqPort = 8078;
    private static final String iiqUser = "spadmin";
    private static final String iiqPass = "admin";
 
    public static void main(String[] args) {
     
    try {
       
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(iiqUser, iiqPass);
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
       

 
        //
        // Call to ping
        //
     
     
        String iiqRequest = "http://" + iiqIP + ":" + String.valueOf(iiqPort) + "/diff/rest/ping";
        System.out.println("\nRequest: " + iiqRequest);
        HttpGet request = new HttpGet(iiqRequest);
        HttpResponse response = client.execute(request);
        BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        while ((line = rd.readLine()) != null)
                System.out.println(line);
 
        //
        // Call to get Application Objects
        //
     
        iiqRequest = "http://" + iiqIP + ":" + String.valueOf(iiqPort) + "/diff/rest/debug/Application?listObjects";
        System.out.println("\nRequest: " + iiqRequest);
        request = new HttpGet(iiqRequest);
        response = client.execute(request);
        rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        line = "";
        while ((line = rd.readLine()) != null) {
            JSONObject jsonObject = new JSONObject(line);
            JSONArray objResponse = (JSONArray) jsonObject.get("objects");
            
            HashMap<String, String> objMap = new HashMap<String, String>();
            HashMap<String, String> xmlMap = new HashMap<String, String>();

            for(int i=0; i<objResponse.length(); i++){
            	objMap.put(objResponse.getJSONObject(i).getString("name"), objResponse.getJSONObject(i).getString("id"));
            }
            
            // Get a set of the entries
            Set set = objMap.entrySet();
            
            // Get an iterator
            Iterator i = set.iterator();
            
            // Display elements
            while(i.hasNext()) {
               Map.Entry me = (Map.Entry)i.next();
               System.out.print(me.getKey() + ": ");
               System.out.println(me.getValue());
               
               //
               // Request to get objects xml
               //
               String xmlRequest = "http://" + iiqIP + ":" + String.valueOf(iiqPort) + "/diff/rest/debug/Application/" + me.getValue();
               request = new HttpGet(xmlRequest);
               response = client.execute(request);
               rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
               line = "";
               while ((line = rd.readLine()) != null) {
                   JSONObject jsonObject2 = new JSONObject(line);
                   JSONArray objResponse2 = (JSONArray) jsonObject2.get("objects");
                   
                   for(int j=0; j<objResponse2.length(); j++){
                   	xmlMap.put(objResponse2.getJSONObject(j).getString("id"), objResponse2.getJSONObject(j).getString("xml"));
                   }
                   
                   
               }

            }
            
         // Get a set of the entries
            Set sets = xmlMap.entrySet();
            
            // Get an iterator
            Iterator x = sets.iterator();
            
            // Display elements
            while(x.hasNext()) {
               Map.Entry m = (Map.Entry)x.next();
               System.out.print(m.getKey() + ": ");
               System.out.println(m.getValue());

        }
     
     
     
        }
        } catch (Exception e) {
            System.out.println("Exception:" + e.toString());
        }
    }
}
 
 
