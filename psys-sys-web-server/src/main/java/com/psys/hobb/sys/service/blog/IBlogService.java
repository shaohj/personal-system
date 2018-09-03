package com.psys.hobb.sys.service.blog;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 博客service
 * @author shaohj
 *
 */
public interface IBlogService {

	/**
	 * 给文章投票
	 * @param userKey redis存储用户的key,格式:user:1
	 * @param artHKey redis存储文章的key,格式:art:1
	 */
	public boolean articleVote(String userKey, String artHKey);

	/**
	 * 发布文章内容
	 * @param userKey redis存储用户的key,格式:user:1
	 * @param title 文章标题
	 * @param content 文章内容
	 * @param link 文章link
	 */
	public void postArticle(String userKey, String title, String content, String link);
	
	/**
	 * 获取文章
	 * @param artHKey redis存储文章的key,格式:art:1
	 * @return
	 */
	public Map<Object, Object> getArticle(String artHKey);
	
	/**
	 * 分页查询文章
	 * @param pageable
	 * @param sortField
	 * @return
	 */
	public Page<Map<Object, Object>> getPage(Pageable pageable, String sortField);
	
}
