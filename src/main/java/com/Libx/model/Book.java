package com.Libx.model;

public class Book {
	private int book_id;
	private String title;
	private String author;
	private String genre;
	private String isbn;
	private String publisher;
	private String cover_img_path;
	private int publish_year;
	private int total_copies;
	private int available_copies; //not in the database
	
	public Book() {
		// Default Constructor
	}
	
	public Book(String title, String author, String genre, String isbn, String publisher, String coverImgPath, int publishYear,
			int totalCopies) {
		super();
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.isbn = isbn;
		this.publisher = publisher;
		this.cover_img_path = coverImgPath;
		this.publish_year = publishYear;
		this.total_copies = totalCopies;
	}

	public int getBook_id() {
		return book_id;
	}

	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getPublish_year() {
		return publish_year;
	}

	public void setPublish_year(int publish_year) {
		this.publish_year = publish_year;
	}

	public int getTotal_copies() {
		return total_copies;
	}

	public void setTotal_copies(int total_copies) {
		this.total_copies = total_copies;
	}

	public String getCover_img_path() {
		return cover_img_path;
	}

	public void setCover_img_path(String cover_img_path) {
		this.cover_img_path = cover_img_path;
	}
	
	public int getAvailable_copies() {
	    return available_copies;
	}

	public void setAvailable_copies(int available_copies) {
	    this.available_copies = available_copies;
	}
	
}
