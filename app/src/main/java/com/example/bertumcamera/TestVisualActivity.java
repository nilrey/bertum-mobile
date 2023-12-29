package com.example.bertumcamera;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import android.support.v7.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class TestVisualActivity extends AppCompatActivity {

    // creating variables for our edittext, 
    // button, textview and progressbar.
    private EditText nameEdt, jobEdt;
    private Button postDataBtn;
    private TextView responseTV;
    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_visual);

        // initializing our views
        nameEdt = findViewById(R.id.idEdtName);
        jobEdt = findViewById(R.id.idEdtJob);
        postDataBtn = findViewById(R.id.idBtnPost);
        responseTV = findViewById(R.id.idTVResponse);
        loadingPB = findViewById(R.id.idLoadingPB);

        // adding on click listener to our button.
        postDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating if the text field is empty or not.
                if (nameEdt.getText().toString().isEmpty() && jobEdt.getText().toString().isEmpty()) {
                    Toast.makeText(TestVisualActivity.this, "Please enter both the values", Toast.LENGTH_SHORT).show();
                    return;
                }

//                handleSSLHandshake();
                // calling a method to post the data and passing our name and job.
                postDataUsingVolley("123456789", "");
            }
        });
    }

    private void postDataUsingVolley(final String photoId, final String photoBase64) {
//        String url = Const.URL_AIAPI;
        String url = "http://h304809427.nichost.ru/api/get_payment_url.php";
        RequestQueue queue = Volley.newRequestQueue(TestVisualActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject objects = null;
                try {
                    objects = new JSONObject(response);
                    JSONObject confirm = objects.getJSONObject("confirm");
                    String link = (String) confirm.get("payment_url");
//                    JSONObject objDetail = settings.getJSONObject("payment_url");
//                    String link = payment_url.toString();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    startActivity(browserIntent);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // handling error on below line.
//                loadingPB.setVisibility(View.GONE);
//                responseTV.setVisibility(View.VISIBLE);
//                responseTV.setText(error.getMessage());
                Toast.makeText(TestVisualActivity.this, "Fail to get response..", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
/*

                JSONObject amountVal = new JSONObject();
                try {
                    amountVal.put("value", "1");
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                JSONObject arrAmount = new JSONObject();
                arrAmount.put(amountVal);
*/



                JSONObject amountObj = new JSONObject();
                try {
                    amountObj.put("value", "2");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }




                JSONObject det1 = new JSONObject();
                JSONObject det2 = new JSONObject();
                try {
                    det1.put("Name", "Бампер передний");
                    det1.put("Price", "100");
                    det1.put("Quantity", "1");
                    det1.put("Amount", "200");
                    det1.put("Tax", "vat20");

                    det2.put("Name", "Ремонт бампер передний");
                    det2.put("Price", "555");
                    det2.put("Quantity", "1");
                    det2.put("Amount", "0");
                    det2.put("Tax", "vat20");

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                JSONArray arrDetails = new JSONArray();
                arrDetails.put(det1);
                arrDetails.put(det2);

                JSONObject receiptObj = new JSONObject();
                try {
                    receiptObj.put("Email", "meff86@list.ru");
                    receiptObj.put("Taxation", "osn");
                    receiptObj.put("Items", arrDetails);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


                JSONObject orderObj = new JSONObject();
                try {
                    orderObj.put("amount", amountObj);
                    orderObj.put("order_id", String.valueOf(System.currentTimeMillis()));
                    orderObj.put("receipt", receiptObj);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                String jsonStr = orderObj.toString();

                params.put("_content", jsonStr);
//                params.put("photoBase64", photoBase64 );
                return params;
            }
        };
        DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(retryPolicy);

        Toast.makeText(TestVisualActivity.this, "Данные успешно отправлены", Toast.LENGTH_SHORT).show();

        // post the data.
        queue.add(request);
    }

    /**
     * Enables https connections
     */
    @SuppressLint("TrulyRandom")
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }

}
