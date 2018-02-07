package com.lepus.hikariboot.app.acgn.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lepus.hikariboot.app.acgn.bean.Anime;
import com.lepus.hikariboot.app.acgn.service.AnimeService;
import com.lepus.hikariboot.app.enums.Checker;
import com.lepus.hikariboot.app.enums.Season;
import com.lepus.hikariboot.app.enums.SerialState;
import com.lepus.hikariboot.app.enums.WatchState;
import com.lepus.hikariboot.framework.build.BaseController;
import com.lepus.hikariboot.framework.build.Page;


/**
 * 
 * @author whenguycan
 * @date 2018年2月3日 上午10:29:34
 */
@RestController
public class AnimeController extends BaseController{
	
	@Resource
	AnimeService animeService;
	
	@RequestMapping("/anime/conditions")
	public Object conditions(HttpServletRequest req, HttpServletResponse resp){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("seasons", Checker.create(Season.values(), "0"));
		map.put("serials", Checker.create(SerialState.values(), "0"));
		map.put("watches", Checker.create(WatchState.values(), "0"));
		return map;
	}
	
	@RequestMapping(value = {"/anime/page", "/anime/page/{pageNo}"})
	public Object all(HttpServletRequest req, HttpServletResponse resp, 
						@PathVariable(required = false)Integer pageNo,
						@RequestParam(required = false)Integer pageSize){
		Map<String, String> params = getInterceptoredParams(req);
		params.put("s_order_desc_updateTime", "desc");
		Page<Anime> page = new Page<Anime>(pageNo, pageSize);
		return animeService.findPage(params, page);
	}
	
	@RequestMapping("/anime/detail/{id}")
	public Object preEdit(HttpServletRequest req, HttpServletResponse resp, @PathVariable()String id){
		Anime anime = animeService.fetch(id, false);
		return anime;
	}
	
	@RequestMapping("/anime/edit")
	public Object edit(HttpServletRequest req, HttpServletResponse resp, Anime anime){
		animeService.saveOrUpdate(anime);
		return getSuccessResponse(null, true);
	}
	
}
