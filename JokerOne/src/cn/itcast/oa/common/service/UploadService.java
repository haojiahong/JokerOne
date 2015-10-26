package cn.itcast.oa.common.service;

import java.util.List;

import cn.itcast.oa.domain.SimUpload;
import cn.itcast.oa.model.PageInfo;

public interface UploadService  {

	public void save(String entityId, String entityType, String filedataFileName);

	public List<SimUpload> retrieveAll(String entityId, String entityType,
			PageInfo pageInfo);

	public SimUpload retrieve(String uploadId);

	public void remove(String uploadId);
	
	


}
