package com.psys.hobb.sec.dao.sec;

import com.psys.hobb.sec.common.BaseApplicationTest;
import com.psys.hobb.sec.model.sec.SecGroup;
import com.psys.hobb.sec.model.sec.SecUser;
import com.psys.hobb.sec.service.sec.SecGroupServ;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class SecUserRepoTest extends BaseApplicationTest {

	private static final Logger logger = LoggerFactory.getLogger(SecUserRepoTest.class);
	
	private @Autowired SecUserRepo secUserRepo;

	@Test
	public void findByUserNameAndPasswordTest(){
		String name = "admin";
		String password = "21232f297a57a5a743894a0e4a801fc3";
		SecUser user = secUserRepo.findByUserNameAndPassword(name, password);

		Assert.assertNotNull(user);
		
		logger.info("user={}", user.getRealName());
	}

}
