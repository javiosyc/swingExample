import static org.junit.Assert.fail;

import java.sql.SQLException;

import model.AgeCategory;
import model.Database;
import model.EmploymentCategory;
import model.Gender;
import model.Person;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestDatabase {
	private static Database db;

	@BeforeClass
	public static void connectDb() {
		db = new Database();
		try {
			db.connect();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@AfterClass
	public static void disconnectDB() {
		db.disconnect();
	}

	@Test
	public void addPersonTest() {

		db.addPerson(new Person("Joe", "builder", AgeCategory.child,
				EmploymentCategory.employed, "777", true, Gender.female));
		db.addPerson(new Person("Sue", "artist", AgeCategory.senior,
				EmploymentCategory.unemployed, null, false, Gender.female));

		try {
			db.save();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void loadTest() {
		try {
			db.load();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
