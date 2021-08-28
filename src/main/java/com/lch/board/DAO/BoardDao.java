package com.lch.board.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.lch.board.JDBCInfo.JDBCInfo;
import com.lch.board.domain.BoardDomain;
import com.lch.board.domain.PageDomain;
public class BoardDao {
	Connection conn;
	Statement stmt;
	ResultSet rs;
	PreparedStatement pstmt;
	
	public ArrayList<BoardDomain> boardList(HttpServletRequest req) throws SQLException{
		ArrayList<BoardDomain> boardList = null;
			boardList = new ArrayList<BoardDomain>();
		PageDomain pd = new PageDomain();
		
		int page;
		String stringPage = "1";
		//최초 들어오는 getParameter는 null 이후 a태그로 인하여 getParameter전달됨
		if(req.getParameter("pageNum") == null || "null".equals(req.getParameter("pageNum"))) {
			stringPage = "1";
		}else {
			stringPage = req.getParameter("pageNum");
		}
		
		page = Integer.parseInt(stringPage);
		int pageNum = 10;
//		int pageNum = 5;
		if(page <=0) {
			page = 0;
		}
		page = (page-1) * 10;
//		page = (page-1) * 5;
		pd.setPage(page);
		pd.setPageNum(pageNum);

		page = pd.getPage();
		pageNum = pd.getPageNum();

		conn = JDBCInfo.getConnection();
		String sql = "select * from board order by groupNum desc , groupLevel asc limit ?,?; ";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, page);
			pstmt.setInt(2, pageNum);
			rs = pstmt.executeQuery();


			while(rs.next()) {
				BoardDomain bd = new BoardDomain();
				bd.setBoardNum(rs.getInt("boardNum"));
				bd.setBoardSeq(rs.getInt("boardSeq"));
				bd.setTitle(rs.getString("title"));
				bd.setContents(rs.getString("contents"));
				bd.setGroupLevel(rs.getInt("groupLevel"));
				boardList.add(bd);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		rs.close();
		pstmt.close();
		conn.close();

		return boardList;
	}
	public ArrayList<Integer> totalNum(HttpServletRequest req) throws SQLException{
		ArrayList<Integer> pageNumList = new ArrayList<Integer>();
		ArrayList<PageDomain> totalNumList = new ArrayList<PageDomain>();
		int curNum = 0;
		if(req.getParameter("pageNum") == null || "null".equals(req.getParameter("pageNum"))) {
			curNum = 1;
		}else {
			curNum = Integer.parseInt(req.getParameter("pageNum"));
		}
		
		conn = JDBCInfo.getConnection();
		String totalNumSQL = "select count(boardNum) as totalNum from board";
		
		pstmt = conn.prepareStatement(totalNumSQL);
		rs = pstmt.executeQuery();
		
		
		
		if(rs.next()) {
			PageDomain pd = new PageDomain();
			pd.setTotalNum(rs.getInt("totalNum"));
			totalNumList.add(pd);
			
			int totalNum = 0;
			
			totalNum = pd.getTotalNum()/10;
	//		totalNum = pd.getTotalNum()/5;
			if(pd.getTotalNum()%10 >0) {
			
	//		if(pd.getTotalNum()%5 >0) {
				totalNum++;
			}
			//총 게시글이 2페이지가 안될때
			if(curNum == totalNum) {
				curNum = curNum -2;
				if(curNum <=0) {
					curNum = 1;
					
				}
			}else {
				if(curNum<3) {
					totalNum = 2;
					curNum = 1;
					
				}else if(totalNum-curNum <= 1){
					curNum = curNum-2;
					totalNum = curNum+3;
				}else {
					curNum = curNum-2;
					totalNum = curNum +4;
				}
				
				
			}
			System.out.println("시작페이지 : "+ curNum);
			System.out.println("마지막페이지 : " +totalNum);
			
			
			for(int i = curNum; i<=totalNum; i++) {
				pageNumList.add(i);
			
			}
		}
		
		return pageNumList;
	}
	
