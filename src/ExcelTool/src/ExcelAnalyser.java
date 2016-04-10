
import jxl.*;
import jxl.read.biff.BiffException;
import java.io.*;

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
	
	public int GetExcelRows()
	{
		return m_sheet.getRows();
	}
	
	public int GetExcelCols()
	{
		return m_sheet.getColumns();
	}
	
	public String GetCellString(int col, int row)
	{
		return m_sheet.getCell(col, row).getContents();
	}
}
