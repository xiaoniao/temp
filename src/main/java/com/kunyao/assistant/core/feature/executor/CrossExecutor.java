package com.kunyao.assistant.core.feature.executor;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/*
 * 异步任务
 */
@Component
public class CrossExecutor {
	private static final Logger log = LoggerFactory.getLogger(CrossExecutor.class);

	/**
	 * 重启 tomcat
	 */
	@Async
	public void reloadTomcat(String cmd) {
		Runtime run = Runtime.getRuntime();
		try {
			Process p = run.exec(cmd);
			BufferedInputStream in = new BufferedInputStream(p.getInputStream());
			BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
			String lineStr;
			while ((lineStr = inBr.readLine()) != null)
				log.info(lineStr);// 打印输出信息

			inBr.close();
			in.close();

			if (p.waitFor() != 0) {
				if (p.exitValue() == 1) // 0表示正常结束，1：非正常结束
					log.error("命令执行失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
