package com.rabbitframework.dbase;

import com.rabbitframework.dbase.builder.Configuration;
import com.rabbitframework.dbase.dataaccess.SqlDataAccess;

public interface RabbitDbaseFactory {

	public SqlDataAccess openSqlDataAccess();

	public Configuration getConfiguration();
}