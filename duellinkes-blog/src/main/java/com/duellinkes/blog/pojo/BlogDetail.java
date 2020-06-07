package com.duellinkes.blog.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="blog_detail")
public class BlogDetail {
    @Id
    private Long blogId;
    private String blogDetail;
    private String blogMarkdown;

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public String getBlogDetail() {
        return blogDetail;
    }

    public void setBlogDetail(String blogDetail) {
        this.blogDetail = blogDetail;
    }

    public String getBlogMarkdown() {
        return blogMarkdown;
    }

    public void setBlogMarkdown(String blogMarkdown) {
        this.blogMarkdown = blogMarkdown;
    }
}
