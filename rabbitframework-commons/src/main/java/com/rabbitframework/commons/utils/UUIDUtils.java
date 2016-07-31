package com.rabbitframework.commons.utils;

import java.util.UUID;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.NoArgGenerator;

public class UUIDUtils {
	public static String DEFAULT_UUID36 = "00000000-0000-0000-0000-000000000000";
	public static String DEFAULT_UUID32 = "00000000000000000000000000000000";
	private static NoArgGenerator timeGenerator;
	private static NoArgGenerator randomGenerator;

	static {
		ensureGeneratorInitialized();
	}

	private static void ensureGeneratorInitialized() {
		if (timeGenerator == null) {
			synchronized (UUIDUtils.class) {
				if (timeGenerator == null) {
					timeGenerator = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
				}
			}
		}
		if (randomGenerator == null) {
			synchronized (com.fasterxml.uuid.UUIDGenerator.class) {
				if (randomGenerator == null) {
					randomGenerator = Generators.randomBasedGenerator();
				}
			}
		}
	}

	public static UUID getRandomUUID() {
		return randomGenerator.generate();
	}

	public static String getRandomUUID36() {
		return getRandomUUID().toString();
	}

	public static String getRandomUUID32() {
		return getUUID32(getRandomUUID36());
	}

	public static UUID getTimeUUID() {
		return timeGenerator.generate();
	}

	public static String getTimeUUID36() {
		return getTimeUUID().toString();
	}

	public static String getTimeUUID32() {
		return getUUID32(getTimeUUID36());
	}

	private static String getUUID32(String generator) {
		return generator.replace("-", "");
	}
}
