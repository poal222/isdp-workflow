package org.idsp.smartengine.helper;

import com.alibaba.fastjson.JSON;
import com.alibaba.smart.framework.engine.configuration.VariablePersister;
import com.alibaba.smart.framework.engine.constant.RequestMapSpecialKeyConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * VariablePersister: 仅用于DataBase模式。默认不需要扩展，业务根据需要进行扩展。
 * 该类支持根据选定的序列化方式，自动存储 command 里面的 request 参数，同时支持黑名单机制。
 */
public class CustomVariablePersister implements VariablePersister {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomVariablePersister.class);

	private static Set<String> blockSet = new HashSet();

	static {

		try {
			Field[] declaredFields = RequestMapSpecialKeyConstant.class.getDeclaredFields();
			for (Field declaredField : declaredFields) {
				String key = (String) declaredField.get(declaredField.getName());
				blockSet.add(key);
			}
		} catch (IllegalAccessException e) {
			LOGGER.error(e.getMessage(), e);
		}

		//do something else.
		blockSet.add("text");
	}


	@Override
	public boolean isPersisteVariableInstanceEnabled() {
		return true;
	}


	@Override
	public Set<String> getBlockList() {


		return blockSet;
	}

	@Override
	public String serialize(Object value) {
		return JSON.toJSONString(value);
	}

	@Override
	public <T> T deserialize(String text, Class<T> clazz) {
		return JSON.parseObject(text, clazz);
	}
}
