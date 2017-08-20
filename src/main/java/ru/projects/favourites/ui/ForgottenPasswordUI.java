package ru.projects.favourites.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import ru.projects.favourites.notification.NotificationSender;

@SpringUI(path = ForgottenPasswordUI.PATH)
@Theme(ValoTheme.THEME_NAME)
@Title(ForgottenPasswordUI.TITLE_UI)
public class ForgottenPasswordUI extends UI implements UIExceptionConfigurer {

	private static final long serialVersionUID = 1483747970337268768L;

	final static String PATH = "/ui/remember-password";
	final static String TITLE_UI = "Remember password";

	private Label label;
	private VerticalLayout content;
	private TextField username;
	private TextField email;
	private Button sendEmailBtn;
	private Label statusLabel;
	private Button backToLoginBtn;

	@Autowired
	private NotificationSender sender;

	public ForgottenPasswordUI() {
		super();
		this.label = new Label("Please, enter you e-mail and username and we send your password in letter");
		this.content = new VerticalLayout();
		this.username = new TextField("Username");
		this.email = new TextField("E-mail");
		this.sendEmailBtn = new Button("Send");
		this.statusLabel = new Label("Letter was successfully send! Check you mail box.");
		this.backToLoginBtn = new Button("<< Back to login form");
	}

	@Override
	protected void init(VaadinRequest request) {
		this.backToLoginBtn.setStyleName("link");
		this.backToLoginBtn.addClickListener(click -> getUI().getPage().setLocation(LoginUI.PATH));

		this.statusLabel.setVisible(false);

		this.username.setPlaceholder("Enter you username");
		this.email.setPlaceholder("Enter you e-mail");

		this.sendEmailBtn.addClickListener(click -> {
			validate();
			sender.rememberPasswordSend(email.getValue(), username.getValue());
			this.statusLabel.setVisible(true);
		});

		content.addComponent(this.backToLoginBtn);
		content.addComponent(this.label);
		content.addComponent(this.username);
		content.addComponent(this.email);
		content.addComponent(this.statusLabel);
		content.addComponent(this.sendEmailBtn);

		content.setComponentAlignment(this.backToLoginBtn, Alignment.TOP_LEFT);
		content.setComponentAlignment(this.label, Alignment.MIDDLE_CENTER);
		content.setComponentAlignment(this.username, Alignment.MIDDLE_CENTER);
		content.setComponentAlignment(this.email, Alignment.MIDDLE_CENTER);
		content.setComponentAlignment(this.sendEmailBtn, Alignment.MIDDLE_CENTER);
		content.setComponentAlignment(this.statusLabel, Alignment.MIDDLE_CENTER);

		configureUIException(content).setContent(content);
	}

	private void validate() {
		if (StringUtils.isEmpty(username.getValue()) || StringUtils.isEmpty(email.getValue()))
			throw new IllegalArgumentException("Username and e-mail can't be empty!");
	}
}
