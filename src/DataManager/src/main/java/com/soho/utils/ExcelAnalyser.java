package com.soho.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelAnalyser {
	
	private String	 m_excFile;		// 表格文件路径
	private Workbook m_workBook;	// 表格操作对象
	private Sheet 	 m_sheet;		// 工作表对象
	
	public void Init(String excfile) throws BiffException, IOException
	{
		m_excFile = excfile;
		m_workBook = Workbook.getWorkbook(new File(m_excFile));
		m_sheet = m_workBook.getSheet(0);
	}
	
	public int findExcelRows()
	{
		return m_sheet.getRows();
	}
	
	public int findExcelCols()
	{
		return m_sheet.getColumns();
	}
	
	public String findCellString(int col, int row)
	{
		Cell cur = m_sheet.getCell(col, row);
		if(cur.getType() == CellType.DATE)
		{
			DateCell dc = (DateCell)cur;
			Date date = dc.getDate();
			// 日期的格式都格式化成这种形式
			//  HH:ss:mm
			SimpleDateFormat ds = new SimpleDateFormat("yyyy-MM-dd");
			return ds.format(date);
		}
		return m_sheet.getCell(col, row).getContents();
	}
}
