package com.dili.alioss.sdk.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dili.ss.domain.BaseOutput;
import com.dili.alioss.sdk.domain.QiniuProperties;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dili.alioss.sdk.service.UploadService;

/**
 * @author
 */
@Controller
@RequestMapping("/uploadImage")
public class UploadImageController {

	@Autowired
	private UploadService uploadService;

	@Autowired
	QiniuProperties qiniuProperties;

	@ResponseBody
	@RequestMapping("/uploadImage.action")
	public Map<Object, Object> uploadImage(MultipartHttpServletRequest request) throws IOException {
		List<String> images = new ArrayList<>();
		for (MultipartFile file : request.getFileMap().values()) {
			String key = uploadService.upload(file.getInputStream(), null);
			images.add(qiniuProperties.getHost() + key);
		}
		Map<Object, Object> result = new HashMap<>(2);
		result.put("errno", 0);
		result.put("data", images);
		return result;
	}

	@ResponseBody
	@RequestMapping("/upload.action")
	public BaseOutput<Map<String, String>> upload(MultipartHttpServletRequest request) throws IOException {
		Map<String, String> images = Maps.newHashMap();
		for (MultipartFile file : request.getFileMap().values()) {
			String key = uploadService.upload(file.getInputStream(), null);
			images.put(key, qiniuProperties.getHost() + key);
		}
		return BaseOutput.success().setData(images);
	}
}
