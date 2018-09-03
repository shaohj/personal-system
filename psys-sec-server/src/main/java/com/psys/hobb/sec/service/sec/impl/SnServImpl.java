package com.psys.hobb.sec.service.sec.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.psys.hobb.sec.dao.sec.SnRepo;
import com.psys.hobb.sec.model.sec.Sn;
import com.psys.hobb.sec.service.sec.SnServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class SnServImpl implements SnServ {
	
	private @Autowired
	SnRepo snRepo;

	@Override
	@Transactional
	public String generateCode(String type, boolean isFulshSn) {
		Assert.hasLength(type, "type参数不能为空！");
		Sn sn = snRepo.findByType(type);
		Assert.notNull(sn, "type参数非法,未查询到type对应的序号配置数据");
		
		String expression = sn.getExpression();
		//处理时间字符串
		String regex = "(?<=T\\{)[^(\\{|\\})]+(?=\\})";
		Pattern patt = Pattern.compile(regex);
		Matcher mat = patt.matcher(expression);
		
		while (mat.find()) {
			String timeFormat = mat.group();
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(timeFormat);
			LocalDateTime now = LocalDateTime.now();
			String value = dateFormat.format(now);
			expression = expression.replaceFirst("T\\{" + timeFormat + "\\}", value);
		}

		//处理流水号
		regex = "(?<=SN\\{)[^(\\{|\\})]+(?=\\})";
		patt = Pattern.compile(regex);
		mat = patt.matcher(expression);
		while (mat.find()) {
			String snCountStr = mat.group();
			String value = generateSn(snCountStr, type, isFulshSn);
			expression = expression.replaceFirst("SN\\{" + snCountStr + "\\}", value);
		}
		
		// 去除所有的单引号
		expression = expression.replaceAll("'", "");
		return expression;
	}

	/**
	 * 获取序列号后的数字
	 * @Title: generateSn 
	 * @param snFormat 数字格式化.eq:%04d,1格式化后为0001
	 * @param type 序列号type
	 * @param isFulshSn 序号是否增长.true:是;false:否
	 * @return eq.001
	 */
	private String generateSn(String snFormat, String type, boolean isFulshSn) {
		Assert.hasLength(type, "type参数不能为空！");
		Sn sn = snRepo.findByType(type);
		Assert.notNull(sn, "type参数非法,未查询到type对应的序号配置数据");
		
		//根据日期查询当前序号值,每天重置序号重1开始计数
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		String lastTime = dateFormat.format(sn.getLastUpdateTime());
		String nowTime = dateFormat.format(LocalDateTime.now());
		
		int newSn = !lastTime.trim().equals(nowTime) ? 1 : sn.getSn() + 1; //判断是否隔天后,若隔天则序号重置
		
		if(isFulshSn){ //若刷新序号,则写入刷新值
			snRepo.updateSn(sn.getId(), newSn, LocalDateTime.now());
		}
		
		return String.format(snFormat, newSn);
	}

}
