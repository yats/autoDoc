package net.atos.iam.utils.autodoc.mswordmanagement.constantes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MTProjectManagers {

	SBAI_NAJLAA("Najla", "SBAI"), AMRANI_IDRISSI_FADOUA("Fadoua", "AMRANI IDRISSI"),
	ALLAM_AAOUATIF("Aaouatif", "ALLAM");

	private final String firstName;
	private final String lastName;

	MTProjectManagers(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public static String[] getEnumDescAsArray() {
		return (String[]) Arrays.stream(MTProjectManagers.values()).map(element -> {
			return element.firstName + " " + element.lastName;
		}).toArray();
	}

	public static List<String> getEnumDescAsList() {
		return Arrays.stream(MTProjectManagers.values()).map(element -> {
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