	public int insertBoard(HttpServletRequest req) throws SQLException {
		int check = 0;
		String title = "";
		String contents = "";
		String boardNum = "";
		title = req.getParameter("title");
		contents = req.getParameter("contents");
		boardNum = req.getParameter("boardNum");
		String insertBoard = "";
		conn = JDBCInfo.getConnection();

			insertBoard = "insert into board(boardNum,title,contents,groupNum,groupLevel,boardSeq) "
					+ "values(null, ?,?,ifnull((select max(boardNum)+1 from board as t1),1),0,0);";

		pstmt = conn.prepareStatement(insertBoard);
		pstmt.setString(1, title);
		pstmt.setString(2, contents);
		pstmt.executeUpdate();	
		conn.commit();
		
		check = pstmt.executeUpdate();
		
		if(check >0) {
			System.out.println("추가성공");
			check = 1;
		}
		return check;
	}

	
	public int  deleteBoard(HttpServletRequest req) throws SQLException {
		int check = 0;
		//String boardNum = req.getParameter("boardNum");
		int boardSeq = Integer.parseInt(req.getParameter("boardSeq"));
		int groupNum = Integer.parseInt(req.getParameter("groupNum"));
		int groupLevel = Integer.parseInt(req.getParameter("groupLevel"));
		String deleteSQL = "";
		conn = JDBCInfo.getConnection();
		if(boardSeq==0) {
			deleteSQL = "delete from board where groupNum = ?";
			pstmt = conn.prepareStatement(deleteSQL);
			pstmt.setInt(1, groupNum);
			check = pstmt.executeUpdate();
		}else {
			deleteSQL = "delete from board where groupNum = ? and groupLevel = ? and boardSeq >= ?";
			pstmt = conn.prepareStatement(deleteSQL);
			pstmt.setInt(1, groupNum);
			pstmt.setInt(2, groupLevel);
			pstmt.setInt(3, boardSeq);
			check = pstmt.executeUpdate();
		}
		
		conn.commit();
		
		if(check>0) {
			System.out.println("삭제성공");
			check = 1;
		}
		pstmt.close();
		conn.close();
		
		return check;
	}
	
	public int updateBoard(HttpServletRequest req) throws SQLException {
		int check = 0;
		String contents = req.getParameter("contents");
		String title = req.getParameter("title");
		String boardNum = req.getParameter("boardNum");
		conn = JDBCInfo.getConnection();
		
		String updateSQL ="update board set contents = ?,title = ? where boardNum=? ";
		pstmt = conn.prepareStatement(updateSQL);
		pstmt.setString(1, contents);
		pstmt.setString(2, title);
		pstmt.setString(3, boardNum);
		
		check = pstmt.executeUpdate();
		conn.commit();
		
		if(check > 0) {
			System.out.println("수정성공");
			check = 1;
		}
		pstmt.close();
		conn.close();
		return check ;
	}
	
