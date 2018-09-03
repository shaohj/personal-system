package com.psys.hobb.sec.dao.sec;

import com.psys.hobb.sec.common.BaseApplicationTest;
import com.psys.hobb.sec.model.sec.SecUser;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class TbCodeRepoTest extends BaseApplicationTest {

	private static final Logger logger = LoggerFactory.getLogger(TbCodeRepoTest.class);
	
	private @Autowired TbCodeRepo tbCodeRepo;

	@Test
	public void delByIdTest(){
		int num = tbCodeRepo.delById(4);

		Assert.assertNotEquals(0, num);
		logger.info("num={}", num);
	}

}
