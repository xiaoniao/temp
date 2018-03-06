package com.kunyao.assistant.web.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.feature.executor.CrossExecutor;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.OrderConfig;
import com.kunyao.assistant.core.utils.FileUtils;
import com.kunyao.assistant.web.dao.OrderConfigMapper;
import com.kunyao.assistant.web.service.OrderConfigService;

@Service
public class OrderConfigServiceImpl extends GenericServiceImpl<OrderConfig, Integer> implements OrderConfigService {
    @Resource
    private OrderConfigMapper orderConfigMapper;
    
    @Resource
	private CrossExecutor crossExecutor;

    public GenericDao<OrderConfig, Integer> getDao() {
        return orderConfigMapper;
    }

	@Override
	public OrderConfig selectOrderConfig() {
		return orderConfigMapper.findOrderServiceFee();
	}
	
	/**
	 * 修改轮训时间 更新xml文件，重启服务器
	 * @param loopTime
	 * @return
	 */
	@Override
	public boolean updateLooptime(Integer loopTime) {
		// 读取文件并替换时间
		String xml = FileUtils.getClassPath() + "/WEB-INF/classes/spring.quartz.xml";
		StringBuffer stringBuffer = new StringBuffer();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(new File(xml)));
			String string = null;
			while ((string = reader.readLine()) != null) {
				if (string.indexOf("<bean id=\"crossTrigger1\" class=\"org.springframework.scheduling.quartz.CronTriggerFactoryBean\">") != -1) {
					String line1 = reader.readLine();
					reader.readLine();
					string = string + "\r\n" + line1 + "\r\n" + "		<property name=\"cronExpression\" value=\"0 0/" + loopTime + " * * * ?\" />";
				}
				stringBuffer.append(string + "\r\n");
			}
			reader.close();
			
			FileWriter fw = new FileWriter(new File(xml));
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(stringBuffer.toString());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {

				}
			}
		}
		crossExecutor.reloadTomcat("sh /data/default/ROOT/reload.sh");
		return true;
	}
}
