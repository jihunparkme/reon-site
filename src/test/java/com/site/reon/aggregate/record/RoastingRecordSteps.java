package com.site.reon.aggregate.record;

import com.site.reon.aggregate.record.service.dto.RoastingRecordRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class RoastingRecordSteps {
    public static final String title = "test roasting222";
    public static final String fan = "[80,80,80,80,80,85,85,85,90,90,95,95,100,100,110,120,130,140,150,150,150,150,150,160,160,160,170,170,170,180,180,180,190,190,190,200,200,210,210,220,220,200,200,190,190,180,170,160,150,140,130,120,120,110,110,100,100,100,90,90,80,80,70,60,50,50,40,40,50,50,60,60,70,80,90,100,110,80,50,40,30]";
    public static final String heater = "[10,15,20,25,30,40,40,40,40,40,50,60,70,75,80,90,95,95,95,95,95,100,100,100,100,100,100,100,100,100,100,110,120,130,140,150,160,170,180,185,185,185,185,190,195,195,195,195,200,200,200,200,200,200,200,200,190,180,170,160,150,140,130,120,100,100,100,100,100,100,100,100,90,90,70,60,50,40,30,20,10]";
    public static final String temp1 = "[0,5,10,12,14,16,18,19,20,23,25,27,28,29,30,34,38,39,47,48,58,59,67,69,72,73,84,86,93,95,98,100,120,130,140,150,160,170,180,190,200,210,215,210,200,200,200,190,200,190,200,190,200,190,200,190,200,190,200,190,200,190,200,180,170,160,150,140,130,120,110,100,90,80,70,60,50,40,30,20,10]";
    public static final String temp2 = "[0,5,10,12,14,16,18,19,20,23,25,27,28,29,30,34,38,39,47,48,58,59,67,69,72,73,84,86,93,95,98,100,120,130,140,150,160,170,180,190,200,210,215,210,200,200,200,190,200,190,200,190,200,190,200,190,200,190,200,190,200,190,200,180,170,160,150,140,130,120,110,100,90,80,70,60,50,40,30,20,10]";
    public static final String temp3 = "[0,5,10,12,14,16,18,19,20,23,25,27,28,29,30,34,38,39,47,48,58,59,67,69,72,73,84,86,93,95,98,100,120,130,140,150,160,170,180,190,200,210,215,210,200,200,200,190,200,190,200,190,200,190,200,190,200,190,200,190,200,190,200,180,170,160,150,140,130,120,110,100,90,80,70,60,50,40,30,20,10]";
    public static final String temp4 = "[0,5,10,12,14,16,18,19,20,23,25,27,28,29,30,34,38,39,47,48,58,59,67,69,72,73,84,86,93,95,98,100,120,130,140,150,160,170,180,190,200,210,215,210,200,200,200,190,200,190,200,190,200,190,200,190,200,190,200,190,200,190,200,180,170,160,150,140,130,120,110,100,90,80,70,60,50,40,30,20,10]";
    public static final String ror = "[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,49,48,47,45,44,43,42,41,40,39,38,37,36,35,34,33,32,31,30,29,28,27,26,25,24,23,22,20,21,20,19]";
    public static final String roasterSn = "1234";
    public static final String crackPoint = "[30.3, 50.3]";
    public static final String crackPointTime = "[2024-02-20 15:00:15 +0000, 2024-02-20 15:00:45 +0000]";
    public static final String turningPointTemp = "[30.3]";
    public static final String turningPointTime = "[2024-02-20 15:00:18 +0000]";
    public static final float preheatTemp = 100.3F;
    public static final String disposeTemp = "[95.3]";
    public static final String disposeTime = "[2024-02-20 15:00:18 +0000]";
    public static final int inputCapacity = 40;

    public static RoastingRecordRequest getRoastingRecordRequest() {
        return RoastingRecordRequest.builder()
                .title(title)
                .fan(fan)
                .heater(heater)
                .temp1(temp1)
                .temp2(temp2)
                .temp3(temp3)
                .temp4(temp4)
                .ror(ror)
                .roasterSn(roasterSn)
                .crackPoint(crackPoint)
                .crackPointTime(crackPointTime)
                .turningPointTemp(turningPointTemp)
                .turningPointTime(turningPointTime)
                .preheatTemp(preheatTemp)
                .disposeTemp(disposeTemp)
                .disposeTime(disposeTime)
                .inputCapacity(inputCapacity)
                .build();
    }

    public static ExtractableResponse<Response> requestUploadRoastingRecord(final RoastingRecordRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/record/upload")
                .then()
                .log().all().extract();
    }
}
