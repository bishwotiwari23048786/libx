package com.Libx.model;

import java.sql.Date;

public class BorrowRecord {
	private int borrow_id;
    private int user_id;
    private int book_id;
    private Date borrow_date;
    private Date due_date;
    private Date return_date;
    
    public BorrowRecord() {
		// Default Constructor
	}

	public BorrowRecord(int borrow_id, int user_id, int book_id, Date borrow_date, Date due_date, Date return_date) {
		super();
		this.borrow_id = borrow_id;
		this.user_id = user_id;
		this.book_id = book_id;
		this.borrow_date = borrow_date;
		this.due_date = due_date;
		this.return_date = return_date;
	}

	public int getBorrow_id() {
		return borrow_id;
	}

	public void setBorrow_id(int borrow_id) {
		this.borrow_id = borrow_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getBook_id() {
		return book_id;
	}

	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}

	public Date getBorrow_date() {
		return borrow_date;
	}

	public void setBorrow_date(Date borrow_date) {
		this.borrow_date = borrow_date;
	}

	public Date getDue_date() {
		return due_date;
	}

	public void setDue_date(Date due_date) {
		this.due_date = due_date;
	}

	public Date getReturn_date() {
		return return_date;
	}

	public void setReturn_date(Date return_date) {
		this.return_date = return_date;
	}

}

