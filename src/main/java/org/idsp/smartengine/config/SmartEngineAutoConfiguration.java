package org.idsp.smartengine.config;

import com.alibaba.smart.framework.engine.SmartEngine;
import com.alibaba.smart.framework.engine.configuration.InstanceAccessor;
import com.alibaba.smart.framework.engine.configuration.ProcessEngineConfiguration;
import com.alibaba.smart.framework.engine.configuration.impl.DefaultProcessEngineConfiguration;
import com.alibaba.smart.framework.engine.configuration.impl.DefaultSmartEngine;
import com.alibaba.smart.framework.engine.exception.EngineException;
import com.alibaba.smart.framework.engine.service.command.RepositoryCommandService;
import com.alibaba.smart.framework.engine.util.IOUtil;
import org.idsp.smartengine.exception.CustomExceptioinProcessor;
import org.idsp.smartengine.helper.CustomVariablePersister;
import org.idsp.smartengine.helper.DefaultMultiInstanceCounter;
import org.idsp.smartengine.helper.DoNothingLockStrategy;
import org.idsp.smartengine.helper.dispatcher.DefaultTaskAssigneeDispatcher;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.InputStream;

import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

@Order(LOWEST_PRECEDENCE)
@Configuration
@ConditionalOnClass(SmartEngine.class)
@EnableConfigurationProperties(SmartEngineProperties.class)
public class SmartEngineAutoConfiguration implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Bean
	@ConditionalOnMissingBean
	public SmartEngine constructSmartEngine(SmartEngineProperties smartEngineProperties) {
		ProcessEngineConfiguration processEngineConfiguration = new DefaultProcessEngineConfiguration();
		processEngineConfiguration.setInstanceAccessor(new CustomInstanceAccessService());

		SmartEngine smartEngine = new DefaultSmartEngine();

		processEngineConfiguration.setExceptionProcessor(new CustomExceptioinProcessor());
		processEngineConfiguration.setTaskAssigneeDispatcher(new DefaultTaskAssigneeDispatcher());
		processEngineConfiguration.setMultiInstanceCounter(new DefaultMultiInstanceCounter());
		processEngineConfiguration.setVariablePersister(new CustomVariablePersister());
		processEngineConfiguration.setLockStrategy(new DoNothingLockStrategy());

		processEngineConfiguration.setIdGenerator(new IsdpBasedIdGenerator());
		smartEngine.init(processEngineConfiguration);

		deployProcessDefinition(smartEngine);
		return smartEngine;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	private class CustomInstanceAccessService implements InstanceAccessor {
		@Override
		public Object access(String name) {
			return applicationContext.getBean(name);
		}

	}


	private void deployProcessDefinition(SmartEngine smartEngine) {
		RepositoryCommandService repositoryCommandService = smartEngine.getRepositoryCommandService();

		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		try {
			Resource[] resources = resolver.getResources("classpath*:/smart-engine/*.xml");
			for (Resource resource : resources) {
				InputStream inputStream = resource.getInputStream();
				repositoryCommandService.deploy(inputStream);
				IOUtil.closeQuietly(inputStream);
			}
		} catch (Exception e) {
			throw new EngineException(e);
		}

	}
}