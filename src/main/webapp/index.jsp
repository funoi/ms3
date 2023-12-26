<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<!--一定是使用表单进行文件的上传，指定表单的enctype属性：采用二进制的方式进行内容的上传 -->
<form action="upload" enctype="multipart/form-data" method="post">
	上传人姓名:<input type="text" name="name"/><br/><!-- 基本的字符信息和2进制的信息可以同时进行上传 -->
	文件:<input type="file" name="file"/><br/>
	<input type="submit" value="提交"/>
</form>
<a href="showFileList"> 刷新</a>
<br><br>
<c:forEach items="${filelist}" var="f">
    <a href="download?fileName=${f.rpath}">${f.oriname}下载</a>
    <br>
</c:forEach>
<br><br><br><br>
</body>
</html>