package net.atos.iam.utils.autodoc.mswordmanagement.constantes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum PrestataireManager {

	AMRI_YASSINE("Yassine", "AMRI"), 
	EDDAALOUS_YASSINE("Yassine", "EDDAALOUS"),
	BENYAHIYA_NAOUFAL("Naoufal", "BENYAHIYA");

	private final String firstName;
	private final String lastName;

	PrestataireManager(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public static String[] getEnumDescAsArray() {
		return (String[]) Arrays.stream(PrestataireManager.values()).map(element -> {
			return element.firstName + " " + element.lastName;
		}).toArray();
	}

	public static List<String> getEnumDescAsList() {
		return Arrays.stream(PrestataireManager.values()).map(element -> {
			return element.firstName + " " + element.lastName;
		}).collect(Collectors.toList());
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

}
