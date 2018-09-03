package com.psys.hobb.sys.service.blog.impl;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.psys.hobb.sys.service.blog.IBlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import static com.psys.hobb.redis.util.BlogConstants.*;

@Service
public class BlogServiceImpl implements IBlogService {

	private static Logger logger = LoggerFactory.getLogger(BlogServiceImpl.class);
	    
    private @Autowired RedisTemplate<String, String> template;
	
	@Override
	public boolean articleVote(String userKey, String artHKey){
		/** 1.判断文章的发布时间是否允许投票 */
        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime preWeekDay = nowTime.minusDays(7);
        long pubArtTime = preWeekDay.toEpochSecond(ZoneOffset.of("+8")); //一周以内可以投票,发布文章时间允许投票的最小时间
        
        ZSetOperations<String, String> zSetOpt = template.opsForZSet();
        SetOperations<String, String> setOpt = template.opsForSet();
        HashOperations<String, Object, Object> hashOpt = template.opsForHash();
        
        Double artScore = zSetOpt.score(ART_PUB_TIME, artHKey); //获取文章的发表时间,判断是否允许投票
        logger.info("artScore={}", artScore);
        
        if(null == artScore || artScore < pubArtTime){ //未发布的文章和一周以前的文章限制投票
            throw new IllegalArgumentException("不能给一周前发布的文章投票！");
        }
        
        /** 2.文章投票,若投票成功,则添加文章得分和文章投票记录数 */
        String artId = artHKey.split(":")[1]; //文章id
        long addNum = setOpt.add("voted:" + artId, userKey); //投票成功,返回1;若用户已经投票,则将返回0,即投票失败
        if(addNum > 0){ //投票成功
            zSetOpt.incrementScore("artscore", artHKey, VOTE_SCORE);
            hashOpt.increment(artHKey, "votes", 1);
            return true;
        }
        return false; //投票失败,目前只有文章已投票是这种情形
    }
	
	@Override
	public void postArticle(String userKey, String title, String content, String link) {
		@SuppressWarnings("unchecked")
		RedisSerializer<String> rs = (RedisSerializer<String>) template.getKeySerializer();
		
		/** 生成一个新的文章id,管道中获取不到生成的id(管道特性,最后一次执行),因此先生成文章id */
		final long artId = template.opsForValue().increment("seq:art:id", 1); 
		
		template.executePipelined(new RedisCallback<List<Object>>() {
			
			@Override
			public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
				connection.multi(); //开始事务
				
				String artHKey = ART_PRE + artId;
				byte[] bArtHKey = rs.serialize(artHKey);
				
				/** 将发布文章的用户添加到文章的已投票用户名单里面,然后将这个文章的投票时间设置为一周 */
				String votedKey = "voted:" + artId;
				byte[] bVotedKey = rs.serialize(votedKey);
				byte[] bUserKey = rs.serialize(userKey);
				
				connection.sAdd(bVotedKey, bUserKey); //投票成功,新文章,一定会返回1;
				connection.expire(bVotedKey, ONE_WEEK_IN_SECONDS);
				
				/** 保存文章 */
				long nowTimeSecond = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
				
				Map<byte[], byte[]> artMap = new HashMap<>();
				artMap.put(rs.serialize("aid"), rs.serialize(String.valueOf(artId)));
				artMap.put(rs.serialize("title"), rs.serialize(String.valueOf(title)));
				artMap.put(rs.serialize("content"), rs.serialize(String.valueOf(content)));
				artMap.put(rs.serialize("link"), rs.serialize(String.valueOf(link)));
				artMap.put(rs.serialize("poster"), bUserKey);
				artMap.put(rs.serialize("votes"), rs.serialize(String.valueOf(String.valueOf(1))));
				artMap.put(rs.serialize("ctime"), rs.serialize(String.valueOf(String.valueOf(nowTimeSecond))));
				
				connection.hMSet(bArtHKey, artMap);
				
				/** 将文章添加到根据发布时间排序的有序集合里面 */
				connection.zAdd(rs.serialize(ART_PUB_TIME), nowTimeSecond, bArtHKey);
				connection.zAdd(rs.serialize(ART_SCORE), VOTE_SCORE, bArtHKey);

				return connection.exec(); //提交事务 
			}
		});
	}

	@Override
	public Map<Object, Object> getArticle(String artHKey) {
		HashOperations<String, Object, Object> hashOpt = template.opsForHash();
		
		Map<Object, Object> resObj = hashOpt.entries(artHKey);
		
		return resObj;
	}

	@Override
	public Page<Map<Object, Object>> getPage(Pageable pageable, String sortField) {
		int num = pageable.getPageNumber() + 1;
		int size = pageable.getPageSize();
		
		int start = (num - 1) * size;
		int end = start + size - 1;
		
		final ZSetOperations<String, String> zSetOpt = template.opsForZSet();
		final HashOperations<String, Object, Object> hashOpt = template.opsForHash();
		Set<String> artHKeys = zSetOpt.range(sortField, start, end);
		long total = zSetOpt.zCard(sortField);
		
		final List<Map<Object, Object>> articles = new ArrayList<>();
		
		if(!CollectionUtils.isEmpty(artHKeys)){
			artHKeys.stream().forEach(artHKey -> {
				Map<Object, Object> art = hashOpt.entries(artHKey);
				
				String ctimeObj = ObjectUtils.getDisplayString(art.get("ctime"));
				Long ctime = Long.parseLong(ctimeObj);
				LocalDateTime date = LocalDateTime.ofEpochSecond(ctime, 0, ZoneOffset.ofHours(8));
				art.put("ctimeShow", date.toString().replace("T", " "));
				articles.add(art);
			});
		}
		
		return new PageImpl<>(articles, pageable, total);
	}

}
