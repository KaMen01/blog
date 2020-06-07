package com.duellinkes.blog.mapper;

import com.duellinkes.blog.pojo.Blog;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface BlogMapper extends Mapper<Blog> {
}
