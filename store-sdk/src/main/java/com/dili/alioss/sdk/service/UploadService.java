package com.dili.alioss.sdk.service;

import com.dili.alioss.sdk.domain.QiniuProperties;
import com.dili.alioss.sdk.exception.QiniuUploadException;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.UUID;

/**
 * 七牛上传文件服务
 */
@Service
public class UploadService {
    @Autowired
    QiniuProperties qiniuProperties;
    UploadManager uploadManager;
    BucketManager bucketManager;
    Auth auth;
    @PostConstruct
    public void init(){
        //密钥配置
        auth = Auth.create(qiniuProperties.getAccessKey(), qiniuProperties.getSecretKey());
        ///////////////////////指定上传的Zone的信息//////////////////
        //第一种方式: 指定具体的要上传的zone
        //注：该具体指定的方式和以下自动识别的方式选择其一即可
        //要上传的空间(bucket)的存储区域为华东时
        // Zone z = Zone.zone0();
        //要上传的空间(bucket)的存储区域为华北时
        // Zone z = Zone.zone1();
        //要上传的空间(bucket)的存储区域为华南时
        // Zone z = Zone.zone2();
        //第二种方式: 自动识别要上传的空间(bucket)的存储区域是华东、华北、华南。
        Zone z = Zone.huabei();
        Configuration c = new Configuration(z);
        //创建上传对象
        uploadManager = new UploadManager(c);
        //实例化一个BucketManager对象
        bucketManager = new BucketManager(auth, c);
    }

    /**
     * 根据文件路径和key上传
     * @param filePath
     * @param key   文件名或生成一个唯一的串, 不指定key的情况下，以文件内容的hash值作为文件名
     * @return
     */
    public String upload(String filePath, String key) {
        try {
            //调用put方法上传
            Response res = uploadManager.put(filePath, buildKey(null, key), getUpToken());
            //返回的信息
            return res.jsonToMap().get("key").toString();
        } catch (QiniuException e) {
            Response r = e.response;
            StringBuilder sb = new StringBuilder("上传异常:");
            sb.append(r.toString());
            // 请求失败时打印的异常的信息
//            System.out.println(r.toString());
            try {
                //响应的文本信息
//                System.out.println(r.bodyString());
                sb.append("\r\n响应的文本:" + r.bodyString());
                throw new QiniuUploadException(sb.toString());
            } catch (QiniuException e1) {
                //ignore
            }
            return null;
        }
    }

    /**
     * 根据输入流和key上传
     * @param is
     * @param key   文件名或生成一个唯一的串, 不指定key的情况下，以文件内容的hash值作为文件名
     * @return
     */
    public String upload(InputStream is, String key) {
        try {
            //调用put方法上传
            Response res = uploadManager.put(is, buildKey(null, key), getUpToken(), null, null);
            //返回的信息
            return res.jsonToMap().get("key").toString();
        } catch (QiniuException e) {
            Response r = e.response;
            StringBuilder sb = new StringBuilder("上传异常:");
            sb.append(r.toString());
            // 请求失败时打印的异常的信息
//            System.out.println(r.toString());
            try {
                //响应的文本信息
//                System.out.println(r.bodyString());
                sb.append("\r\n响应的文本:" + r.bodyString());
                throw new QiniuUploadException(sb.toString());
            } catch (QiniuException e1) {
                //ignore
            }
            return null;
        }
    }

    /**
     * 根据字符数组和key上传
     * @param bytes
     * @param key   文件名或生成一个唯一的串, 不指定key的情况下，以文件内容的hash值作为文件名
     * @return
     */
    public String upload(byte[] bytes, String key) {
        try {
            //调用put方法上传
            Response res = uploadManager.put(bytes, buildKey(null, key), getUpToken());
            //返回的信息
            return res.jsonToMap().get("key").toString();
        } catch (QiniuException e) {
            Response r = e.response;
            StringBuilder sb = new StringBuilder("上传异常:");
            sb.append(r.toString());
            // 请求失败时打印的异常的信息
//            System.out.println(r.toString());
            try {
                //响应的文本信息
//                System.out.println(r.bodyString());
                sb.append("\r\n响应的文本:" + r.bodyString());
                throw new QiniuUploadException(sb.toString());
            } catch (QiniuException e1) {
                //ignore
            }
            return null;
        }
    }

    /**
     * 根据key删除图片
     * @param key
     */
    public void delete(String key){
        try {
            bucketManager.delete(qiniuProperties.getBucketName(), key);
        } catch (QiniuException e) {
            e.printStackTrace();
            Response r = e.response;
            StringBuilder sb = new StringBuilder("删除异常:");
            sb.append(r.toString());
            try {
                //响应的文本信息
//                System.out.println(r.bodyString());
                sb.append("\r\n响应的文本:" + r.bodyString());
                throw new QiniuUploadException(sb.toString());
            } catch (QiniuException e1) {
                //ignore
            }
        }
    }

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    private String getUpToken() {
        return auth.uploadToken(qiniuProperties.getBucketName());
    }

    /**
     * 根据命名空间和key构建七牛key
     * @param namespace
     * @param key
     * @return
     */
    private String buildKey(String namespace, String key){
        if(StringUtils.isBlank(namespace)){
            namespace = qiniuProperties.getNamespace();
        }
        if(StringUtils.isBlank(key)){
            key =  UUID.randomUUID().toString().replace("-", "").toLowerCase();
        }
        StringBuilder keySb = new StringBuilder();
        return keySb.append(qiniuProperties.getBucketName()).append("/").append(namespace).append("/").append(key).toString();
    }
}
