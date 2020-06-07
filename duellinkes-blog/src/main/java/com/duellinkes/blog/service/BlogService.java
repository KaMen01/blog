package com.duellinkes.blog.service;

import com.duellinkes.blog.mapper.BlogDetailMapper;
import com.duellinkes.blog.mapper.BlogMapper;
import com.duellinkes.blog.pojo.Blog;
import com.duellinkes.blog.pojo.BlogDetail;
import com.duellinkes.blog.commons.bo.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class BlogService {
    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private BlogDetailMapper blogDetailMapper;

    public  String findBlogDetailById(Integer id) {
        BlogDetail blogDetail = blogDetailMapper.selectByPrimaryKey(id);
        return blogDetail.getBlogDetail();
    }

    /**
     * 添加博客
     * @param blogValue
     * @param blogMarkdown
     */
    @Transactional
    public void addBlog(String blogValue, String blogMarkdown){
        //正则表达式来获得带\n的   博客文本内容
        StringBuffer blogStringValue = new StringBuffer(blogValue.replaceAll("<[.[^>]]*>","").replaceAll(" ", ""));

        //截取第一个换行前的字符串
        String blogTitle = blogStringValue.substring(0, blogStringValue.indexOf("\n"));
        //先获得全部的content，然后筛选出simpleContent
        String content = blogStringValue.substring(blogStringValue.indexOf("\n") + 1, blogStringValue.length());

        if(content.length()>100){
            content = content.substring(0,100);
        }
        if(content.endsWith("\n")){
            content = content.substring(0,content.length()-1);
        }

        Date createTime = new Date();
        Date updateTime = createTime;

        Blog blog = new Blog();
        blog.setBlogTitle(blogTitle);
        blog.setCreateTime(createTime);
        blog.setUpdateTime(updateTime);
        blog.setSimpleContent(content);
        blogMapper.insertSelective(blog);

        BlogDetail blogDetail = new BlogDetail();
        blogDetail.setBlogId(blog.getId());
        blogDetail.setBlogDetail(blogValue);
        blogDetail.setBlogMarkdown(blogMarkdown);
        blogDetailMapper.insertSelective(blogDetail);
    }

    public PageResult<Blog> findBlogByPage(Integer pageSize, Integer curPage, String key) {
        if(key!=null){
            //todo 要做查询的筛选
            System.out.println(key);
        }
        PageHelper.startPage(curPage,pageSize);
        List<Blog> blogs = blogMapper.selectAll();
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);


        return new PageResult<>(pageInfo.getTotal(),pageInfo.getPages(),pageInfo.getList());
    }
}
