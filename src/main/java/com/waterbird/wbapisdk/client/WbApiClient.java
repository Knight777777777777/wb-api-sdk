package com.waterbird.wbapisdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.waterbird.wbapisdk.model.User;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static com.waterbird.wbapisdk.utils.SignUtils.genSign;


/**
 * 调用第三方接口的客户端
 */
public class WbApiClient {
    private static final String GATEWAY_HOST="http://localhost:8090";
    private String accessKey;
    private String secretKey;

    public WbApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }


    public String getNameByGet(String name) {
        // 可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        // 将"name"参数添加到映射中
        paramMap.put("name", name);
        // 使用HttpUtil工具发起GET请求，并获取服务器返回的结果
        String result= HttpUtil.get(GATEWAY_HOST+"/api/name/get", paramMap);
        // 打印服务器返回的结果
        System.out.println(result);
        // 返回服务器返回的结果
        return result;
    }


    public String getNameByPost(String name) {
        // 可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        // 使用HttpUtil工具发起POST请求，并获取服务器返回的结果
        String result= HttpUtil.post(GATEWAY_HOST+"/api/name/post", paramMap);
        System.out.println(result);
        return result;
    }
    // 创建一个私有方法，用于构造请求头
    private Map<String, String> getHeaderMap(String body) throws UnsupportedEncodingException {
        // 创建一个新的 HashMap 对象
        Map<String, String> hashMap = new HashMap<>();
        // 将 "accessKey" 和其对应的值放入 map 中
        hashMap.put("accessKey", accessKey);
        //添加随机数
        hashMap.put("nonce", RandomUtil.randomNumbers(5));
        //请求具体内容
        hashMap.put("body", URLEncoder.encode(body,"utf-8"));
        //添加时间戳
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis()/1000));
        //添加签名
        hashMap.put("sign",genSign(body,secretKey));
        // 返回构造的请求头 map
        return hashMap;
    }

    public String getUserNameByPost(User user) throws UnsupportedEncodingException {
        // 将User对象转换为JSON字符串
        String json = JSONUtil.toJsonStr(user);
        // 使用HttpRequest工具发起POST请求，并获取服务器的响应
        HttpResponse httpResponse = HttpRequest.post(GATEWAY_HOST+"/api/name/user")
                .addHeaders(getHeaderMap(json))
                .body(json) // 将JSON字符串设置为请求体
                .execute(); // 执行请求
        // 打印服务器返回的状态码
        System.out.println(httpResponse.getStatus());
        // 获取服务器返回的结果
        String result = httpResponse.body();
        // 打印服务器返回的结果
        System.out.println(result);
        // 返回服务器返回的结果
        return result;
    }
}
