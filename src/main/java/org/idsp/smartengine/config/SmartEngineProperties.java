package org.idsp.smartengine.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

@Getter
@Setter
@ConfigurationProperties(prefix = "configuration.properties")
public class SmartEngineProperties implements Serializable {

	private String QName = "http://www.omg.org/spec/BPMN/20100524/MODEL";

	/**
	 * 配置全局唯一标识
	 */
	private String id;

	/**
	 * 通知类型标识
	 *
	 */
	private String type;
}

//
//	@Override
//	public void setUp() {
//		super.setUp();
//
//		BasicServiceTaskDelegation.resetCounter();
//		ExclusiveTaskDelegation.resetCounter();
//	}
//
//	@Test
//	public void test() throws Exception {
//
//		ExecutionInstance executionInstance = commonCodeSnippet("basic-process.bpmn.xml");
//
//		Map<String, Object> request = new HashMap<String, Object>();
//		request.put("route", "a");
//
//		commonCode(request, executionInstance);
//
//	}
