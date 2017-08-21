package ru.projects.favourites.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import ru.projects.favourites.dao.DomainOperations;
import ru.projects.favourites.domain.EntityType;
import ru.projects.favourites.domain.Favourite;

@SpringComponent
@UIScope
public class FavouritesEditor extends VerticalLayout {

	private static final long serialVersionUID = 6457027718159009049L;

	@Autowired
	private DomainOperations domainOperations;

	private Favourite fv;

	private TextField name;
	private TextField link;
	private TextField order;

	private Button saveBtn;
	private Button cancelBtn;

	private CssLayout actions;
	private Binder<Favourite> binder;

	public FavouritesEditor() {
		super();
		this.name = new TextField("Name");
		this.link = new TextField("Link");
		this.order = new TextField("Order");
		this.saveBtn = new Button("Save");
		this.cancelBtn = new Button("Cancel");
		this.actions = new CssLayout(saveBtn, cancelBtn);
		this.binder = new Binder<>(Favourite.class);
		init();
	}

	private void init() {
		addComponents(name, link, order, actions);
		binder.bindInstanceFields(this);

		setSpacing(true);

		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		saveBtn.setStyleName(ValoTheme.BUTTON_PRIMARY);
		saveBtn.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		saveBtn.addClickListener(e -> saveOrUpdate());
		cancelBtn.addClickListener(e -> cancel());
		setVisible(false);
	}

	private void cancel() {
		if (fv == null) {
			setVisible(false);
			return;
		}

		final boolean saved = domainOperations.findById(EntityType.FAVOURITE, fv.getUID()) != null;

		cancelBtn.setVisible(saved);
		setVisible(true);
		binder.setBean(fv);

		saveBtn.focus();
	}

	private void saveOrUpdate() {
		if (fv == null) {
			setVisible(false);
			return;
		}

		final boolean saved = domainOperations.findById(EntityType.FAVOURITE, fv.getUID()) != null;
		if (saved) {
			domainOperations.update(EntityType.FAVOURITE, fv);
			Notification.show("Item was successfully updated");
		} else {
			domainOperations.save(fv);
			Notification.show("Item was successfully saved");
		}
	}
}
