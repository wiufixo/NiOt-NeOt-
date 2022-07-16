package com.sist.nono.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class ProductImageCofig implements WebMvcConfigurer {
	
	@Value("{uploadPath}")
	String uploadPath;
	
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//url에 product로 시작하는 경우 uploadPath에 설정한 폴더를 기준으로 파일을 읽어오도록함
		registry.addResourceHandler("/product/**")
				.addResourceLocations(uploadPath);
	}
}
