<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
<head>
	<title>상세 내용</title>
</head>
<script type="text/javascript">
document.addEventListener("DOMContentLoaded", function(){
	document.getElementById('replyBoard').style.display = 'none';
});
function onDisplay(){
	document.getElementById('replyBoard').style.display = 'block';
}
function offDisplay(){
	document.getElementById('replyBoard').style.display = 'none';
}
	

</script>
<body>
<div>	
	<form action = "/board/updateBoard" method = "get" name = "boardForm">
		<p>글번호 :  ${selectBoard.boardNum }
		<input type = "hidden" name ="boardNum" value = "${selectBoard.boardNum }">
		</p>
		<p>제목<input type = "text" name ="title" value = "${selectBoard.title }"  readonly></p>
		<p>내용<textarea name = "contents"  readonly>${selectBoard.contents }</textarea></p>
		<input type="submit" value = "수정하기">
	</form>
	<form action = "/board/deleteBoard" method = "get">
		<input type="hidden" name="boardNum" value = "${selectBoard.boardNum }">
		<input type = "hidden"  name = "boardSeq" value = "${selectBoard.boardSeq }" >
		<input type = "hidden" name = "groupNum" value = "${selectBoard.groupNum }">
		<input type = "hidden" name = "groupLevel" value = "${selectBoard.groupLevel }">
		<input type = "submit"  value = "삭제하기">
	</form>
	<input type = "button" value= "답글 달기" onclick = "onDisplay()">

	<div id = "replyBoard">
		<form action ="/board/replyInsertBoard" method = "get">
			<input type ="hidden" name = "boardSeq" value = "${selectBoard.boardSeq }">
			<input type="hidden" name="boardNum" value = "${selectBoard.boardNum }">
			<input type = "hidden" name = "groupNum" value = "${selectBoard.groupNum }">
			<input type = "hidden" name = "groupLevel" value = "${selectBoard.groupLevel }">
			<p>제목<input type ="text" name = "title"></p>
			<p>내용<textarea name ="contents" ></textarea></p>
			<input type ="submit" value = "쓰기">
			<input type = "button" value = "취소하기" onclick = "offDisplay()">
		</form>
	</div>
</div>
<div>

</div>


</body>
</html>
