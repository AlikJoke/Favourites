package ru.projects.favourites.domain;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

/**
 * Класс, описывающий один объект избранного (страницу).
 * 
 * @see DomainObject
 * 
 * @author Alimurad A. Ramazanov
 * @since 15.08.2017
 * @version 1.0.0
 *
 */
public class Favourite extends DomainObject {

	@NotNull
	private String name;
	
	@NotNull
	private String link;
	
	@NotNull
	private Long counter;
	
	@NotNull
	private Integer order;
	
	@NotNull
	private LocalDateTime addingDT;
	
	@NotNull
	private String userName;

	public Favourite(String link, String name, String username) {
		super(link.hashCode() + name.hashCode() + username.hashCode());
		this.link = link;
		this.name = name;
		this.userName = username;
		this.addingDT = LocalDateTime.now();
	}

	public Favourite(String uid, String username, LocalDateTime addingDT, LocalDateTime deletingDT) {
		super(uid, deletingDT);
		this.addingDT = addingDT;
		this.userName = username;
	}

	public Favourite(String name, String link, String uid, String username, LocalDateTime addingDT,
			LocalDateTime deletingDT) {
		this(uid, username, addingDT, deletingDT);
		this.name = name;
		this.link = link;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Long getCounter() {
		return counter;
	}

	public void setCounter(Long counter) {
		this.counter = counter;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getUID() {
		return uid;
	}

	@Override
	public String getEntityName() {
		return EntityType.FAVOURITE.getName();
	}

	public LocalDateTime getAddingDT() {
		return addingDT;
	}

	public String getUserId() {
		return userName;
	}
}
