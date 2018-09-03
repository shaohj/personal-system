package com.psys.hobb.common.sys.util;

import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * Assert拓展,校验参数,抛出IllegalArgumentException异常
 * 编  号：<br/>
 * 名  称：AssertUtil<br/>
 * 描  述：<br/>
 * 完成日期：2017年12月3日 下午2:56:18<br/>
 * 编码作者：shaohj<br/>
 */
public class AssertUtil extends Assert{
	
	public static void notEmptyStr(Object str, String message) {
		if (StringUtils.isEmpty(str)) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void notEmpty(Collection<?> collection, String message) {
		if (CollectionUtils.isEmpty(collection)) {
			throw new IllegalArgumentException(message);
		}
	}

}
