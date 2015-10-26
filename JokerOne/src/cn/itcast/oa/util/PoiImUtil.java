package cn.itcast.oa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * excel导入工具类
 * 
 * @author haojiahong
 * 
 * @createtime：2015-7-16 下午3:41:42
 * 
 * 
 */
public class PoiImUtil {

	private Sheet sheet;
	private Workbook workbook;
	private CreationHelper createHelper;
	private int nowrow = 0;// 当前行
	private int nowcolumn = 0;
	private Row row;
	private List<String> head;
	private CellStyle style = null;// 主要类型,边框

	/**
	 * 导入excel用,返回行数
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	public int importExcel(File file) throws Exception {
		workbook = this.openFile(file);
		sheet = workbook.getSheetAt(0);
		createHelper = workbook.getCreationHelper();
		row = sheet.getRow(0);
		head = this.getRowHead();
		return sheet.getLastRowNum();
	}

	/**
	 * Workbook读取excel工厂,可以判断扩展名选择2007或者2003的excelapi
	 * 
	 * @param file
	 * @return Sheet
	 * @throws Exception
	 */
	public Workbook openFile(File file) throws Exception {
		InputStream inp = null;
		try {
			if (!file.exists() || !file.isFile()) {
				throw new Exception("文件不存在!");
			}
			// 判断扩展名是否正确 -- xlsx或者xls的
			if (!"xls".equalsIgnoreCase(CommonUtil.getExtName(file.getName()))
					&& !"xlsx".equalsIgnoreCase(CommonUtil.getExtName(file
							.getName()))) {
				throw new Exception("文件格式不正确，请重新选择Excel文件！");
			}
			inp = new FileInputStream(file);
			return WorkbookFactory.create(inp);
		} finally {
			if (inp != null) {
				inp.close();
			}
		}
	}

	/**
	 * 获取第一行所有字符串集合.表头验证
	 * 
	 * @param headRow
	 * @return
	 * @throws Exception
	 */
	public List<String> getRowHead() throws Exception {
		Row headRow = sheet.getRow(0);
		if (headRow == null) {
			return null;
		}
		Iterator<Cell> cellIter = headRow.cellIterator();
		List<String> hiddenhead = new ArrayList<String>();// 获取第一列的所有字符
		while (cellIter.hasNext()) {
			hiddenhead.add(cellIter.next().toString());
		}
		return hiddenhead;
	}

	/**
	 * 移到下一行,列开头(读取)
	 */
	public Row nextRow() {
		row = sheet.getRow(++nowrow);
		this.nowcolumn = 0;
		return row;
	}

	public String getStr(String strindex) throws Exception {
		int numIndex = head.indexOf(strindex);
		return getStrByNumIndex(numIndex);

	}

	private String getStrByNumIndex(int numIndex) throws Exception {
		String result = null;
		if (row != null) {
			Cell cell = row.getCell(numIndex);
			if (cell == null) {
				return "";
			}
			// 如果是公式，那么需要处理，公式结算结果必须为数字，否则抛出异常
			if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
				result = cell.getNumericCellValue() + "";
			} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {// 单元格是字符
				result = row.getCell(numIndex) == null ? null : row.getCell(
						numIndex).getStringCellValue();
			} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {// 单元格是数字
				result = row.getCell(numIndex) == null ? null : row.getCell(
						numIndex).getNumericCellValue()
						+ "";
				if (result.indexOf(".0") == result.length() - 2) {
					result = result.substring(0, result.length() - 2);
				}
			} else {
				result = row.getCell(numIndex) == null ? null : row.getCell(
						numIndex).toString();
			}
		}
		return result;
	}

	public Timestamp getDate(String strindex) throws Exception {
		int numIndex = head.indexOf(strindex);
		return this.getDateByNumIndex(numIndex);
	}

	public Timestamp getDateByNumIndex(int numIndex) throws Exception {
		Cell mycell = row.getCell(numIndex);
		Date utilDate = null;
		if (mycell.getCellType() == Cell.CELL_TYPE_NUMERIC
				&& DateUtil.isCellDateFormatted(mycell)) {
			// 日期格式需要单独处理，否则读不出来yyyy年mm月dd日或其他格式
			// format为yyyy-MM-dd格式
			utilDate = row.getCell(numIndex).getDateCellValue();
			if (utilDate == null) {
				return null;
			}
		} else {
			try {
				String datestr = row.getCell(numIndex).getStringCellValue();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				utilDate = dateFormat.parse(datestr);
			} catch (Exception e) {
				return null;
			}
		}
		return new java.sql.Timestamp(utilDate.getTime());
	}
}
