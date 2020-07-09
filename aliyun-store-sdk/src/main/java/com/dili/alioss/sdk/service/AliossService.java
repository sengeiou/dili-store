package com.dili.alioss.sdk.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.dili.alioss.sdk.domain.AliossProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.InputStream;

/**
 * 阿里云OSS服务
 */
@Service
public class AliossService {
    @Autowired
    AliossProperties aliossProperties;

    OSS ossClient;
    @PostConstruct
    public void init(){
        //生成OSSClient
        ossClient = new OSSClientBuilder().build(aliossProperties.getEndpoint(), aliossProperties.getAccessKeyId(), aliossProperties.getAccessKeySecret());
    }

    /**
     *
     * @param bucketName
     * @param storageClass 分为StorageClass.Standard:标准， StorageClass.IA低频和StorageClass.Archive归档。 默认为StorageClass.Standard
     * @param dataRedundancyType
     */
    public void createBucket(String bucketName, StorageClass storageClass, DataRedundancyType dataRedundancyType){
        if(StringUtils.isBlank(bucketName)){
            throw new OSSException("bucketName为空");
        }
        // 创建CreateBucketRequest对象。
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
        // 如果创建存储空间的同时需要指定存储类型以及数据容灾类型, 可以参考以下代码。
        // 此处以设置存储空间的存储类型为标准存储为例。
        if(storageClass != null) {
            createBucketRequest.setStorageClass(storageClass);
        }
        // 默认情况下，数据容灾类型为本地冗余存储，即DataRedundancyType.LRS。如果需要设置数据容灾类型为同城冗余存储，请替换为DataRedundancyType.ZRS。
         createBucketRequest.setDataRedundancyType(DataRedundancyType.LRS);
        // 创建存储空间。
        ossClient.createBucket(createBucketRequest);
    }

    /**
     * 获取BucketInfo
     * @param bucketName
     * @return
     */
    public BucketInfo getBucketInfo(String bucketName){
        if(StringUtils.isBlank(bucketName)){
            throw new OSSException("bucketName为空");
        }
//        System.out.println("Bucket " + bucketName + "的信息如下：");
//        System.out.println("\t数据中心：" + info.getBucket().getLocation());
//        System.out.println("\t创建时间：" + info.getBucket().getCreationDate());
//        System.out.println("\t用户标志：" + info.getBucket().getOwner());
        return ossClient.getBucketInfo(bucketName);
    }

    /**
     * 根据文件路径和key上传
     * @param bucketName
     * @param fileKey 文件名或生成一个唯一的串, 不指定key的情况下，以文件内容的hash值作为文件名
     * @param file
     * @return
     */
    public PutObjectResult putObject(String bucketName, String fileKey, File file) {
        if(StringUtils.isBlank(bucketName)){
            throw new OSSException("bucketName为空");
        }
        return ossClient.putObject(bucketName, fileKey, file);
    }

    /**
     * 根据输入流路径和key上传
     * @param bucketName
     * @param fileKey 文件名或生成一个唯一的串, 不指定key的情况下，以文件内容的hash值作为文件名
     * @param is
     * @return
     */
    public PutObjectResult putObject(String bucketName, String fileKey, InputStream is) {
        if(StringUtils.isBlank(bucketName)){
            throw new OSSException("bucketName为空");
        }
        return ossClient.putObject(bucketName, fileKey, is);
    }


    /**
     * 根据bucketName和key删除图片
     * @param bucketName
     * @param fileKey
     */
    public void deleteObject(String bucketName, String fileKey){
        if(StringUtils.isBlank(bucketName)){
            throw new OSSException("bucketName为空");
        }
        if(StringUtils.isBlank(fileKey)){
            throw new OSSException("fileKey为空");
        }
        ossClient.deleteObject(bucketName, fileKey);
    }

    /**
     * 判断文件是否存在
     * @param bucketName
     * @param fileKey
     * @return
     */
    public Boolean exists(String bucketName, String fileKey){
        if(StringUtils.isBlank(bucketName)){
            throw new OSSException("bucketName为空");
        }
        if(StringUtils.isBlank(fileKey)){
            throw new OSSException("fileKey为空");
        }
        return ossClient.doesObjectExist(bucketName, fileKey);
    }

}
