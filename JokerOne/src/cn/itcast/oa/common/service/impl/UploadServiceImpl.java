package cn.itcast.oa.common.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.itcast.oa.common.service.UploadService;
import cn.itcast.oa.domain.SimUpload;
import cn.itcast.oa.model.PageInfo;
import cn.itcast.oa.model.QueryParamList;
import cn.itcast.oa.util.JPAUtil;

@Service("uploadService")
public class UploadServiceImpl implements UploadService {

	@Override
	public void save(String entityId, String entityType, String filedataFileName) {
		SimUpload sim = new SimUpload();
		sim.setEntityId(entityId);
		sim.setEntityType(entityType);
		sim.setFileName(filedataFileName);
		JPAUtil.create(sim);
	}

	@Override
	public List<SimUpload> retrieveAll(String entityId, String entityType,
			PageInfo pageInfo) {
		String jpql = "select sim from SimUpload sim where sim.entityId =:entityId and sim.entityType =:entityType";
		QueryParamList params = new QueryParamList();
		params.addParam("entityId", entityId);
		params.addParam("entityType", entityType);
		List<SimUpload> result = JPAUtil.find(jpql, params, null, pageInfo);
		return initInfo(result);

	}

	private List<SimUpload> initInfo(List<SimUpload> result) {
		for (SimUpload sim : result) {

			String name = sim.getFileName().substring(0,
					sim.getFileName().lastIndexOf("."));
			if (name.length() > 15) {// 如果文件名称太长，用。。。代替
				name = name.substring(0, 14) + "...";
			}
			String suffixName = sim.getFileName()
					.substring(sim.getFileName().lastIndexOf(".") + 1)
					.toLowerCase();
			sim.setSuffixName(suffixName);// 扩展名
			sim.setFileName(name);// filename 的set要放后面，否则扩展名拿不到了，name时已经把扩展名去掉了。

			// 判断扩展名是否为图片
			String picSuffix = "jpg,jpeg,bmp,png,";
			// 判断扩展名是否问文档
			String docSuffix = "doc,docx,xls,xlsx,";
			if (picSuffix.indexOf(suffixName + ",") >= 0) {
				sim.setVisibleFlag("");
				sim.setFileType("pic");
			} else if (docSuffix.indexOf(suffixName + ",") >= 0) {
				sim.setVisibleFlag("");
				sim.setFileType("doc");
			} else {
				sim.setVisibleFlag("none");
			}
		}
		return result;
	}

	@Override
	public SimUpload retrieve(String uploadId) {
		return JPAUtil.loadById(SimUpload.class, uploadId);
	}
	
	@Override
	public void remove(String uploadId) {
		JPAUtil.remove(SimUpload.class, uploadId);
	}
}
