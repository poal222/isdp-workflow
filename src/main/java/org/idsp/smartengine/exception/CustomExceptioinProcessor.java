package org.idsp.smartengine.exception;

import com.alibaba.smart.framework.engine.configuration.ExceptionProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomExceptioinProcessor implements ExceptionProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomExceptioinProcessor.class);

	@Override
	public void process(Exception exception, Object context) {
		LOGGER.error(exception.getMessage(), exception);

	}
}