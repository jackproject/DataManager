
import jxl.*;
import jxl.read.biff.BiffException;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExcelAnalyser {
	
	public ExcelAnalyser() {
		super();
		this.m_content = new HashMap<String, ArrayList<String>>();
	}

	private String	 m_excFile;		// 表格文件路径
	private Workbook m_workBook;	// 表格操作对象
	private Sheet 	 m_sheet;		// 工作表对象d
	private Map<String, ArrayList<String>> m_content;	//表内容对象
	
	public void Init(String excfile) throws BiffException, IOException
	{
		m_excFile = excfile;
		m_workBook = Workbook.getWorkbook(new File(m_excFile));
		m_sheet = m_workBook.getSheet(0);
		
		//将表格内容保存到内存中
		m_content.clear();
		for (int i = 0; i < GetExcelCols(); i++) 
		{
			String title = new String();
			ArrayList<String> list = new ArrayList<String>();
			title = GetCellString(i, 0);
			for (int j = 1; j < GetExcelRows(); j++) 
			{
				list.add(GetCellString(i, j));
			}
			m_content.put(title, list);
		}
	}
	
	public Map<String, ArrayList<String>> GetExcelContent() 
	{
		return m_content;
	}
	
	private int GetExcelRows()
	{
		return m_sheet.getRows();
	}
	
	private int GetExcelCols()
	{
		return m_sheet.getColumns();
	}
	
	private String GetCellString(int col, int row)
	{
		Cell cur = m_sheet.getCell(col, row);
		if(cur.getType() == CellType.DATE)
		{
			DateCell dc = (DateCell)cur;
			Date date = dc.getDate();
			SimpleDateFormat ds = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
			return ds.format(date);
		}
		return m_sheet.getCell(col, row).getContents();
	}
}
