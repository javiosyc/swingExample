package controller;

import gui.FormEvent;

import java.io.File;
import java.io.IOException;
import java.util.List;

import model.AgeCategory;
import model.Database;
import model.EmploymentCategory;
import model.Gender;
import model.Person;

public class Controller {
	Database db = new Database();

	public List<Person> getPeople() {
		return db.getPeople();
	}

	public void addPersion(FormEvent ev) {
		String name = ev.getName();
		String occupation = ev.getOccupation();
		int ageCatId = ev.getAgeCategory();
		String empCat = ev.getEmpCategory();
		boolean isUs = ev.isUsCitizen();
		String taxId = ev.getTaxId();
		String gender = ev.getGender();

		AgeCategory ageCategory = null;

		switch (ageCatId) {
		case 0:
			ageCategory = AgeCategory.child;
			break;
		case 1:
			ageCategory = AgeCategory.adult;
			break;
		case 2:
			ageCategory = AgeCategory.senior;
			break;
		}

		EmploymentCategory employmentCategory;

		if ("employed".equals(empCat)) {
			employmentCategory = EmploymentCategory.employed;
		} else if ("self-employed".equals(empCat)) {
			employmentCategory = EmploymentCategory.selfEmployed;
		} else if ("unemployed".equals(empCat)) {
			employmentCategory = EmploymentCategory.unemployed;
		} else {
			employmentCategory = EmploymentCategory.other;
			System.err.println(empCat);
		}

		Gender genderCat;
		if ("male".equals(gender)) {
			genderCat = Gender.male;
		} else {
			genderCat = Gender.female;
		}

		Person person = new Person(name, occupation, ageCategory,
				employmentCategory, taxId, isUs, genderCat);

		db.addPerson(person);
	}

	public void saveToFile(File file) throws IOException {
		db.saveToFile(file);
	}

	public void loadFromFile(File file) throws IOException {
		db.loadFromFile(file);
	}

	public void removePerson(int index) {
		db.removePerson(index);
	}
}
