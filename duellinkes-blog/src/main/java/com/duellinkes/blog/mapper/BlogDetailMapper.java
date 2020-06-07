package com.duellinkes.blog.mapper;

import com.duellinkes.blog.pojo.BlogDetail;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface BlogDetailMapper extends Mapper<BlogDetail> {
}
