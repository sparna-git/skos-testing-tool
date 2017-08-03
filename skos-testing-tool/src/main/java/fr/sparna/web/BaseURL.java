package fr.sparna.web;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

public class BaseURL {

	public static URL buildBaseURL(HttpServletRequest request) {
		try {
			return new URL("http://"+request.getServerName()+((request.getServerPort() != 80)?":"+request.getServerPort():"")+request.getContextPath());
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
