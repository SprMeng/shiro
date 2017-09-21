package com.zfm.start.two;

import java.io.IOException;
import java.sql.DriverManager;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.DatabaseIdProvider;

public class MyDataSourceFactory {
	
	private static DataSourceFactory factory = new PooledDataSourceFactory();
	
	
	public static DataSource getDataSource() throws IOException {
		Properties properties = Resources.getResourceAsProperties("db.properties");
		factory.setProperties(properties);
		return factory.getDataSource();
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println(MyDataSourceFactory.getDataSource());
	}

}
