package postgresql;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 */
public class App {
    
    String url = "jdbc:postgresql://localhost:5432/mydb1";
    String user = "postgres";
    String password = "abc13579";
    Connection c = null;
    Statement stmt = null;
    PreparedStatement preparedStatement = null;
	String INSERT_ROW = null;
	Scanner scanner = new Scanner(System.in);
	String tableName = null;

    private App() {
    }

    /**
     * Says hello to the world.
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
     
        String CreateSql = null;
        // Scanner scanner = new Scanner(System.in);
        // System.out.println("nhap ten db");
        // String database = scanner.nextLine();
        // System.out.println("nhap ten table");
        // String table = scanner.nextLine();
        // String createTable = "Create Table" +table+"(id int primary key, name
        // varchar, address text) ";

      
    }

    public LinkedHashMap<String, String> inputColums(LinkedHashMap<String, String> input) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("nhập tên cột và kiểu dữ liệu, cách nhau bằng 1 cấu ','");
        String temp = scanner.nextLine();
        String[] colums = temp.split(",");
        if (checkType(colums[1].trim())) {
            input.put(colums[0].trim(), colums[1].trim());
            System.out.println("có muốn thêm cột không?(Y/N)");
            if (scanner.next().equalsIgnoreCase("y")) {
                inputColums(input);
            }
        } else {
            System.out.println("nhập sai kiểu dữ liệu, nhập lại");
            inputColums(input);
        }
        return input;
    }

    public boolean checkType(String columName) {
        return true;
    }

    public void createTable() {
		System.out.println("nhap ten bang");
		tableName = scanner.nextLine();
		System.out.println();
		ArrayList<String[]> colums = new ArrayList<String[]>();
		columsInput(colums);
		String create = "Create TABLE " + tableName + " (";
		for (String[] colum : colums) {
			create += "" + colum[0] + " " + colum[1] + ",";
		}
		create = create.substring(0, create.length() - 1) + " )";
		try {
			c = DriverManager.getConnection(url, user,password);
			stmt = c.createStatement();
			stmt.execute(create);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public ArrayList<String[]> columsInput(ArrayList<String[]> input) {
		System.out.println("nhap ten cot, kieu du lieu");
		String temp[] = scanner.nextLine().split(",");
		String name = temp[0];
		String type = temp[1];
		if (checkInputType(type)) {
			input.add(new String[] { name, type });
		} else {
			System.out.println("nhap sai, nhap lai");
			columsInput(input);
		}
		return input;
	}

	public ArrayList<String[]> rowInput(ArrayList<String[]> rowInput) {
		System.out.println("nha du lieu, cach nhau dau ','");
		String temp[] = scanner.nextLine().split(",");
		
		if (checkInputType("")){
			rowInput.add(temp);
		} else {
			System.out.println("nhap sai, nhap lai");
			rowInput(rowInput);
		}
		System.out.println("nhap tiep? (Y/N)");
		if (scanner.next().equalsIgnoreCase("y")) {
			rowInput(rowInput);
		}
		return rowInput;
	}
	
	

	public void insertRow(ArrayList<String[]> input) {
		try {
			INSERT_ROW = "INSERT INTO "+tableName +"  (id, name, email, country, password) VALUES " +
        " (?, ?, ?, ?, ?);";
			c= DriverManager.getConnection(url, user,password);
			preparedStatement = c.prepareStatement(INSERT_ROW);
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	public boolean check(String input, String type){
		
		return false;
	}

	public boolean checkInputType(String type) {
		try {
			JDBCType.valueOf(type);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

}
