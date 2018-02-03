package com.lepus.hikariboot.app.acgn.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lepus.hikariboot.app.acgn.bean.Anime;
import com.lepus.hikariboot.app.acgn.service.AnimeService;


/**
 * 
 * @author whenguycan
 * @date 2018年2月3日 上午10:29:34
 */
@RestController
public class AnimeController {
	
	@Resource
	AnimeService animeService;
	
	@RequestMapping(value = {"/anime/page", "/anime/page/{pageNo}"})
	public Object all(HttpServletRequest req, HttpServletResponse resp, @PathVariable(required = false)Integer pageNo){
		Anime anime = animeService.fetch("402881626078d2da016078d443c00000", false);
		System.out.println();
		return anime;
	}
	
}
