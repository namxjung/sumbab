package com.sumbab.project.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sumbab.project.model.Notice;
import com.sumbab.project.model.NoticeService;

@Controller
public class NoticeController {

	private NoticeService noticeService;

	@Autowired
	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}

	@RequestMapping("/mypage/noticePage")
	public String firstPage(Model model, HttpSession session) {
		String id="admin1";
		session.setAttribute("classify", noticeService.classify(id));
		model.addAttribute("noticeList", noticeService.bringNotice(id));
		return "mypage/noticePage";
	}
	
	@RequestMapping("/mypage/noticeDetail/{noticeNum}")
	public String noticeDetail(Model model, @PathVariable int noticeNum) {
		model.addAttribute("noticeVo", noticeService.noticeDetail(noticeNum));
		return "mypage/noticeDetail";
	}
	
	@RequestMapping(value="/mypage/writeNotice", method=RequestMethod.GET)
	public String write(Model model) {
		model.addAttribute("notice", new Notice());
		return "mypage/writeNotice";
	}
	
	@RequestMapping(value="/mypage/writeNotice", method=RequestMethod.POST)
	public String write(Notice notice) {
		noticeService.write(notice);
		return "redirect:/mypage/noticePage";
	}
	
	@RequestMapping(value="/mypage/editNotice/{noticeNum}", method=RequestMethod.GET)
	public String edit(@PathVariable int noticeNum, Model model) {
		model.addAttribute("notice", noticeService.noticeDetail(noticeNum));
		return "mypage/editNotice";
	}
	
	@RequestMapping(value="/mypage/editNotice/{noticeNum}", method=RequestMethod.POST)
	public String edit(@PathVariable int noticeNum, Notice notice) {
		noticeService.edit(notice);
		return "redirect:/mypage/noticeDetail/"+noticeNum;
	}
	
	@RequestMapping(value="/mypage/deleteNotice", method=RequestMethod.GET)
	public String delete() {
		return "mypage/deleteNotice";
	}
	
	@RequestMapping(value="/mypage/deleteNotice", method=RequestMethod.POST)
	public String delete(int noticeNum) {
		noticeService.delete(noticeNum);
		return "redirect:/mypage/noticePage";
	}
	
}
