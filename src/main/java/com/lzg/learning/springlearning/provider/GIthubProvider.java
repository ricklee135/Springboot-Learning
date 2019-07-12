package com.lzg.learning.springlearning.provider;

import com.alibaba.fastjson.JSON;
import com.lzg.learning.springlearning.dto.AccessTokenDTO;
import com.lzg.learning.springlearning.dto.GIthubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GIthubProvider {
    public String getAcessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) { }
             return null;
    }
    public GIthubUser getUser(String accessToken ){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GIthubUser gIthubUser = JSON.parseObject(string, GIthubUser.class);
            return gIthubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
