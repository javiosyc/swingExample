package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Database {
	private List<Person> people;

	private Connection con;

	private int port;
	private String user;
	private String password;

	public Database() {
		people = new LinkedList<Person>();
	}

	public void configure(int port, String user, String password)
			throws Exception {
		this.port = port;
		this.user = user;
		this.password = password;

		if (con != null) {
			disconnect();
			connect();
		}
	}

	public void connect() throws Exception {

		if (con != null)
			return;

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new Exception("Driver not found");
		}

		String connectionUrl = "jdbc:mysql://localhost:3306/swingtest";
		con = DriverManager.getConnection(connectionUrl, "root", "password");
	}

	public void disconnect() {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				System.out.println("Can't close connection.");
			}
		}
	}

	public void save() throws SQLException {

		String checkSql = "select count(1) as count from people where id=?";

		PreparedStatement checkStmt = con.prepareStatement(checkSql);

		String insertSql = "insert into people (id,name,age,employment_status,tax_id,us_citizen,gender,occupation) values (?,?,?,?,?,?,?,?)";
		PreparedStatement insertStatement = con.prepareStatement(insertSql);

		String updateSql = "update people set name=?, age=?, employment_status=?, tax_id=?, us_citizen=?, gender=?, occupation=? where id=?";
		PreparedStatement updateStatemnet = con.prepareStatement(updateSql);

		for (Person person : people) {
			int id = person.getId();

			String name = person.getName();
			String occupation = person.getOccupation();
			AgeCategory age = person.getAgeCategory();
			EmploymentCategory emp = person.getEmpCategory();
			String tax = person.getTaxId();
			boolean isUs = person.isUsCitizen();
			Gender gender = person.getGender();

			checkStmt.setInt(1, id);

			ResultSet checkResult = checkStmt.executeQuery();

			checkResult.next();

			int count = checkResult.getInt(1);

			if (count == 0) {
				System.out.println("Inserting person with ID " + id);

				int col = 1;
				insertStatement.setInt(col++, id);
				insertStatement.setString(col++, name);
				insertStatement.setString(col++, age.name());
				insertStatement.setString(col++, emp.name());
				insertStatement.setString(col++, tax);
				insertStatement.setBoolean(col++, isUs);
				insertStatement.setString(col++, gender.name());
				insertStatement.setString(col++, occupation);

				insertStatement.executeUpdate();

			} else {
				int col = 1;

				updateStatemnet.setString(col++, name);
				updateStatemnet.setString(col++, age.name());
				updateStatemnet.setString(col++, emp.name());
				updateStatemnet.setString(col++, tax);
				updateStatemnet.setBoolean(col++, isUs);
				updateStatemnet.setString(col++, gender.name());
				updateStatemnet.setString(col++, occupation);
				updateStatemnet.setInt(col++, id);

				updateStatemnet.executeUpdate();

			}

			System.out.println("Count for person id :" + id + " is " + count);

		}

		updateStatemnet.close();
		insertStatement.close();
		checkStmt.close();
	}

	public void load() throws SQLException {
		people.clear();

		String sql = "select id,name,age,employment_status,tax_id,us_citizen,gender,occupation from people order by name";
		Statement selectStatement = con.createStatement();

		ResultSet results = selectStatement.executeQuery(sql);

		while (results.next()) {
			int id = results.getInt("id");
			String name = results.getString("name");
			String age = results.getString("age");
			String emp = results.getString("employment_status");
			String taxId = results.getString("tax_id");
			boolean isUs = results.getBoolean("us_citizen");
			String gender = results.getString("gender");
			String occ = results.getString("occupation");

			Person person = new Person(id, name, occ, AgeCategory.valueOf(age),
					EmploymentCategory.valueOf(emp), taxId, isUs,
					Gender.valueOf(gender));

			people.add(person);
		}

		results.close();
		selectStatement.close();
	}

	public void addPerson(Person person) {
		people.add(person);
	}

	public List<Person> getPeople() {
		return Collections.unmodifiableList(people);
	}

	public void removePerson(int index) {
		people.remove(index);
	}

	public void saveToFile(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		Person[] persons = people.toArray(new Person[people.size()]);

		oos.writeObject(persons);
		oos.close();
	}

	public void loadFromFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);

		try {
			Person[] persons = (Person[]) ois.readObject();

			people.clear();

			people.addAll(Arrays.asList(persons));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		ois.close();
	}

}
