<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지 상세 페이지</title>
</head>
<body>
<h2>공지 사항</h2>
	<table border="1">
		<tr>
			<th>제목</th>
			<td>${noticeVo.title}</td>
		</tr>
		<tr>
			<th>공지한 날짜</th>
			<td><fmt:formatDate value="${noticeVo.regdate}" pattern="yyyy.MM.dd" /></td>
		</tr>
		<c:if test="${classify == 3}">
			<tr>
				<th>볼 권한이 있는 아이디</th>
				<td>${noticeVo.authority}</td>
		</c:if>
		<tr>
			<th>내용</th>
			<td>${noticeVo.content}<br>
			<c:if test="${noticeVo.reviewNum != ''}">
				<a href="<c:url value="#" />">해당 리뷰 보기</a>
			</c:if>
			</td>
		</tr>
	</table>
	<c:if test="${classify == 3}">
		<input type="button" value="공지 수정" onclick="location.href='/project/mypage/editNotice/${noticeVo.noticeNum}'"/>
		<input type="button" value="공지 삭제" onclick="openDelete()"/>
		<input type="hidden" id="noticeNum" value="${noticeVo.noticeNum}">
	</c:if>
	<script>
		function openDelete(){
			var popWidth = 300;
			var popHeight = 200;
			var winHeight = document.body.clientHeight;
			var winWidth = document.body.clientWidth;
			var winX = window.screenLeft;
			var winY = window.screenTop;
			var popX = winX + (winWidth - popWidth)/2;
			var popY = winY + (winHeight - popHeight)/2;
			url="../deleteNotice";
			var openWin = window.open(url, "get", "left="+popX+",top="+popY+",width="+popWidth+",height="+popHeight);
			openWin.document.getElementById("deleteNum").value = document.getElementById("noticeNum").value;
		}
	</script>
</body>
</html>