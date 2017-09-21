package com.zfm.start.two;

import org.apache.ibatis.annotations.Select;

import entity.Blog;

public interface BlogMapper {
	
	@Select("select * from blog where id = #{id}")
	public Blog selectBlog(Integer id);
}
