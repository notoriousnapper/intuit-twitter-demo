package com.twitter.util;

import javax.servlet.http.HttpSession;

import com.twitter.model.User;

public class PreLoad {
	private PreLoad(){
		
	}
	public static User preloadPageUserBySession(HttpSession session){
		   // Let this be the wall Page
		String pageUserId = (String) session.getAttribute("pageUserId");
		String pageUserName = (String) session.getAttribute("pageUserName");
		String pageUserHandle  = (String) session.getAttribute("pageUserHandle");
		String pageUserPageId = (String) session.getAttribute("pageUserPageId");
		String pageUserImageUrl = (String) session.getAttribute("pageUserImageUrl");
		

		if ((pageUserImageUrl == null)){
			pageUserId = "0";
			pageUserName = "Please Load Page from Servlet";
			pageUserHandle = "Start Project From Directory Run";
			pageUserPageId = "4";
			pageUserImageUrl = "http://allthingsd.com/files/2012/06/brad_smith_intuit.png";
		}
		
		 User pageUser = new User(Integer.parseInt(pageUserId),pageUserName, "", "", pageUserImageUrl,  pageUserHandle);
		return pageUser;
	}


	public static User preloadUserBySession(HttpSession session){
		String userId = (String) session.getAttribute("userId");
		String userName = (String) session.getAttribute("userName");
		String userHandle  = (String) session.getAttribute("userHandle");
		String imageUrl = (String) session.getAttribute("imageUrl");
	    User user = new User(Integer.parseInt(userId), userName, "", "", imageUrl, userHandle);
	    
	    return user;
	}
	
	

}
