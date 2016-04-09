
public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ExcelAnalyser analyser = new ExcelAnalyser();
		try {
			analyser.Init("../../doc/广东建伟数据库及DEMO需求.xls");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Excel file is invalid !");
			return;
		}
		
		int i=0;
        int j=0;
        //循环读取数据
        for(i=0;i<analyser.GetExcelRows();i++)
        {
            for(j=0;j<analyser.GetExcelCols();j++)
            {
                System.out.print(analyser.GetCellString(j, i)+"\t");
            }
            System.out.println(" ");
        }
	}

}
