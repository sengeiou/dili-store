package com.dili.alioss.sdk.controller;

import com.dili.alioss.sdk.domain.AliossProperties;
import com.dili.alioss.sdk.service.AliossService;
import com.dili.ss.domain.BaseOutput;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * @author
 */
@Controller
@RequestMapping("/uploadImage")
public class UploadImageController {

	@Autowired
	private AliossService uploadService;

	@Autowired
	AliossProperties aliossProperties;

	/**
	 * 批量上传文件
	 * 返回文件名为Key，URL为值的Map
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/upload.action")
	public BaseOutput<Map<String, String>> upload(MultipartHttpServletRequest request) throws IOException {
		Map<String, String> images = Maps.newHashMap();
		for (MultipartFile file : request.getFileMap().values()) {
			String fileKey = genKey();
			uploadService.putObject(aliossProperties.getBucketName(), fileKey, file.getInputStream());
			images.put(fileKey, aliossProperties.getHost() + fileKey);
		}
		return BaseOutput.success().setData(images);
	}


	/**
	 * 根据bucketName和key构建key
	 * @return
	 */
	private String genKey(){
		return  UUID.randomUUID().toString().replace("-", "").toLowerCase();
	}
}
