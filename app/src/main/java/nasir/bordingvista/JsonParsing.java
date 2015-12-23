    package nasir.bordingvista;

    import android.app.Activity;
    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ListAdapter;
    import android.widget.ListView;
    import android.widget.SimpleAdapter;
    import android.widget.Toast;

    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;

    import java.util.ArrayList;
    import java.util.HashMap;

    /**
     * Created by Nasir on 12/22/2015.
     */
    public class JsonParsing extends Activity implements AsyncResponse {

        ArrayList<HashMap<String, String>> arrList;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.json_layout);

            Intent intent = getIntent();
            HashMap<String, String> postData = new HashMap<String, String>();

            PostResponseAsyncTask loginTask = new PostResponseAsyncTask(this,postData);
            loginTask.execute("http://vikingmobile.bording.dk/agetor/vikingproxy/ArticleInq?ClientId=1&Store=4");
        }

        @Override
        public void processFinish(String result) {
            //Toast.makeText(this, "Result: "+result, Toast.LENGTH_LONG).show();
            ListView listView = (ListView) findViewById(R.id.listView);
            arrList = new ArrayList();
            try {
                JSONObject json4 = new JSONObject(result);
                JSONObject article = json4.getJSONObject("ArticleInq");
                JSONObject departments = article.getJSONObject("Departments");
                JSONArray jArray = departments.getJSONArray("Dept");

                //JSONArray jArray = new JSONArray(result);
                for (int i = 0; i < jArray.length(); i++) {
                   // Toast.makeText(this, "jArray: "+jArray, Toast.LENGTH_LONG).show();
                      HashMap<String, String> map1 = new HashMap();
                    JSONObject current = jArray.getJSONObject(i);
                    map1.put("DeptNo", current.getString("DeptNo"));
                    map1.put("DeptText", current.getString("DeptText"));
                   map1.put("Count", current.getString("Count"));
                    //Toast.makeText(this, current.getString("DeptNo"), Toast.LENGTH_LONG).show();
                    arrList.add(map1);
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (!arrList.isEmpty()) {


                ListAdapter adapter = new SimpleAdapter( this, arrList,
                        R.layout.list_item, new String[] { "DeptNo", "DeptText", "Count" },
                        new int[] { R.id.DeptNo, R.id.DeptText, R.id.Count});

                listView.setAdapter(adapter);


            }
            else {
                Toast.makeText(this, "Arraylist: Empty", Toast.LENGTH_LONG).show();

            }


        }
    }
