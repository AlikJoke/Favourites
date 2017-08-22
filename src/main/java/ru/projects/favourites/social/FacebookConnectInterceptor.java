package ru.projects.favourites.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.WebRequest;

@Component
public class FacebookConnectInterceptor implements ConnectInterceptor<Facebook> {

	@Autowired
	private ConnectionSignUp fbSignUp;

	@Override
	public void postConnect(Connection<Facebook> arg0, WebRequest arg1) {
		fbSignUp.execute(arg0);
	}

	@Override
	public void preConnect(ConnectionFactory<Facebook> arg0, MultiValueMap<String, String> arg1, WebRequest arg2) {

	}

}
