package cn.itcast.oa.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * 打印工具类
 * 
 * @author haojiahong
 * 
 * @createtime：2015-8-13 下午4:40:27
 * 
 * 
 */
public class JasperUtil {
	public static InputStream exportPdfDir(String fileName,
			Map<String, Object> maps, List<?> ls) {
		try {
			JRDataSource ds = new JRBeanCollectionDataSource(ls, false);

			String filenurl = PathUtil.getWebPath() + "ireport/" + fileName;
			InputStream file = new FileInputStream(filenurl);

			JasperPrint jasperPrint = JasperFillManager.fillReport(file, maps,
					ds);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
