/*
 * Copyright (c) 2017, 2018, VP-BYTE and/or its affiliates. All rights reserved.
 * VP-BYTE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jmeplay.core.utils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jmeplay.core.utils.SettingsLoader;

/**
 * Store and load settings tests
 *
 * @author Vladimir Petrenko (vp-byte)
 */
public class SettingsLoaderTest {

	private String settingsfile = "testsettings.xml";
	private SettingsLoader settingsLoader;

	@Before
	public void init() {
		settingsLoader = new SettingsLoader();
		settingsLoader.loadSettings(settingsfile);
	}

	@After
	public void destroy() {
		settingsLoader.deleteSettings();
	}

	@Test
	public void testSettingsLoader() {
		Assert.assertNotNull(settingsLoader);
	}

	@Test
	public void testWriteSettings() {
		settingsLoader.writeSettings();
	}

	@Test
	public void testValueString() {
		Assert.assertEquals(settingsLoader.value("test", "DEFAULT"), "DEFAULT");
		settingsLoader.setValue("test", "VALUE");
		Assert.assertEquals(settingsLoader.value("test", "DEFAULT"), "VALUE");
		settingsLoader.writeSettings();

		settingsLoader = null;

		settingsLoader = new SettingsLoader();
		settingsLoader.loadSettings(settingsfile);
		Assert.assertEquals(settingsLoader.value("test", "DEFAULT"), "VALUE");
	}

	@Test
	public void testValueBoolean() {
		Assert.assertEquals(settingsLoader.value("test", true), true);
		settingsLoader.setValue("test", true);
		Assert.assertEquals(settingsLoader.value("test", false), true);
		settingsLoader.writeSettings();

		settingsLoader = null;

		settingsLoader = new SettingsLoader();
		settingsLoader.loadSettings(settingsfile);
		Assert.assertEquals(settingsLoader.value("test", false), true);
	}

	@Test
	public void testValueInteger() {
		Assert.assertEquals(settingsLoader.value("test", 1000).longValue(), 1000);
		settingsLoader.setValue("test", 2000);
		Assert.assertEquals(settingsLoader.value("test", 1000).longValue(), 2000);
		settingsLoader.writeSettings();

		settingsLoader = null;

		settingsLoader = new SettingsLoader();
		settingsLoader.loadSettings(settingsfile);
		Assert.assertEquals(settingsLoader.value("test", 1000).longValue(), 2000);
	}

	@Test
	public void testValueLong() {
		Assert.assertEquals(settingsLoader.value("test", 1000L).longValue(), 1000L);
		settingsLoader.setValue("test", 2000L);
		Assert.assertEquals(settingsLoader.value("test", 1000L).longValue(), 2000L);
		settingsLoader.writeSettings();

		settingsLoader = null;

		settingsLoader = new SettingsLoader();
		settingsLoader.loadSettings(settingsfile);
		Assert.assertEquals(settingsLoader.value("test", 1000L).longValue(), 2000L);
	}

	@Test
	public void testValueFloat() {
		Assert.assertEquals(settingsLoader.value("test", 1.1f).floatValue(), 1.1f, 0.1f);
		settingsLoader.setValue("test", 2.1);
		Assert.assertEquals(settingsLoader.value("test", 1.1f).floatValue(), 2.1f, 0.1f);
		settingsLoader.writeSettings();

		settingsLoader = null;

		settingsLoader = new SettingsLoader();
		settingsLoader.loadSettings(settingsfile);
		Assert.assertEquals(settingsLoader.value("test", 1.1f).floatValue(), 2.1f, 0.1f);
	}

	@Test
	public void testValueDouble() {
		Assert.assertEquals(settingsLoader.value("test", 1.1).doubleValue(), 1.1, 0.1);
		settingsLoader.setValue("test", 2.1);
		Assert.assertEquals(settingsLoader.value("test", 1.1).doubleValue(), 2.1, 0.1);
		settingsLoader.writeSettings();

		settingsLoader = null;

		settingsLoader = new SettingsLoader();
		settingsLoader.loadSettings(settingsfile);
		Assert.assertEquals(settingsLoader.value("test", 1.1).doubleValue(), 2.1, 0.1);
	}

	@Test
	public void testRemoveValue() {
		settingsLoader.setValue("test", "VALUE");
		Assert.assertEquals(settingsLoader.value("test", "DEFAULT"), "VALUE");
		settingsLoader.removeValue("test");
		Assert.assertEquals(settingsLoader.value("test", "DEFAULT"), "DEFAULT");
	}
}
