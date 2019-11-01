package com.hg.jeedev.common.utils;

import java.util.Map;

public interface PicSendFormService {
	public String upload(String url,String filePath,String param,String Authorization);
	public String httpPost(String url,String Authorization, Map<String, String> paramMap);
}
