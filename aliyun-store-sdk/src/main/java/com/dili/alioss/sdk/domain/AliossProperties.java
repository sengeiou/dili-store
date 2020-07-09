package com.dili.alioss.sdk.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 配置阿里OSS属性
 * Created by asiamaster on 2020/05/22
 */
@Component
@ConfigurationProperties(prefix="alioss", ignoreInvalidFields=true)
public class AliossProperties {

	@Value("${endpoint:http://oss-cn-chengdu.aliyuncs.com}")
	private String endpoint;

	@Value("${accessKeyId:LTAI4GHkJPZuuS7vK2UTwiHc}")
	private String accessKeyId;

	@Value("${accessKeySecret:IQ4V3aMySyUEXnqH0oUIi16X5PlmGi}")
	private String accessKeySecret;

	@Value("${host:https://asiamaster.oss-cn-chengdu.aliyuncs.com/}")
	private String host;

	@Value("${bucketName:asiamaster}")
	private String bucketName;

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
}
