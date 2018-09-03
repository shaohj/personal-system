package com.psys.hobb.sec.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.psys.hobb.common.sso.bean.SsoUser;
import com.psys.hobb.sec.common.BaseApplicationTest;
import com.psys.hobb.sec.model.sec.SecResource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

public class SsoSecResourceRedisTemplateTest extends BaseApplicationTest {

    private static Logger logger = LoggerFactory.getLogger(SsoSecResourceRedisTemplateTest.class);

    @Autowired
    private RedisTemplate<String, SecResource> secResourceTemplate;

    @Test
    public void testResourceRedisTemplate() throws Exception {
        // 保存对象
        SecResource secResource = new SecResource();
        secResource.setResourceId(1);
        secResource.setName("测试资源");

        List<SecResource> child = new ArrayList<>();
        SecResource s1 = new SecResource();
        s1.setResourceId(11);
        s1.setName("测试资源01");
        child.add(s1);

        secResource.setChildren(child);

        secResourceTemplate.opsForValue().set("test:res:"+secResource.getResourceId(), secResource);

        SecResource result = secResourceTemplate.opsForValue().get("test:res:"+secResource.getResourceId());

        logger.info("result={}", JSON.toJSONString(result));
    }

    @Test
    public void testResourceRedisTemplate1() throws Exception {
        // 保存对象
        String str = "{\"children\":[{\"children\":[{\"name\":\"用户管理\",\"newWindow\":\"N\",\"parentId\":\"M-20170101-001\",\"realNode\":{\"$ref\":\"@\"},\"remark\":\"用户管理\",\"resourceCode\":\"M-20170101-002\",\"resourceId\":2,\"sort\":1,\"treeId\":\"M-20170101-002\",\"treeParentId\":\"M-20170101-001\",\"url\":\"sys/user/to/list\"},{\"name\":\"角色管理\",\"newWindow\":\"N\",\"parentId\":\"M-20170101-001\",\"realNode\":{\"$ref\":\"@\"},\"remark\":\"角色管理\",\"resourceCode\":\"M-20170101-003\",\"resourceId\":3,\"sort\":2,\"target\":\"_blank\",\"treeId\":\"M-20170101-003\",\"treeParentId\":\"M-20170101-001\",\"url\":\"sys/role/to/list\"},{\"name\":\"菜单管理\",\"newWindow\":\"N\",\"parentId\":\"M-20170101-001\",\"realNode\":{\"$ref\":\"@\"},\"remark\":\"菜单管理\",\"resourceCode\":\"M-20170101-004\",\"resourceId\":4,\"sort\":5,\"target\":\"_blank\",\"treeId\":\"M-20170101-004\",\"treeParentId\":\"M-20170101-001\",\"url\":\"sys/resource/to/list\"},{\"name\":\"组织管理\",\"newWindow\":\"N\",\"parentId\":\"M-20170101-001\",\"realNode\":{\"$ref\":\"@\"},\"remark\":\"组织管理\",\"resourceCode\":\"M-20170101-005\",\"resourceId\":5,\"sort\":3,\"target\":\"_blank\",\"treeId\":\"M-20170101-005\",\"treeParentId\":\"M-20170101-001\",\"url\":\"sys/group/to/list\"},{\"name\":\"数据字典\",\"newWindow\":\"N\",\"parentId\":\"M-20170101-001\",\"realNode\":{\"$ref\":\"@\"},\"remark\":\"数据字典\",\"resourceCode\":\"M-20170101-006\",\"resourceId\":6,\"sort\":4,\"target\":\"_blank\",\"treeId\":\"M-20170101-006\",\"treeParentId\":\"M-20170101-001\",\"url\":\"sys/code/to/list\"}],\"iTreeNodeChildren\":[{\"$ref\":\"$.children[0].children[0]\"},{\"$ref\":\"$.children[0].children[1]\"},{\"$ref\":\"$.children[0].children[2]\"},{\"$ref\":\"$.children[0].children[3]\"},{\"$ref\":\"$.children[0].children[4]\"}],\"icon\":\"sys_manage\",\"name\":\"系统管理\",\"newWindow\":\"N\",\"parentId\":\"-1\",\"realNode\":{\"$ref\":\"@\"},\"remark\":\"系统管理\",\"resourceCode\":\"M-20170101-001\",\"resourceId\":1,\"sort\":1,\"target\":\"_blank\",\"treeId\":\"M-20170101-001\",\"treeParentId\":\"-1\"},{\"children\":[{\"name\":\"博客中心\",\"newWindow\":\"N\",\"parentId\":\"M-20180331-001\",\"realNode\":{\"$ref\":\"@\"},\"remark\":\"博客中心\",\"resourceCode\":\"M-20180331-002\",\"resourceId\":14,\"sort\":1,\"treeId\":\"M-20180331-002\",\"treeParentId\":\"M-20180331-001\",\"url\":\"blog/center/to/list\"},{\"name\":\"博客群组\",\"newWindow\":\"N\",\"parentId\":\"M-20180331-001\",\"realNode\":{\"$ref\":\"@\"},\"remark\":\"博客群组\",\"resourceCode\":\"M-20180331-003\",\"resourceId\":15,\"sort\":2,\"treeId\":\"M-20180331-003\",\"treeParentId\":\"M-20180331-001\",\"url\":\"blog/group/to/list\"}],\"iTreeNodeChildren\":[{\"$ref\":\"$.children[1].children[0]\"},{\"$ref\":\"$.children[1].children[1]\"}],\"icon\":\"sys_manage\",\"name\":\"博客demo\",\"newWindow\":\"N\",\"parentId\":\"-1\",\"realNode\":{\"$ref\":\"@\"},\"remark\":\"博客demo\",\"resourceCode\":\"M-20180331-001\",\"resourceId\":13,\"sort\":2,\"treeId\":\"M-20180331-001\",\"treeParentId\":\"-1\"}],\"iTreeNodeChildren\":[{\"$ref\":\"$.children[0]\"},{\"$ref\":\"$.children[1]\"}],\"name\":\"模拟根节点\",\"parentId\":\"-\",\"realNode\":{\"$ref\":\"@\"},\"resourceCode\":\"-1\",\"sort\":0,\"treeId\":\"-1\",\"treeParentId\":\"-\"}";
        JSONObject json = JSONObject.parseObject(str);
        SecResource secResource = json.toJavaObject(SecResource.class);
        secResourceTemplate.opsForValue().set("test:res:"+secResource.getResourceId(), secResource);

        SecResource result = secResourceTemplate.opsForValue().get("test:res:"+secResource.getResourceId());

        logger.info("result={}", JSON.toJSONString(result));
    }

    @Test
    public void testResourceRedisTemplate2() throws Exception {
        SecResource result = secResourceTemplate.opsForValue().get("SEC:USER:MENU:1");

        logger.info("result={}", JSON.toJSONString(result));
    }

}
