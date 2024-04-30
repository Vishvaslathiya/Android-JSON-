import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<Map<String, String>> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize ListView and data list
        listView = findViewById(R.id.listView);
        productList = new ArrayList<>();

        // Call AsyncTask to fetch data from URL
        new FetchDataTask().execute();
    }

    private class FetchDataTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            String result = null;
            try {
                URL url = new URL("https://demonuts.com/Demonuts/JsonTest/Tennis/json_parsing.php");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                result = stringBuilder.toString();
                bufferedReader.close();
                inputStream.close();
                urlConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                parseJsonAndDisplay(result);
            }
        }
    }

    private void parseJsonAndDisplay(String jsonString) {
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String description = jsonObject.getString("desc");
                String imageUrl = jsonObject.getString("image");

                // Create a map for each product
                Map<String, String> productMap = new HashMap<>();
                productMap.put("name", name);
                productMap.put("description", description);
                productMap.put("image", imageUrl);

                // Add product map to the list
                productList.add(productMap);
            }

            // Create SimpleAdapter with data and layout
            String[] from = {"name", "description", "image"};
            int[] to = {R.id.productName, R.id.productDescription, R.id.productImage};
            SimpleAdapter adapter = new SimpleAdapter(this, productList, R.layout.custom_list_item, from, to);

            // Set the adapter to the ListView
            listView.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
