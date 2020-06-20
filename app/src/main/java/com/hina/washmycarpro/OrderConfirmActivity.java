package com.hina.washmycarpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Response;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hina.washmycarpro.SendNotificationPack.APIService;
import com.hina.washmycarpro.SendNotificationPack.Client;
import com.hina.washmycarpro.SendNotificationPack.Data;

import com.hina.washmycarpro.SendNotificationPack.MyResponse;
import com.hina.washmycarpro.SendNotificationPack.NotificationSender;
import com.hina.washmycarpro.SendNotificationPack.Token;

import retrofit2.Call;
import retrofit2.Callback;

public class OrderConfirmActivity extends AppCompatActivity {

    String data1, data2, data3, data4, data5;
    TextView servicePrivderNametxt, ServieNametxt, timetxt, datetxt, totalAmounttxt;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1F7FB8")));
        getSupportActionBar().setTitle("Order Confimation");

        servicePrivderNametxt = findViewById(R.id.ServiceProvider);
        ServieNametxt = findViewById(R.id.serviceNametext);
        timetxt = findViewById(R.id.timetext);
        datetxt = findViewById(R.id.datetext);
        totalAmounttxt = findViewById(R.id.totalPricetext);

        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
//Initialize the object of APIService with client class

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            data1 = extras.getString("ProviderName");
            data2 = extras.getString("ServiceName");
            data3 = extras.getString("Time");
            data4 = extras.getString("Date");
            data5 = extras.getString("TotalPrice");
        }

        servicePrivderNametxt.setText(data1);
        ServieNametxt.setText(data2);
        timetxt.setText(data3);
        datetxt.setText(data4);
        totalAmounttxt.setText(data5);

        Button btnhome = findViewById(R.id.btnHome);
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(OrderConfirmActivity.this,UserActivity.class));
//                finish();
                FirebaseDatabase.getInstance().getReference().child("Tokens").child(ServieNametxt.getText().toString().trim()).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override

                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String usertoken = dataSnapshot.getValue(String.class);
                        sendNotifications(usertoken, timetxt.getText().toString().trim(), datetxt.getText().toString().trim());
                    }

                    @Override

                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

            }
        });

        UpdateToken();
    }

    private void UpdateToken() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        Token token = new Token(refreshToken);
        FirebaseDatabase.getInstance().getReference("Tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token);
    }

    //This Method Sends the notifications combining all class of SendNotificationPack Package work together
    public void sendNotifications(String usertoken, String servicePrivderNametxt, String datetxt) {
        Data data = new Data(servicePrivderNametxt, datetxt);
        NotificationSender sender = new NotificationSender(data, usertoken);
        //apiService.sendNotifcation(sender).enqueue(new Callback<MyResponse>() {
//            @Override
////            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
////                if (response.code() == 200) {
////                    if (response.body().success != 1) {
////                        Toast.makeText(OrderConfirmActivity.this, "Failed ",
////                                Toast.LENGTH_LONG);
////                    }
////                }
            }

//            @Override
//            public void onResponse(Call<MyResponse> call, retrofit2.Response<MyResponse> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<MyResponse> call, Throwable t) {
//            }
//        });
   // }
}
