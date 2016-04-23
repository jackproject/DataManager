import java.util.ArrayList;
import java.util.Map;


public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ExcelAnalyser analyser = new ExcelAnalyser();
		try {
			analyser.Init("../../doc/测试数据.xls");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Excel file is invalid !");
			return;
		}
		
		Map<String, ArrayList<String>> content = analyser.GetExcelContent();
		for (String key : content.keySet()) {
			System.out.print(key + " : ");
			ArrayList<String> values = content.get(key);
			for (String tmp : values) {
				System.out.print(tmp + " ");
			}
			
			System.out.println(" ");
		}
	}

}
