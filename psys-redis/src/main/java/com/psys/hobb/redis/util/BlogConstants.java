package com.psys.hobb.redis.util;

public class BlogConstants {

	public static final long ONE_WEEK_IN_SECONDS = 604800; //7 * 24 * 3600
	
	public static final long VOTE_SCORE = 432;
	
	public static final String USER_PRE = "user:";
	
	public static final String ART_PRE = "art:";
	
	/** 文章发布时间排序的有序集合 */
	public static final String ART_PUB_TIME = "arttime";
	
	/** 文章评分排序的有序集合 */
	public static final String ART_SCORE = "artscore";
	
	
}
