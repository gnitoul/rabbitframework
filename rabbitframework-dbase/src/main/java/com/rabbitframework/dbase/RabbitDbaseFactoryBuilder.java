package com.rabbitframework.dbase;

import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

import com.rabbitframework.dbase.builder.Configuration;
import com.rabbitframework.dbase.builder.XMLConfigBuilder;
import com.rabbitframework.dbase.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link RabbitDbaseFactory}
 */
public class RabbitDbaseFactoryBuilder {
    private static final Logger logger = LoggerFactory.getLogger(RabbitDbaseFactoryBuilder.class);

    public RabbitDbaseFactory build(Reader reader) {
        return build(reader, null);
    }

    public RabbitDbaseFactory build(Reader reader, Properties properties) {
        try {
            XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder(reader,
                    properties);
            return build(xmlConfigBuilder.parse());
        } catch (Exception e) {
            String msg = "Error building SqlSession.";
            logger.error(msg, e);
            throw new PersistenceException(msg, e);
        } finally {
            try {
                reader.close();
            } catch (Exception e) {
            }
        }
    }

    public RabbitDbaseFactory build(InputStream inputStream) {
        return build(inputStream, null);
    }

    public RabbitDbaseFactory build(InputStream inputStream,
                                    Properties properties) {
        try {
            XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder(
                    inputStream, properties);
            return build(xmlConfigBuilder.parse());
        } catch (Exception e) {
            String msg = "Error building SqlSession.";
            logger.error(msg, e);
            throw new PersistenceException(msg, e);
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
            }
        }
    }

    public RabbitDbaseFactory build(Configuration configuration) {
        return new DefaultRabbitDbaseFactory(configuration);
    }
}
