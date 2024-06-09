package com.waterbird.wbapisdk.client;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.waterbird.wbapisdk.model.User;


/**
 * NameController-NameApiClient
 */
public class NameApiClient extends CommonApiClient{
    private static final String GATEWAY_HOST="http://localhost:8090";

    public NameApiClient(String accessKey, String secretKey) {
        super(accessKey, secretKey);
    }

    /**
     * 获取用户名
     * @param user
     * @return
     */
    public String getUserNameByPost(User user) {
        String json = JSONUtil.toJsonStr(user);
        return HttpRequest.post(GATEWAY_HOST+"/api/name/user")
                .addHeaders(getHeadMap(json,accessKey,secretKey))
                .body(json)
                .execute().body();
    }
}
