package com.cg.wallet.dao;



import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;




import java.time.LocalDate;
import java.time.LocalDateTime;

import com.cg.wallet.bean.Account;
import com.cg.wallet.db.WalletdB;
import com.cg.wallet.exception.WalletException;

public class WalletDaoImpl implements IWalletDao {
	

	@Override
	public String createAccount(Account acc) throws WalletException {
		Connection con=WalletdB.getConnection();
		PreparedStatement stat;
		try{
			con.setAutoCommit(false);
			stat = con.prepareStatement(IQueryMapper.insert);
			stat.setString(1, acc.getName());
			stat.setString(2, acc.getEmail());
			stat.setString(3, acc.getMobileno());
			stat.setDouble(4, acc.getBalance());
			int res=stat.executeUpdate();
			if(res==1){
				con.commit();
				return acc.getMobileno();
			}else{
				throw new WalletException("Could not create account");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new WalletException(e.getMessage());
		}
		
	}

	@Override
	public double showBalance(String mobileNo) throws WalletException {
		Connection con=WalletdB.getConnection();
		PreparedStatement stat;
		try{
			stat=con.prepareStatement(IQueryMapper.bal);
			stat.setString(1, mobileNo);
			ResultSet rs=stat.executeQuery();
			con.commit();
			if(rs!=null){
				rs.next();
				return rs.getDouble("balance");
			}else{
				throw new WalletException("mobile no does not exists");
			}
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new WalletException(e.getMessage());
		}
		
		
	}

	@Override
	public Account deposit(String mobileNo,double depositAmt) throws WalletException {
		Connection con=WalletdB.getConnection();
		PreparedStatement stat;
		PreparedStatement stat1;
		try{
			
			stat=con.prepareStatement(IQueryMapper.acc);
			stat.setString(1, mobileNo);
			ResultSet rs=stat.executeQuery();
			if(rs!=null){
				rs.next();
			Account acc=new Account();
			double balance=rs.getDouble("balance")+depositAmt;
			acc.setName(rs.getString("name"));
			acc.setMobileno(rs.getString("mobileno"));
			acc.setBalance(balance);
			acc.setEmail(rs.getString("email"));
			acc.setDate(Date.valueOf(LocalDate.now()));
		
			
			stat1=con.prepareStatement(IQueryMapper.updateAcc);
			stat1.setDouble(1, acc.getBalance());
			stat1.setDate(2, acc.getDate());
			stat1.setString(3, acc.getMobileno());
			int rs1=stat1.executeUpdate();
			
			
			if(rs1==1){
				
				con.commit();
				
				return acc;
			}else{
				throw new WalletException("balance is not updated");
			}
			
			}
			else{
				throw new WalletException("mobile no does not exists");
			}
			
			
		}catch (SQLException e) {
		
			throw new WalletException(e.getMessage());
		}
		
	}

	@Override
	public Account withdraw(String mobileNo,double withdrawAmt) throws WalletException {
		Connection con=WalletdB.getConnection();
		PreparedStatement stat;
		PreparedStatement stat1;
		try{
			
			stat=con.prepareStatement(IQueryMapper.acc);
			stat.setString(1, mobileNo);
			ResultSet rs=stat.executeQuery();
			if(rs!=null){
				rs.next();
			Account acc=new Account();
			double balance=rs.getDouble("balance")-withdrawAmt;
			acc.setName(rs.getString("name"));
			acc.setMobileno(rs.getString("mobileno"));
			acc.setBalance(balance);
			acc.setEmail(rs.getString("email"));
			acc.setDate(Date.valueOf(LocalDate.now()));
		
			stat1=con.prepareStatement(IQueryMapper.updateAcc);
			stat1.setDouble(1, acc.getBalance());
			stat1.setDate(2, acc.getDate());
			stat1.setString(3, acc.getMobileno());
			int rs1=stat1.executeUpdate();
			
			
			if(rs1==1){
				
				con.commit();
				
				return acc;
			}else{
				throw new WalletException("mobile no does not exists");
			}
		
			}
			else{
				throw new WalletException("mobile no does not exists");
			}
			
			
		}catch (SQLException e) {
			
			throw new WalletException(e.getMessage());
		}
		
	}

	@Override
	public Account printTransactionDetails(String mobileNo) throws WalletException{
		Connection con=WalletdB.getConnection();
		PreparedStatement stat;
		try{
			stat=con.prepareStatement(IQueryMapper.acc);
			stat.setString(1, mobileNo);
			ResultSet rs=stat.executeQuery();
			con.commit();
			if(rs!=null){
				rs.next();
				Account ac=new Account();
				ac.setName(rs.getString("name"));
				ac.setMobileno(rs.getString("mobileno"));
				ac.setEmail(rs.getString("email"));
				ac.setBalance(rs.getDouble("balance"));
				ac.setDate(rs.getDate("date1"));
				return ac;
			}else{
				throw new WalletException("mobile no does not exists");
			}
			
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new WalletException(e.getMessage());
		}
		
	}

	

}
