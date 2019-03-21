package com.sandu.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Sandu @ClassName ModelTransBalanceHelper
 * @date 2018/11/6
 */
@Component
@Scope(scopeName = "singleton")
public class ModelTransBalanceHelper {

	public static final String READ_TRANS_ACTION = "read";
	public static final String WRITE_TRANS_ACTION = "write";

	@Value("${trans.model.machine.count}")
	private Integer transModelMachineCount;

	private final ReentrantLock readLock = new ReentrantLock(true);

	private final ReentrantLock writeLock = new ReentrantLock(true);

	private Vector<String> list;

	private AtomicInteger currentReadIndex = new AtomicInteger(0);

	private AtomicInteger currentWriteIndex = new AtomicInteger(0);

	@PostConstruct
	public void setIpTables() {
		list = new Vector<>(transModelMachineCount);
		for (int i = 0; i < transModelMachineCount; i++) {
			list.add(String.valueOf(i));
		}
	}

	public String getMachineIp(String type) {
		if (READ_TRANS_ACTION.equals(type)) {
			return doGet(readLock, currentReadIndex);
		} else if (WRITE_TRANS_ACTION.equals(type)) {
			return doGet(writeLock, currentWriteIndex);
		}
		throw new IllegalArgumentException("参数类型错误");
	}

	private String doGet(ReentrantLock lock, AtomicInteger index) {
		lock.lock();
		String ip = null;
		try {
			if (index.get() == list.size()) {
				index.set(0);
			}
			System.out.println(index);
			ip = list.get(index.getAndIncrement());
		} finally {
			lock.unlock();
		}
		return ip;
	}

	public static void main(String[] args) {
		//

	}

}
