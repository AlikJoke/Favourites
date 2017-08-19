package ru.projects.favourites.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import ru.projects.favourites.rest.resources.UserResource;
import ru.projects.favourites.security.SecurityReference;

@SpringUI(path = RegisterUI.PATH)
@Theme(LoginUI.THEME_NAME)
@Title(LoginUI.TITLE_UI)
public class RegisterUI extends UI implements UIExceptionConfigurer {

	private static final long serialVersionUID = 5838428951413137884L;

	final static String PATH = "/favourites/register";
	final static String THEME_NAME = "valo";
	final static String TITLE_UI = "Create account";

	private TextField username;
	private PasswordField password;
	private Button createAccountBtn;
	private Button backToLoginBtn;
	private VerticalLayout content;
	private TextField email;

	@Autowired
	private SecurityReference security;

	public RegisterUI() {
		super();
		this.content = new VerticalLayout();
		this.username = new TextField("Username");
		this.email = new TextField("E-mail");
		this.password = new PasswordField("Password");
		this.createAccountBtn = new Button("Create account");
		this.backToLoginBtn = new Button("Sign In");
	}

	@Override
	protected void init(VaadinRequest request) {
		this.username.setRequiredIndicatorVisible(true);
		this.username.setPlaceholder("Enter you username");

		this.email.setRequiredIndicatorVisible(true);
		this.email.setPlaceholder("Enter you e-mail");

		this.password.setRequiredIndicatorVisible(true);
		this.password.setPlaceholder("Enter you password");

		this.backToLoginBtn.addClickListener(click -> getUI().getPage().setLocation(LoginUI.PATH));
		this.createAccountBtn.addClickListener(click -> security.create(validateAndCreate()));

		content.addComponent(this.backToLoginBtn);
		content.addComponent(this.username);
		content.addComponent(this.email);
		content.addComponent(this.password);
		content.addComponent(this.createAccountBtn);

		content.setComponentAlignment(this.backToLoginBtn, Alignment.TOP_RIGHT);
		content.setComponentAlignment(this.createAccountBtn, Alignment.MIDDLE_LEFT);
		content.setComponentAlignment(this.email, Alignment.MIDDLE_LEFT);
		content.setComponentAlignment(this.username, Alignment.MIDDLE_LEFT);
		content.setComponentAlignment(this.password, Alignment.MIDDLE_LEFT);

		configureUIException(content).setContent(content);
	}

	private UserResource validateAndCreate() {
		final String username = this.username.getValue();
		final String email = this.email.getValue();
		final String password = this.password.getValue();
		final LocalDate regDate = LocalDate.now();
		final LocalDateTime lastLoggedDT = LocalDateTime.now();

		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(email) || StringUtils.isEmpty(password))
			throw new IllegalArgumentException("Please, check you data! Username, e-mail and password can't be empty!");

		return new UserResource(username, null, email, lastLoggedDT, regDate, password);
	}
}
