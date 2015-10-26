package cn.itcast.oa.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Excel导出通用类
 * 
 * @author haojiahong
 * 
 * @createtime：2015-7-14 下午4:13:50
 * 
 * 
 */
public class PoiExUtil {
	private Workbook workBook;
	private Sheet sheet;
	private CreationHelper createHelper;
	private Row curRow;
	private int rowIndex = 0;
	private int columnIndex = 0;

	// 普通单元格样式、必填项单元格样式、普通日期样式、必填项日期样式、表头样式
	private CellStyle normalStyle;
	private CellStyle requiredStyle;
	private CellStyle normalDateStyle;
	private CellStyle requiredDateStyle;
	private CellStyle headerStyle;

	private List<String> hiddenList = new ArrayList<String>();
	// 下拉sheet的索引
	private char beginIndex = 'A';
	private int dropColumnIndex = 0;

	// 数据显示的索引开始
	private int dataIndex;

	/**
	 * 导出Excel文件流
	 * 
	 * @return
	 * @throws Exception
	 */
	public InputStream write() throws Exception {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		workBook.write(byteStream);
		return new ByteArrayInputStream(byteStream.toByteArray());
	}

	public PoiExUtil(String sheetName) {
		workBook = new HSSFWorkbook();
		sheet = workBook.createSheet(sheetName);
		createHelper = workBook.getCreationHelper();
		initStyle();
	}

	// 初始化样式
	private void initStyle() {
		// 普通单元格样式
		normalStyle = workBook.createCellStyle();
		normalStyle.setBorderBottom(CellStyle.BORDER_THIN);
		normalStyle.setBorderLeft(CellStyle.BORDER_THIN);
		normalStyle.setBorderRight(CellStyle.BORDER_THIN);
		normalStyle.setBorderTop(CellStyle.BORDER_THIN);
		normalStyle.setWrapText(true);
		normalStyle.setDataFormat(createHelper.createDataFormat()
				.getFormat("@"));

		// 必填项单元格的样式
		requiredStyle = workBook.createCellStyle();
		requiredStyle.cloneStyleFrom(normalStyle);
		requiredStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		requiredStyle.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
		requiredStyle.setDataFormat(createHelper.createDataFormat().getFormat(
				"@"));

		// 日期样式
		normalDateStyle = workBook.createCellStyle();
		normalDateStyle.cloneStyleFrom(normalStyle);
		normalDateStyle.setDataFormat(createHelper.createDataFormat()
				.getFormat("yyyy-MM-dd"));

		// 必填项日期的样式
		requiredDateStyle = workBook.createCellStyle();
		requiredDateStyle.cloneStyleFrom(normalDateStyle);
		requiredDateStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		requiredDateStyle.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);

		// 表头样式
		headerStyle = workBook.createCellStyle();
		headerStyle.cloneStyleFrom(normalStyle);
		headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		headerStyle.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
		Font font = workBook.createFont();
		font.setColor(HSSFColor.WHITE.index);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerStyle.setFont(font);

	}

	// 设置列宽（1个汉字的宽度）
	public void setColumnWidth(int[] array) {
		for (int i = 0; i < array.length; i++) {
			sheet.setColumnWidth(i, array[i] * 512);
		}
	}

	/**
	 * 添加隐藏属性
	 * 
	 * @param strArray
	 */
	public void addHiddenValue(String[] strArray) {
		if (curRow == null) {
			curRow = sheet.createRow(rowIndex++);
			columnIndex = 0;
		}
		hiddenList.clear();
		for (String value : strArray) {
			Cell cell = curRow.createCell(columnIndex++);
			cell.setCellValue(value);
			hiddenList.add(value);
		}
		curRow.setHeight((short) 0);
		sheet.createFreezePane(0, 1, 0, 1);
		dataIndex++;
	}

	/**
	 * 创建表头
	 */
	public void createHeaderRow() {
		curRow = sheet.createRow(rowIndex++);
		curRow.setHeight((short) 400);
		this.columnIndex = 0;
		dataIndex++;
	}

	/**
	 * 批量添加表头
	 * 
	 * @param strArray
	 */
	public void addHeaderValue(String[] strArray) {
		for (String value : strArray) {
			this.addHeaderValue(value);
		}
	}

	/**
	 * 添加表头
	 * 
	 * @param value
	 */
	public void addHeaderValue(String value) {
		Cell cell = curRow.createCell(columnIndex++);
		cell.setCellStyle(this.headerStyle);
		cell.setCellValue(value);
	}

	/**
	 * 创建普通行
	 */
	public void createRow() {
		curRow = sheet.createRow(rowIndex++);
		this.columnIndex = 0;
	}

	/**
	 * 必填单元格添加value
	 * 
	 * @param value
	 */
	public void addRequiredValue(String value) {
		Cell cell = curRow.createCell(columnIndex++);
		cell.setCellStyle(requiredStyle);
		cell.setCellValue(value);
	}

	/**
	 * 普通单元格添加value
	 * 
	 * @param value
	 */
	public void addValue(String value) {
		Cell cell = curRow.createCell(columnIndex++);
		cell.setCellStyle(normalStyle);
		cell.setCellValue(value);
	}

	/**
	 * 必填单元格添加日期value
	 * 
	 * @param value
	 */
	public void addRequiredValue(Date value) {
		Cell cell = curRow.createCell(columnIndex++);
		cell.setCellStyle(requiredDateStyle);
		if (value != null) {
			cell.setCellValue(value);
		}
	}

	/**
	 * 创建名称管理器
	 * 
	 * @param nameName
	 *            名称的名字
	 * @param list
	 *            字符串集合
	 */
	public void createName(String nameName, List<String> list) {
		Sheet nameSheet = workBook.getSheet("下拉");
		if (nameSheet == null) {
			nameSheet = workBook.createSheet("下拉");
			nameSheet.createRow(0);
		}
		int lastRowNum = nameSheet.getLastRowNum();
		for (int i = 0; i < list.size(); i++) {
			if (i <= lastRowNum) {
				nameSheet.getRow(i).createCell(dropColumnIndex)
						.setCellValue(list.get(i));
			} else {
				nameSheet.createRow(i).createCell(dropColumnIndex)
						.setCellValue(list.get(i));
			}
		}

		Name dutyName = workBook.createName();
		dutyName.setNameName(nameName);
		dutyName.setRefersToFormula("'下拉'!$" + beginIndex + "$1:$" + beginIndex
				+ "$" + (list.size() == 0 ? 1 : list.size()));// 0会报错

		dropColumnIndex++;
		beginIndex++;
	}
}
