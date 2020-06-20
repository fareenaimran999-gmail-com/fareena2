package com.hina.washmycarpro.SendNotificationPack;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAAq0aIbY:APA91bF-o7RRUwp7vVHSeVl-FD3vgwUFUfxnybd9gkq_M6qrhmxpge96rVBjGYogd4vSwXVdqj8N-LOZS346NPBCpsd9qmv6lKEK2PrVdrtQcZmaywlvctCuNgt1SKe2z8s1WXht-Y2_0"

            }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotifcation(@Body NotificationSender body);
}