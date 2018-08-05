package com.cg.wallet.dao;

public interface IQueryMapper {
	public String insert="insert into account(name,email,mobileno,balance) values(?,?,?,?)";
	public String bal="select balance from account where mobileno=?";
	public String acc="select * from account where mobileno=?";
	public String updateAcc="update account set balance=?,date1=? where mobileno=?";
}
