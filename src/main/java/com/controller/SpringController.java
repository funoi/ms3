package com.controller;

import com.dao.IFile;
import com.vo.MFile;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
public class SpringController {
    @RequestMapping("upload")
    public String upload(MultipartFile file, HttpServletRequest req) throws IOException {
        /*
         * MultipartFile:来源于SpringMVC这个框架，对应页面中的file输入流
         */
        String rpath = req.getServletContext().getRealPath("/files");
        File dir = new File(rpath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 获取到原始的文件名
        String oriname = file.getOriginalFilename(); // 通过输入流对象，获取文件名,上传的真实文件名

        file.getSize();// 获取文件的长度

        // 获取文件的后缀
        String suffix = oriname.substring(oriname.lastIndexOf("."));

        if (suffix.equalsIgnoreCase(".png")||suffix.equalsIgnoreCase(".jpg")) { // 判断文件的后缀
            String uuid = UUID.randomUUID().toString();// 生成一个新的文件名，避免文件同名出现文件覆盖的现象
            // 1.路径的获取
            String newName = uuid + suffix;

            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(dir + "\\" + newName));

            MFile file1 = new MFile(oriname, dir + "\\" + newName, file.getSize(), new java.sql.Date(new java.util.Date().getTime()));

            InputStream is = Resources.getResourceAsStream("mybatis_config.xml");
            SqlSessionFactory build = new SqlSessionFactoryBuilder().build(is);
            SqlSession session = build.openSession();
            IFile mapper = session.getMapper(IFile.class);
            mapper.insert(file1);
            session.commit();
            return "index.jsp";
        } else {
            return "error.jsp";
        }
    }

    @RequestMapping("showFileList")
    // 创建一个下载文件的列表
    public String showFileList(Model model) throws Exception {
        InputStream is = Resources.getResourceAsStream("mybatis_config.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(is);
        SqlSession session = build.openSession();
        IFile mapper = session.getMapper(IFile.class);
        List<MFile> files = mapper.findAll();
        model.addAttribute("filelist", files);
        return "index.jsp";
    }

    @RequestMapping("download")
    public void download(String fileName, HttpServletResponse res,
                         HttpServletRequest req) throws IOException {

        InputStream is = Resources.getResourceAsStream("mybatis_config.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(is);
        SqlSession session = build.openSession();
        IFile mapper = session.getMapper(IFile.class);
        MFile file = mapper.findByRpath(fileName);
        Path path = Paths.get(file.getRpath());

        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment; filename=\"" + file.getOriname() + "\"");

        java.nio.file.Files.copy(path, res.getOutputStream());
        res.getOutputStream().flush();
    }
}
