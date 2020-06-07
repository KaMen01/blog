package com.duellinkes.blog.web;

import com.duellinkes.blog.commons.bo.PageResult;
import com.duellinkes.blog.pojo.Blog;
import com.duellinkes.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TemplateEngine templateEngine;

    @PostMapping("/addBlog")
    public ResponseEntity<Void> addBlog(
            @RequestParam("blog") String blogValue,
            @RequestParam("blog_markdown") String blogMarkdown
    ){

        blogService.addBlog(blogValue,blogMarkdown);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/blogPage")
    public ResponseEntity<PageResult<Blog>> findBlogByPage(
            @RequestParam(value = "pageSize",defaultValue = "15") Integer pageSize,
            @RequestParam("curPage") Integer curPage,
            @RequestParam(value = "key",required = false) String key
    ){
       PageResult<Blog> pageResult = blogService.findBlogByPage(pageSize,curPage,key);
        return ResponseEntity.ok(pageResult);
    }

    @GetMapping("/blogDetail")
    public ResponseEntity<String> findBlogDetailById(@RequestParam("id")Integer id){
        String blogDetail= blogService.findBlogDetailById(id);

        return ResponseEntity.ok(blogDetail);
    }

    @GetMapping("/blogDetail/{id}.html")
    public String buildDetail(@PathVariable(name = "id")Integer id, Model model){
        String blogDetail= blogService.findBlogDetailById(id);
        Map map = new HashMap();
        map.put("blogContent",blogDetail);
        model.addAllAttributes(map);

        buildHtml(id);
        return "blogDetail";

    }

    private void buildHtml(Integer id){
        Context context = new Context();
        Map map = new HashMap();
        map.put("blogContent",blogService.findBlogDetailById(id));
        context.setVariables(map);

        File file = new File("D:\\Program Files\\nginx-1.18.0\\html\\thymeleaf\\api\\blog\\blogDetail\\"+id+".html");
//        File file = new File("/data/www/blog-project/thymeleafHtml/api/blog/blogDetail/"+id+".html");
        PrintWriter printWriter = null;

        try {
            printWriter =  new PrintWriter(file);
            templateEngine.process("blogDetail",context,printWriter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
