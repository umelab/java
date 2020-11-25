import umelab.CSVReader;

public class CSVReaderExample {
	
	public static void main(String args[]) {
		String path = "test.csv";
		CSVReader reader = null;
		String token;
		try{
			reader = new CSVReader(path);
			
			while((token = reader.nextToken()) != null) {
				System.out.println("token: " + token);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

		