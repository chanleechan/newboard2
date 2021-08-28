package com.lch.board.service;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.lch.board.DAO.BoardDao;

//import org.springframework.stereotype.Service;

import com.lch.board.domain.BoardDomain;
public class BoardService {
	
	public ArrayList<BoardDomain> boardList( HttpServletRequest req) throws SQLException{
		ArrayList<BoardDomain> boardList= 
				new ArrayList<BoardDomain>();
		BoardDao bd = new BoardDao();
		boardList = bd.boardList(req);
		return boardList;
	}
	
	public int insertBoard(HttpServletRequest req) throws SQLException {
		int check = 0;
		BoardDao bd = new BoardDao();
		
		check = bd.insertBoard(req);
		return check;
	}
	
	public int updateBoard(HttpServletRequest req) throws SQLException {
		int check = 0;
		BoardDao bd = new BoardDao();
		check = bd.updateBoard(req);
		return check;
	}
	
	public int deleteBoard(HttpServletRequest req) throws SQLException {
		int check = 0;
		BoardDao bd = new BoardDao();
		check = bd.deleteBoard(req);
		return check;
	}
	
	public BoardDomain selcetBoard(HttpServletRequest req) throws SQLException {
		BoardDomain selectBoard = new BoardDomain();
		BoardDao bd = new BoardDao();
		selectBoard = bd.boardInfo(req);
		
		return selectBoard;
	}
	
	public ArrayList<BoardDomain> searchContents(HttpServletRequest req) throws SQLException{
		ArrayList<BoardDomain> searchList = null;
		searchList = new ArrayList<BoardDomain>();
		BoardDao bd = new BoardDao();
		searchList = bd.searchContents(req);
		
		return searchList;
	}
	
	public ArrayList<Integer> totalNum(HttpServletRequest req) throws SQLException{
		ArrayList<Integer> totalNumList = null;
		totalNumList = new ArrayList<Integer>();
		
		BoardDao bd = new BoardDao();
		
		totalNumList = bd.totalNum(req);
		
		return totalNumList;
	}
	
	public int insertReplyBoard(HttpServletRequest req) throws SQLException {
		int check = 0;
		BoardDao bd = new BoardDao();
		check = bd.insertReplyBoard(req);
		return check;
	}
	
	

}
