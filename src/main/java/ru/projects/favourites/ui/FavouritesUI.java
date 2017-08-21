package ru.projects.favourites.ui;

import java.util.Collection;
import java.util.Map;

import javax.validation.constraints.Null;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.google.common.collect.Maps;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import ru.projects.favourites.dao.DomainOperations;
import ru.projects.favourites.domain.EntityType;
import ru.projects.favourites.domain.Favourite;
import ru.projects.favourites.security.SecurityReference;
import ru.projects.favourites.user.CurrentUser;

@SuppressWarnings("deprecation")
@SpringUI(path = FavouritesUI.PATH)
@Theme(ValoTheme.THEME_NAME)
@Title(FavouritesUI.TITLE_UI)
public class FavouritesUI extends UI implements UIExceptionConfigurer {

	private static final long serialVersionUID = 1170543174888454508L;

	@Autowired
	private DomainOperations domainOperations;

	@Autowired
	private CurrentUser user;

	@Autowired
	private SecurityReference security;
	
	@Autowired
	private FavouritesEditor fvEditor;

	final static String PATH = "/ui/list";
	final static String TITLE_UI = "Favourites";

	private final static String COLUMN_NAME = "Name";
	private final static String COLUMN_LINK = "Link";
	private final static String COLUMN_ORDER = "Order";
	private final static String COLUMN_COUNTER = "Count of usage";

	private final static String OPEN_PAGE_TYPE = "_blank";

	private Map<String, String> name2idMap = Maps.newHashMap();

	private Grid<Favourite> fvGrid;
	private Label label;
	private Button logoutBtn;
	private VerticalLayout content;
	private Button addBtn;
	private Label labelLogin;
	private Button deleteBtn;
	private HorizontalLayout loginInfo;
	private HorizontalLayout buttons;
	private TextField searchField;

	public FavouritesUI() {
		this.fvGrid = new Grid<>(Favourite.class);
		this.content = new VerticalLayout();
		this.loginInfo = new HorizontalLayout();
		this.buttons = new HorizontalLayout();
		this.logoutBtn = new Button("Logout");
		this.addBtn = new Button("Add", FontAwesome.PLUS);
		this.label = new Label("List of favourites pages");
		this.labelLogin = new Label("Login as ");
		this.deleteBtn = new Button("Delete", FontAwesome.MINUS);
		this.searchField = new TextField("Filter");
	}

	@Override
	protected void init(VaadinRequest request) {
		deleteBtn.setDescription("Select items and enter this button for deleting pages from list");

		labelLogin.setValue(labelLogin.getValue() + user.getUsername());

		fvGrid.removeAllColumns();
		fvGrid.setSizeFull();
		name2idMap.put(COLUMN_ORDER, fvGrid.addColumn(Favourite::getOrder).setCaption(COLUMN_ORDER).setMaximumWidth(75)
				.setMinimumWidthFromContent(true).setResizable(false).getId());
		name2idMap.put(COLUMN_NAME,
				fvGrid.addColumn(Favourite::getName).setCaption(COLUMN_NAME).setResizable(true).getId());
		name2idMap.put(COLUMN_LINK,
				fvGrid.addColumn(Favourite::getLink).setCaption(COLUMN_LINK).setResizable(true).getId());
		name2idMap.put(COLUMN_COUNTER, fvGrid.addColumn(Favourite::getCounter).setCaption(COLUMN_COUNTER)
				.setMaximumWidth(150).setMinimumWidthFromContent(true).setResizable(false).getId());
		fvGrid.setSelectionMode(SelectionMode.MULTI);
		fvGrid.addItemClickListener(click -> doClick(click.getColumn(), click.getItem()));
		fvGrid.addSelectionListener(
				listener -> Notification.show("Selected items: " + listener.getAllSelectedItems().size()));

		logoutBtn.addClickListener(click -> {
			security.logout();
			getUI().getPage().setLocation(LoginUI.PATH);
		});

		searchField.setPlaceholder("Enter any value for filtering");

		deleteBtn.addClickListener(listener -> {
			if (fvGrid.getSelectedItems().isEmpty())
				Notification.show("No any items selected!");
			else {
				fvGrid.getSelectedItems().forEach(item -> domainOperations.remove(item));
				Notification.show("Successfully deleted items: " + fvGrid.getSelectedItems().size());
			}
		});

		searchField.addBlurListener(listener -> getFVWithSearch(((TextField) listener.getComponent()).getValue()));

		if (StringUtils.isEmpty(searchField.getValue()))
			getFVWithSearch(null);
		
		loginInfo.addComponent(labelLogin);
		loginInfo.addComponent(logoutBtn);

		buttons.addComponent(addBtn);
		buttons.addComponent(deleteBtn);

		content.addComponent(loginInfo);
		content.addComponent(label);
		content.addComponent(buttons);
		content.addComponent(fvEditor);
		content.addComponent(fvGrid);

		content.setComponentAlignment(this.loginInfo, Alignment.TOP_RIGHT);
		content.setComponentAlignment(this.label, Alignment.MIDDLE_CENTER);
		content.setComponentAlignment(this.buttons, Alignment.MIDDLE_LEFT);

		configureUIException(content).setContent(content);
	}

	public void doClick(Column<Favourite, ?> col, Favourite fv) {
		if (name2idMap.get(col.getId()).equals(COLUMN_LINK)) {
			fv.setCounter(fv.getCounter() + 1);
			domainOperations.update(EntityType.FAVOURITE, fv);

			getUI().getPage().open(fv.getLink(), OPEN_PAGE_TYPE, false);

		}
	}

	@SuppressWarnings("unchecked")
	private void getFVWithSearch(@Null String searchTxt) {
		if (StringUtils.isEmpty(searchTxt))
			fvGrid.setItems((Collection<Favourite>) domainOperations.findAll(user.getUsername()));
		else
			fvGrid.setItems((Collection<Favourite>) domainOperations.findWithFilter(user.getUsername(), searchTxt));
	}

}
