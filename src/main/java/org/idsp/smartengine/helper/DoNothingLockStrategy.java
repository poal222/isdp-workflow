package org.idsp.smartengine.helper;


import com.alibaba.smart.framework.engine.configuration.LockStrategy;
import com.alibaba.smart.framework.engine.exception.LockException;
import org.springframework.stereotype.Service;

/**
 * Created by 高海军 帝奇 74394 on 2017 January  18:03.
 */
@Service
public class DoNothingLockStrategy implements LockStrategy {


	@Override
	public void tryLock(String processInstanceId) throws LockException {
		//ExtensionPointRegistry extensionPointRegistry = smartEngine.getProcessEngineConfiguration()
		//    .getExtensionPointRegistry();
		//PersisterFactoryExtensionPoint persisterFactoryExtensionPoint = extensionPointRegistry.getExtensionPoint(PersisterFactoryExtensionPoint.class);
		//
		//ProcessInstanceStorage processInstanceStorage = persisterFactoryExtensionPoint.getExtensionPoint(ProcessInstanceStorage.class);
		//
		//processInstanceStorage.update()

		//ProcessInstanceDAO processInstanceDAO= (ProcessInstanceDAO)processEngineConfiguration.getInstanceAccessor().access("processInstanceDAO");
		//
		//processInstanceDAO.tryLock(Long.valueOf(processInstanceId));

		//可以是设置 db uniqueKey 唯一索引； 或者在插入后直接再锁上； 或者使用其他中间件。
	}

	@Override
	public void unLock(String processInstanceId) throws LockException {
		//do nothing
	}
}