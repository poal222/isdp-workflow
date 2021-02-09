package org.idsp.smartengine.config;

import com.alibaba.smart.framework.engine.configuration.IdGenerator;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class IsdpBasedIdGenerator implements IdGenerator {

	private static AtomicLong temp = new AtomicLong(0);

	@Override
	public String getId() {
		//去掉“-”符号
		return String.valueOf(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
	}
}