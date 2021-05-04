package com.sumbab.project.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sumbab.project.model.PickService;

@Controller
public class PickController {

	@Autowired
	private PickService pickService;
	
	//보관함에 담기 버튼 클릭(Ajax 사용)
	//반환값이 1:이미 보관함에 해당 가게가 담겨 있을 경우, 0:보관함에 해당 가게 추가
	@RequestMapping(value="/storeWarning/pick/{storeNum}", method=RequestMethod.POST)
	public int memberPick(@PathVariable int storeNum, HttpServletResponse response, HttpServletRequest request) {
		String id="hello";			//merge하면 session에 있는 id 사용
		if(!id.equals(null)) {		//로그인 했을 경우
			return pickService.bringPick(id, storeNum);
		}
		//비회원일 경우
		List<String> list = new ArrayList<>(); 		//쿠키에 담을 가게번호들 넣는 객체
		Cookie[] cookies = request.getCookies();	//클라이언트에 있는 쿠키들 가져오기
		String value = "";							
		String[] picked = null;
		if(cookies != null) {						//클라이언트에 쿠키가 존재할 경우
			for(int i=0; i<cookies.length; i++) {
				if(cookies[i].getName().equals("pick")) {	//보관함에 해당하는 쿠키가 존재할 경우
					value = cookies[i].getValue();	//쿠키에 저장되어 있는 가게번호들을 문자열로 저장
					picked = value.split(",");		//문자열로 저장한 가게번호들을 ,로 구분하여 배열에 저장
					break;
				}
			}
		}
		if(value.equals("")) {						//보관함에 해당하는 쿠키가 존재하지 않을 경우
			Cookie cookie = new Cookie("pick", Integer.toString(storeNum));
			cookie.setMaxAge(60*60*24);
			cookie.setPath("/");
			response.addCookie(cookie);
			return 0;
		} else {
			for(int i=0; i<picked.length; i++) {
				if(picked[i].equals(Integer.toString(storeNum)))	//보관함에 추가할 가게번호가 이미 있을 경우
					return 1;
			}
			//보관함에 추가할 가게번호가 없을 경우
			for(int i=0; i<picked.length; i++)		
				list.add(picked[i]);				//가게 번호를 추가하기 위해 크기가 정해져 있지 않은 List 타입으로 기존에 있던 가게번호들 담기
			list.add(Integer.toString(storeNum));	//가게 번호 추가
			String finalStorage = "";				
			for(int i=0; i<list.size(); i++)
				finalStorage += list.get(i) + ",";	//쿠키에 List 타입으로 저장할 수 없기 때문에 다시 String형으로 가게번호들 담기
			Cookie cookie = new Cookie("pick", finalStorage);
			cookie.setMaxAge(60*60*24);
			cookie.setPath("/");
			response.addCookie(cookie);
			return 0;
		}			
	}
}
