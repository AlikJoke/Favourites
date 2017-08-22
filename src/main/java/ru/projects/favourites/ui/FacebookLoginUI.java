package ru.projects.favourites.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import ru.projects.favourites.security.SecurityReference;

@SpringUI(path = FacebookLoginUI.PATH)
@Theme(ValoTheme.THEME_NAME)
@Title(FacebookLoginUI.TITLE_UI)
public class FacebookLoginUI extends UI implements UIExceptionConfigurer {

	private static final long serialVersionUID = -992670491587923981L;
	
	final static String PATH = "/ui/auth/facebook";
	final static String TITLE_UI = "Facebook login page";

	private Label label;
	private TextField username;
	private PasswordField password;
	private Button loginButton;
	private VerticalLayout content;
	private Button backLoginBtn;
	private CheckBox rememberMe;
	private HorizontalLayout loginComposite;

	@Autowired
	private SecurityReference security;

	public FacebookLoginUI() {
		super();
		this.label = new Label("Login from Facebook account!");
		this.content = new VerticalLayout();
		this.username = new TextField("Login");
		this.password = new PasswordField("Password");
		this.loginButton = new Button("Sign in");
		this.rememberMe = new CheckBox("Remember me");
		this.backLoginBtn = new Button("Back to login page");
		this.loginComposite = new HorizontalLayout();
	}

	@Override
	protected void init(VaadinRequest request) {
		this.username.setPlaceholder("Enter you login");
		this.password.setPlaceholder("Enter you password");

		this.loginButton.addClickListener(click -> {
			security.loginByFacebook(this.username.getValue(), this.password.getValue());
			getUI().getPage().setLocation(FavouritesUI.PATH);
		});
		this.backLoginBtn.addClickListener(click -> getUI().getPage().setLocation(LoginUI.PATH));

		loginComposite.addComponent(rememberMe);
		loginComposite.addComponent(loginButton);

		content.addComponent(this.backLoginBtn);
		content.addComponent(this.label);
		content.addComponent(this.username);
		content.addComponent(this.password);
		content.addComponent(this.loginComposite);

		loginComposite.setComponentAlignment(this.loginButton, Alignment.MIDDLE_CENTER);
		loginComposite.setComponentAlignment(this.rememberMe, Alignment.MIDDLE_CENTER);

		content.setComponentAlignment(this.backLoginBtn, Alignment.TOP_LEFT);
		content.setComponentAlignment(this.label, Alignment.TOP_CENTER);
		content.setComponentAlignment(this.username, Alignment.MIDDLE_CENTER);
		content.setComponentAlignment(this.password, Alignment.MIDDLE_CENTER);
		content.setComponentAlignment(this.loginComposite, Alignment.MIDDLE_CENTER);

		configureUIException(content).setContent(content);
	}

}
