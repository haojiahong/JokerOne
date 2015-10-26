package cn.itcast.oa.util;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 定时任务，清空临时文件夹
 * 
 * @author haojiahong
 * 
 * @createtime：2015-7-21 下午2:23:55
 * 
 * 
 */
public class TaskJob {
	private Log log = LogFactory.getLog(this.getClass());

	public TaskJob() {
		log.debug("初始化定时任务类完成。。");
	}

	public void exceteDelJob() {
		String tempPath = (String) this.getAppConfig().getAppExtProp()
				.get("fileTempPath");
		System.out.println(tempPath);
		if (!delFile(tempPath)) {
			return;
		}

	}

	private boolean delFile(String tempPath) {
		File file = new File(tempPath);
		if (!file.exists()) {
			log.debug("文件夹不存在");
			return false;
		}
		if (file.isFile()) {
			file.delete();
			log.debug("单个文件删除成功");
			return true;
		}
		File[] files = file.listFiles();
		for (File f : files) {
			if (f.isFile()) {
				f.delete();
				log.debug("文件夹中单个文件删除成功");
			} else {
				this.delFile(f.getAbsolutePath());
				log.debug("开始删除文件夹中的文件夹");
			}
		}
		file.delete();
		return true;
	}

	public AppConfig getAppConfig() {
		return (AppConfig) ApplicationUtil.getBean("appConfig");
	}

}
