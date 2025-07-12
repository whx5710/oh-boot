package com.finn.core.utils;

import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * http请求工具类
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2025-06-14
 */
public class HttpUtil {
    /**
     * get请求
     *
     * @param url
     * @param params 请求参数
     * @return
     */
    public static String get(String url, Map<String, Object> params) {
        return get(url, params, null);
    }

    /**
     * get请求
     *
     * @param url
     * @param params  请求参数
     * @param headers 请求头
     * @return
     */
    public static String get(String url, Map<String, Object> params, MultiValueMap<String, String> headers) {
        return request(url, params, headers, HttpMethod.GET);
    }

    /**
     * post请求
     *
     * @param url
     * @param params 请求参数
     * @return
     */
    public static String post(String url, Map<String, Object> params) {
        return post(url, params, null);
    }

    /**
     * post请求
     *
     * @param url
     * @param params  请求参数
     * @param headers 请求头
     * @return
     */
    public static String post(String url, Map<String, Object> params, MultiValueMap<String, String> headers) {
        return request(url, params, headers, HttpMethod.POST);
    }

    /**
     * put请求
     *
     * @param url
     * @param params 请求参数
     * @return
     */
    public static String put(String url, Map<String, Object> params) {
        return put(url, params, null);
    }

    /**
     * put请求
     *
     * @param url
     * @param params  请求参数
     * @param headers 请求头
     * @return
     */
    public static String put(String url, Map<String, Object> params, MultiValueMap<String, String> headers) {
        return request(url, params, headers, HttpMethod.PUT);
    }

    /**
     * delete请求
     *
     * @param url
     * @param params 请求参数
     * @return
     */
    public static String delete(String url, Map<String, Object> params) {
        return delete(url, params, null);
    }

    /**
     * delete请求
     *
     * @param url
     * @param params  请求参数
     * @param headers 请求头
     * @return
     */
    public static String delete(String url, Map<String, Object> params, MultiValueMap<String, String> headers) {
        return request(url, params, headers, HttpMethod.DELETE);
    }

    /**
     * 表单请求
     *
     * @param url
     * @param params  请求参数
     * @param headers 请求头
     * @param method  请求方式
     * @return
     */
    public static String request(String url, Map<String, Object> params, MultiValueMap<String, String> headers, HttpMethod method) {
        if (params == null) {
            params = new HashMap<>();
        }
        return request(url, params, headers, method, MediaType.APPLICATION_JSON).getBody();
    }

    /**
     * http请求
     *
     * @param url
     * @param params    请求参数
     * @param headers   请求头
     * @param method    请求方式
     * @param mediaType 参数类型
     * @return
     */
    public static ResponseEntity<String> request(String url, Object params, MultiValueMap<String, String> headers, HttpMethod method, MediaType mediaType) {
        if (url == null || url.trim().isEmpty()) {
            return null;
        }
        RestTemplate client = new RestTemplate();
        // header
        HttpHeaders httpHeaders = new HttpHeaders();
        if (headers != null) {
            httpHeaders.addAll(headers);
        }
        // 提交方式：表单、json
        httpHeaders.setContentType(mediaType);
        HttpEntity<Object> httpEntity = new HttpEntity<>(params, httpHeaders);
        return client.exchange(url, method, httpEntity, String.class);
    }
}