	public BoardDomain boardInfo(HttpServletRequest req) throws SQLException {
		
		BoardDomain boardSelect = null;
		String boardNum = "";
		boardNum = req.getParameter("boardNum");
		String selectSql = "select * from board where boardNum = ?";
		conn = JDBCInfo.getConnection();
		pstmt = conn.prepareStatement(selectSql);
		pstmt.setString(1,boardNum);
		
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
			boardSelect = new BoardDomain();
			boardSelect.setBoardNum(Integer.parseInt(boardNum));
			boardSelect.setBoardSeq(rs.getInt("boardSeq"));
			boardSelect.setTitle(rs.getString("title"));
			boardSelect.setContents(rs.getString("contents"));
			boardSelect.setGroupNum(rs.getInt("groupNum"));
			boardSelect.setGroupLevel(rs.getInt("groupLevel"));
		}
		return boardSelect;
	}
	
	public ArrayList<BoardDomain> searchContents(HttpServletRequest req) throws SQLException{
		ArrayList<BoardDomain> searchList = null;
		searchList = new ArrayList<BoardDomain>();
		PageDomain pd = new PageDomain();
		int page;
		String stringPage = "1";
		
		//최초 들어오는 getParameter는 null 이후 a태그로 인하여 getParameter전달됨
		if(req.getParameter("pageNum") == null || "null".equals(req.getParameter("pageNum"))) {
			stringPage = "1";
		}else {
			stringPage = req.getParameter("pageNum");
		}
		
		page = Integer.parseInt(stringPage);
		int pageNum = 5;
		if(page <=0) {
			page = 0;
		}
		page = (page-1) * 5;
		pd.setPage(page);
		pd.setPageNum(pageNum);

		page = pd.getPage();
		pageNum = pd.getPageNum();
		
		
		String search = "";
		String searchSQL = "";

			search = req.getParameter("contents");
			searchSQL = "select * from board where contents like ? order by boardNum desc limit ?,? ";
		conn = JDBCInfo.getConnection();
		
		pstmt = conn.prepareStatement(searchSQL);
		pstmt.setString(1, "%"+search+"%");
		pstmt.setInt(2, page);
		pstmt.setInt(3, pageNum);
		rs = pstmt.executeQuery();
		
		while(rs.next()) {
			BoardDomain bd = new BoardDomain();
			bd.setBoardNum(rs.getInt("boardNum"));
			bd.setTitle(rs.getString("title"));
			bd.setContents(rs.getString("contents"));
			searchList.add(bd);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return searchList;
	}
	
	public int insertReplyBoard(HttpServletRequest req) throws SQLException {
//		ReplyDomain replyBoard  = new ReplyDomain();
		BoardDomain parent = new BoardDomain();
		int check = 0;
		String boardSeq = "";
		String title = "";
		String contents = "";
		
		String groupLevel =""; 
		String boardNum = "";
		String groupNum = "";
		boardNum = req.getParameter("boardNum");
		title = req.getParameter("title");
		contents = req.getParameter("contents");
		groupNum = req.getParameter("groupNum");
		boardSeq = req.getParameter("boardSeq");
		groupLevel= req.getParameter("groupLevel");
		
		
		
		String replySQL = "";
		String updateSQL = "";
		
		conn = JDBCInfo.getConnection();
		if(Integer.parseInt(boardSeq) == 0) {
			//댓글
			replySQL = "insert into board(boardNum,title,contents,groupNum,groupLevel,boardSeq) " + 
					"	values(null,?,?, " + 
					"	(select b.groupNum from board b where boardNum = ?), " + 
					"	(select max(b.groupLevel)+1 from board b where groupNum = ?), " + //수정중
					"	(select b.boardSeq+1 from board b where boardNum = ?) " + 
					")";
			
			pstmt = conn.prepareStatement(replySQL);
			pstmt.setString(1, title);
			pstmt.setString(2, contents);
			pstmt.setString(3, boardNum);
			pstmt.setString(4, groupNum);
			pstmt.setString(5, boardNum);
			check = pstmt.executeUpdate();
			conn.commit();
			
		}else {
			//대댓글

			replySQL =  "insert into board(boardNum,title,contents,groupNum,groupLevel,boardSeq) " + 
					"	values(null,?,?, " + 
					"(select b.groupNum from board b where boardNum = ?), "+
					"(select max(b.groupLevel) from board b where boardNum = ?), "+
					"(select b.boardSeq+1 from board b where boardNum = ?) )";
			
			pstmt = conn.prepareStatement(replySQL);
			pstmt.setString(1, title);
			pstmt.setString(2, contents);
			pstmt.setString(3, boardNum);
			pstmt.setString(4, boardNum);
			pstmt.setString(5, boardNum);
			check = pstmt.executeUpdate();
			conn.commit();

		}
//		replySQL = "insert into board(boardNum,title,contents,groupNum,groupLevel,boardSeq) " +
//				" values(null,?,?,?,?,?)";
//		pstmt = conn.prepareStatement(replySQL);
//		pstmt.setString(1, title);
//		pstmt.setString(2, contents);
//		pstmt.setInt(3, Integer.parseInt(groupNum));
//		pstmt.setInt(4,Integer.parseInt(groupLevel)+1);
//		pstmt.setInt(5, Integer.parseInt(boardSeq)+1);
//		conn.commit();

		
		if(check > 0) {
			check = 1;
		}
		
		return check;
	}
	
	public int replyUpdate(HttpServletRequest req) throws SQLException{
		BoardDomain parent = new BoardDomain();
		int check = 0;
		String groupNum = req.getParameter("groupNum");
		String boardSeq = req.getParameter("boardSeq");
		conn = JDBCInfo.getConnection();
		String updateSQL = "update board set boardSeq = boardSeq + 1 where groupNum = ? and boardSeq > ?";
		
		pstmt= conn.prepareStatement(updateSQL);
		pstmt.setInt(1, Integer.parseInt(groupNum));
		pstmt.setInt(2, Integer.parseInt(boardSeq));
		

		return check;
	}
}
