package ru.projects.favourites.ui;

import javax.validation.constraints.NotNull;

import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * Интерфейс задает дефолтный конфигуратор обработки ошибок на пользовательских
 * формах.
 * 
 * @author Alimurad A. Ramazanov
 * @since 20.08.2017
 * @version 1.0.0
 *
 */
public interface UIExceptionConfigurer {

	/**
	 * Дефолтный метод, конфигурирующий обработку ошибок на форме.
	 * <p>
	 * 
	 * @see UI
	 * @param content - компонент, на котором будет отображена ошибка; не может быть {@code null}.
	 * @return текущую форму.
	 */
	@NotNull
	default UI configureUIException(@NotNull VerticalLayout content) {
		UI.getCurrent().setErrorHandler(new DefaultErrorHandler() {

			private static final long serialVersionUID = 7896726324438602112L;

			@Override
			public void error(com.vaadin.server.ErrorEvent event) {
				String cause = "<b>Error: </b>";
				for (Throwable t = event.getThrowable(); t != null; t = t.getCause())
					if (t.getCause() == null)
						cause += t.getMessage() + "<br/>";

				content.addComponent(new Label(cause, ContentMode.HTML));
				doDefault(event);
			}
		});

		return UI.getCurrent();
	}
}
