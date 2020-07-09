package com.dili.alioss.sdk.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置七牛属性
 * Created by asiamaster on 2018/11/02
 */
@Component
@ConfigurationProperties(prefix="qiniu", ignoreInvalidFields=true)
public class QiniuProperties {

	//初始化以避免空
	@Value("${accessKey:}")
	private String accessKey;

	@Value("${secretKey:}")
	private String secretKey;

	@Value("${bucketName:dili-fresh}")
	private String bucketName;

	@Value("${host:http://img10.nong12.com/}")
	private String host;

	@Value("${namespace:groupbuy}")
	private String namespace;

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

}
