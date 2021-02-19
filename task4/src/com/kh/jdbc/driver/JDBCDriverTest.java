package com.kh.jdbc.driver;

public class JDBCDriverTest {

	public static void main(String[] args) throws ClassNotFoundException{
	Class.forName("oracle.jdbc.OracleDriver");	
	System.out.println("system: Driver Class successfully added.");
	}
}
