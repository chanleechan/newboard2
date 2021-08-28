<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
<head>
	<link rel ="stylesheet" href = "<c:url value="/resources/css/table.css"/>">
	<title>게시판 목록</title>
</head>


<script type="text/javascript">
document.addEventListener("DOMContentLoaded".function(){
	document.getElementById	
});
</script>

<body>
<div class = "tableArea">
	<table>
		<tr>
			<th>글번호</th>
			<th>제목</th>
			<th>내용</th>
		</tr>
		<c:forEach var="boardList"  items="${boardList }">
		<tr>
			<td><p>${boardList.boardNum}</p> 
			<input type="hidden" name="boardNum" value = "${boardList.boardNum} ">
			</td>
			<c:choose>
				<c:when test = "${boardList.groupLevel >0 }">
					<td class = "title" style="padding-left: ${boardList.boardSeq * 20}px"><a href = "/board/selectBoard?boardNum=${boardList.boardNum}">ㄴRE : ${boardList.title}</a>
					<p class = "time">${boardList.boardSeq}</p>
					</td>
				</c:when>
				<c:otherwise>
					<td class = "title"><a href = "/board/selectBoard?boardNum=${boardList.boardNum}">${boardList.title}</a>
					<p class = "time">${boardList.boardSeq}</p>
					</td>
				</c:otherwise>
			</c:choose>
			<td>${boardList.contents }</td>
		</tr>
		</c:forEach>
	</table>
</div>
<div class= "pagebar">
	<a href = "/board/boardList?pageNum=1">처음으로</a>
	<c:forEach var="totalNumList" items="${totalNumList }">  
		<a href = "/board/boardList?pageNum=${totalNumList }" >${totalNumList }</a>
	</c:forEach>
	<a href = #>마지막으로</a>
	<form action ="/board/searchBoard" method = "get">
	<p><input type ="text" name="contents" placeholder="검색" > <input type="submit" value = "검색" ></p>
	</form>
</div>
<div class = "newBoard">
<a href ="/board/insertBoardForm">새 글 쓰기</a>
</div>
</body>
</html>
