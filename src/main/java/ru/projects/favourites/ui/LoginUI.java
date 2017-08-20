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

@SpringUI(path = LoginUI.PATH)
@Theme(ValoTheme.THEME_NAME)
@Title(LoginUI.TITLE_UI)
public class LoginUI extends UI implements UIExceptionConfigurer {

	private static final long serialVersionUID = 8655698055156902998L;

	final static String PATH = "/ui/login";
	final static String TITLE_UI = "Login page";

	private Label label;
	private TextField username;
	private PasswordField password;
	private Button loginButton;
	private VerticalLayout content;
	private Button registerButton;
	private CheckBox rememberMe;
	private Button forgottenPassword;
	private HorizontalLayout loginComposite;

	@Autowired
	private SecurityReference security;

	public LoginUI() {
		super();
		this.label = new Label("Welcome to FV application!");
		this.content = new VerticalLayout();
		this.username = new TextField("Username");
		this.password = new PasswordField("Password");
		this.loginButton = new Button("Sign in");
		this.rememberMe = new CheckBox("Remember me");
		this.registerButton = new Button("Join now");
		this.forgottenPassword = new Button("Forgot you password?");
		this.loginComposite = new HorizontalLayout();
	}

	@Override
	protected void init(VaadinRequest request) {
		this.username.setPlaceholder("Enter you username");
		this.password.setPlaceholder("Enter you password");

		this.forgottenPassword.setStyleName("link");

		this.loginButton.addClickListener(click -> {
			security.login(this.username.getValue(), this.password.getValue());
			getUI().getPage().setLocation(FavouritesUI.PATH);
		});
		this.registerButton.addClickListener(click -> getUI().getPage().setLocation(RegisterUI.PATH));
		this.forgottenPassword.addClickListener(click -> getUI().getPage().setLocation(ForgottenPasswordUI.PATH));

		loginComposite.addComponent(rememberMe);
		loginComposite.addComponent(loginButton);

		content.addComponent(this.registerButton);
		content.addComponent(this.label);
		content.addComponent(this.username);
		content.addComponent(this.password);
		content.addComponent(this.forgottenPassword);
		content.addComponent(this.loginComposite);

		loginComposite.setComponentAlignment(this.loginButton, Alignment.MIDDLE_CENTER);
		loginComposite.setComponentAlignment(this.rememberMe, Alignment.MIDDLE_CENTER);

		content.setComponentAlignment(this.registerButton, Alignment.TOP_RIGHT);
		content.setComponentAlignment(this.label, Alignment.TOP_CENTER);
		content.setComponentAlignment(this.username, Alignment.MIDDLE_CENTER);
		content.setComponentAlignment(this.password, Alignment.MIDDLE_CENTER);
		content.setComponentAlignment(this.loginComposite, Alignment.MIDDLE_CENTER);
		content.setComponentAlignment(this.forgottenPassword, Alignment.MIDDLE_CENTER);

		configureUIException(content).setContent(content);
	}

}
