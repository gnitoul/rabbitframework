package com.rabbitframework.commons.exl;

import com.rabbitframework.commons.exl.annotations.Exl;
import com.rabbitframework.commons.exl.annotations.ExlColumnIndex;
import com.rabbitframework.commons.reflect.MetaObject;
import com.rabbitframework.commons.reflect.MetaObjectUtils;
import com.rabbitframework.commons.utils.ClassUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Excel工具类，对poi封装
 *
 * @author justin.liang
 */
public class ExcelUploadParse<T> {
	private static final Logger logger = LoggerFactory.getLogger(ExcelUploadParse.class);
	private Workbook workbook;
	private int sheetIndex;
	private int titleIndex;
	private Map<Integer, String> entity = new HashMap<Integer, String>();
	private Class<T> classzz;

	public ExcelUploadParse(InputStream inputStream, Class<T> classzz) throws Exception {
		workbook = getHSSFWorkbook(inputStream);
		this.classzz = classzz;
		parseEntity();
	}

	public void parseEntity() {
		Exl exl = classzz.getAnnotation(Exl.class);
		sheetIndex = exl.sheetIndex();
		titleIndex = exl.titleIndex();
		Field[] fields = classzz.getDeclaredFields();
		for (Field field : fields) {
			ExlColumnIndex exlColumnIndex = field.getAnnotation(ExlColumnIndex.class);
			entity.put(exlColumnIndex.index(), field.getName());
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> parse() {
		List<T> result = new ArrayList<T>();
		Sheet sheet = workbook.getSheetAt(sheetIndex);
		int rowNum = sheet.getLastRowNum(); // 获取总行数下标从0开始
		Row row = sheet.getRow(titleIndex);
		// 标题总列数
		int colNum = row.getPhysicalNumberOfCells();
		for (int i = titleIndex + 1; i <= rowNum; i++) {
			row = sheet.getRow(i);
			T mappedObject = (T) ClassUtils.newInstance(classzz);
			MetaObject metaObject = MetaObjectUtils.forObject(mappedObject);
			for (int j = 0; j < colNum; j++) {
				String fieldName = entity.get(j);
				metaObject.setValue(fieldName, getCellFormatValue(row.getCell(j)).trim());
			}
			result.add(mappedObject);
		}
		return result;
	}

	public int getSheetIndex() {
		return sheetIndex;
	}

	public int getTitleIndex() {
		return titleIndex;
	}

	public String[] getTitles() {
		Sheet sheet = workbook.getSheetAt(sheetIndex);
		// int rowNum = sheet.getPhysicalNumberOfRows(); //获取行数下标从1计算
		Row row = sheet.getRow(titleIndex);
		// 标题总列数
		int colNum = row.getPhysicalNumberOfCells();
		String[] title = new String[colNum];
		for (int i = 0; i < colNum; i++) {
			// title[i] = getStringCellValue(row.getCell((short) i));
			title[i] = getCellFormatValue(row.getCell(i));
		}
		return title;
	}

	private Workbook getHSSFWorkbook(InputStream inputStream) throws IOException {
		Workbook book = null;
		try {
			book = new XSSFWorkbook(inputStream);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			book = new HSSFWorkbook(inputStream);
		}
		return book;
	}

	/**
	 * 根据HSSFCell类型设置数据
	 *
	 * @param cell
	 * @return
	 */
	private String getCellFormatValue(Cell cell) {
		String cellvalue = "";
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
			// 如果当前Cell的Type为NUMERIC
			case HSSFCell.CELL_TYPE_NUMERIC:
			case HSSFCell.CELL_TYPE_FORMULA: {
				// 判断当前的cell是否为Date
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// 如果是Date类型则，转化为Data格式
					// 方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
					// cellvalue = cell.getDateCellValue().toLocaleString();
					// 方法2：这样子的data格式是不带带时分秒的：2011-10-12
					Date date = cell.getDateCellValue();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					cellvalue = sdf.format(date);
				}
				// 如果是纯数字
				else {
					// 取得当前Cell的数值
					cellvalue = String.valueOf(cell.getNumericCellValue());
				}
				break;
			}
				// 如果当前Cell的Type为STRIN
			case HSSFCell.CELL_TYPE_STRING:
				// 取得当前的Cell字符串
				cellvalue = cell.getRichStringCellValue().getString();
				break;
			// 默认的Cell值
			default:
				cellvalue = " ";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;
	}

	public static void main(String[] args) throws Exception {
//		InputStream inputStream = new FileInputStream("E:\\goodsInfo.xlsx");
//		ExcelUploadParse<ExlGoodsInfo> excelParse = new ExcelUploadParse<ExlGoodsInfo>(inputStream, ExlGoodsInfo.class);
//		String[] titles = excelParse.getTitles();
//		int titlesSize = titles.length;
//		logger.debug("titlesSize:" + titlesSize);
//		List<ExlGoodsInfo> goodsInfos = excelParse.parse();
		// for (ExlGoodsInfo goodsInfo : goodsInfos) {
		// logger.debug("getGoodsCategoryName:" +
		// goodsInfo.getGoodsCategoryName() +
		// ",goodsName:" + goodsInfo.getGoodsName());
		// }
	}
}