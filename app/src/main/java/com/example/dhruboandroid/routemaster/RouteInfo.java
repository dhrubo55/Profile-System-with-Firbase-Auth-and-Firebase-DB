package com.example.dhruboandroid.routemaster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class RouteInfo extends AppCompatActivity {


    private static final String TAG = "RouteInfo:Dhrubo= ";
    private EditText testFrom;
    private EditText testTo;
    private Button submit;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_info);


        testFrom = findViewById(R.id.from);
        testTo = findViewById(R.id.to);
        submit = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiInterface apiService =
                        APIClient.getClient().create(ApiInterface.class);

                Call<List<Businfo>> call = apiService.getVehicleByRoute(testFrom.getText().toString().trim(), testTo.getText().toString().trim());
                call.enqueue(new Callback<List<Businfo>>() {

                    @Override
                    public void onResponse(Call<List<Businfo>> call, retrofit2.Response<List<Businfo>> response) {
                        // /Log.d(TAG, S);

                        if (response != null){
                            List<Businfo> rs = response.body();
                            Log.d(TAG,rs.toString());
                        }

                        List<Businfo> rs = response.body();
                        for (Businfo businfo: rs
                                ) {

                            String s =  businfo.toString();
                            textView.setText(businfo.toString());

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Businfo>>call, Throwable t) {
                        // Log error here since request failed
                        Log.e(TAG, t.toString());
                    }
                });
            }
        });
    }
}
