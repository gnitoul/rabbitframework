package com.rabbitframework.dbase;

import com.rabbitframework.dbase.builder.Configuration;
import com.rabbitframework.dbase.dataaccess.DefaultSqlDataAccess;
import com.rabbitframework.dbase.dataaccess.SqlDataAccess;

public class DefaultRabbitDbaseFactory implements RabbitDbaseFactory {
	private Configuration configuration;

	public DefaultRabbitDbaseFactory(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public SqlDataAccess openSqlDataAccess() {
		return new DefaultSqlDataAccess(configuration);
	}

	@Override
	public Configuration getConfiguration() {
		return configuration;
	}

}
