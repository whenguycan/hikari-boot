package com.lepus.hikariboot.app.acgn.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.lepus.hikariboot.app.acgn.bean.Anime;
import com.lepus.hikariboot.framework.build.BaseService;
import com.lepus.hikariboot.utils.StringUtils;

/**
 * 
 * @author whenguycan
 * @date 2018年2月3日 下午1:34:56
 */
@Service
public class AnimeService extends BaseService<Anime>{
	
	public Anime saveOrUpdate(Anime e){
		if(StringUtils.isBlank(e.getId())){
			return save(e);
		}else{
			Anime origin = fetch(e.getId(), false);
			if(origin == null){
				return null;
			}else{
				origin.setName(e.getName());
				origin.setCurr(e.getCurr());
				origin.setTotal(e.getTotal());
				origin.setSerialState(e.getSerialState());
				origin.setSeason(e.getSeason());
				origin.setLink(e.getLink());
				origin.setUpdateTime(new Date());
				origin.calWatchState();
				return dao.update(origin);
			}
		}
	}
	
	public Anime changeFavo(String id){
		Anime origin = fetch(id, false);
		if(origin != null){
			origin.setFavo(origin.getFavo() ^ 1);
			return dao.update(origin);
		}
		return null;
	}
	
}