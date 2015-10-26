package cn.itcast.oa.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
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
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;

//import com.haiyisoft.ep.framework.model.DropBean;
//import com.haiyisoft.ep.framework.util.DropBeanUtil;

/**
 * Excel导出通用处理类
 * 
 * 从aw哪里拷过来的，有可能以后有用(里面有一些没有拷贝到poiIMUtil那个工具类中)
 * 
 */
public class PoiExUtilFromAW {

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
	 * 普通单元格添加日期型value
	 * 
	 * @param value
	 */
	public void addValue(Date value) {
		Cell cell = curRow.createCell(columnIndex++);
		cell.setCellStyle(normalDateStyle);
		if (value != null) {
			cell.setCellValue(value);
		}
	}

	public void addValue(Timestamp value) {
		this.addValue(new Date(value.getTime()));
	}

	/**
	 * 普通单元格添加Double型value
	 * 
	 * @param value
	 */
	public void addValue(Double value) {
		Cell cell = curRow.createCell(columnIndex++);
		cell.setCellStyle(normalStyle);
		cell.setCellValue(value);
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
	 * 必填单元格添加Double型value
	 * 
	 * @param value
	 */
	public void addRequiredValue(Double value) {
		Cell cell = curRow.createCell(columnIndex++);
		cell.setCellStyle(requiredStyle);
		cell.setCellValue(value);
	}

	/**
	 * 合并单元格
	 * 
	 * @param rowFrom
	 *            开始行
	 * @param rowTo
	 *            结束行
	 * @param colFrom
	 *            开始列
	 * @param colTo
	 *            结束列
	 */
	public void mergeRegion(int rowFrom, int rowTo, int colFrom, int colTo) {
		sheet.addMergedRegion(new CellRangeAddress(rowFrom, rowTo, colFrom,
				colTo));
		sheet.getRow(rowFrom).getCell(colFrom).getCellStyle()
				.setAlignment(CellStyle.ALIGN_CENTER);
		sheet.getRow(rowFrom).getCell(colFrom).getCellStyle()
				.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	}

	/**
	 * 创建名称管理器
	 * 
	 * @param nameName
	 *            名称的名字
	 * @param dropName
	 *            下拉的名字
	 */
	public void createName(String nameName, String dropName) {
		// DropBeanUtil beanUtil = new DropBeanUtil();
		// List<DropBean> list = beanUtil.getDrop(dropName);
		List<String> strList = new ArrayList<String>();
		// for (DropBean bean : list) {
		// strList.add(bean.getLabel() + "(" + bean.getValue() + ")");
		// }
		this.createName(nameName, strList);
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

	/**
	 * 添加数据有效性验证
	 * 
	 * @param colIndex
	 *            索引号
	 * @param nameName
	 *            序列名称
	 */
	public void addConstraint(int colIndex, String nameName) {
		DVConstraint constraint = DVConstraint
				.createFormulaListConstraint(nameName);
		CellRangeAddressList regions = new CellRangeAddressList(
				(short) dataIndex, (short) rowIndex - 1, (short) colIndex,
				(short) colIndex);
		HSSFDataValidation nameValidation = new HSSFDataValidation(regions,
				constraint);
		sheet.addValidationData(nameValidation);
	}

	/**
	 * 添加数据有效性验证
	 * 
	 * @param colIndex
	 *            隐藏属性值
	 * @param nameName
	 *            序列名称
	 */
	public void addConstraint(String colName, String nameName) {
		int index = hiddenList.indexOf(colName);
		this.addConstraint(index, nameName);
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
	 * 创建标题头
	 */
	public void createTitle() {
		curRow = sheet.createRow(rowIndex++);
		curRow.setHeight((short) 500);
		this.columnIndex = 0;
		dataIndex++;
	}

	/**
	 * 添加标题（默认居中对其）
	 * 
	 * @param title
	 */
	public void addTitle(String title) {
		Cell cell = curRow.createCell(columnIndex++);
		cell.setCellValue(title);
		CellStyle titleStyle = workBook.createCellStyle();
		titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
		titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Font font = workBook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontHeight((short) 300);
		titleStyle.setFont(font);
		cell.setCellStyle(titleStyle);
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
	 * 创建普通行
	 */
	public void createRow() {
		curRow = sheet.createRow(rowIndex++);
		this.columnIndex = 0;
	}

	/**
	 * 设置当前的sheet页
	 * 
	 * @param sheetName
	 */
	public void setCurrentSheet(String sheetName) {
		if (workBook.getSheet(sheetName) != null) {
			// 设置sheet为当前的指定的sheet
			sheet = workBook.getSheet(sheetName);
			// 将隐藏的值清空，获取指定sheet的隐藏值
			hiddenList.clear();
			hiddenList = this.getRowHead();
			// 切换sheet页，将焦点移到最后一行，第一列
			rowIndex = sheet.getLastRowNum();
			columnIndex = 0;
		}
	}

	private List<String> getRowHead() {
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
	 * 创建sheet页
	 * 
	 * @param sheetName
	 */
	public void createSheet(String sheetName) {
		sheet = workBook.createSheet(sheetName);
		rowIndex = 0;
		columnIndex = 0;
	}

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

	// 设置列宽（1个汉字的宽度）
	public void setColumnWidth(int[] array) {
		for (int i = 0; i < array.length; i++) {
			sheet.setColumnWidth(i, array[i] * 512);
		}
	}

	public PoiExUtilFromAW(String sheetName) {
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

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public void setFrozen(int rowNum) {
		sheet.createFreezePane(0, rowNum, 0, rowNum);
	}
}
