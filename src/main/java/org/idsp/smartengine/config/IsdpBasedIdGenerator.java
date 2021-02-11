package org.idsp.smartengine.config;

import com.alibaba.smart.framework.engine.configuration.IdGenerator;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * IdGenerator:建议结合 分布式id 生成器来实现这个接口。
 */
public class IsdpBasedIdGenerator implements IdGenerator {

	private static AtomicLong temp = new AtomicLong(0);

	@Override
	public String getId() {
		//去掉“-”符号
		return String.valueOf(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
	}
}