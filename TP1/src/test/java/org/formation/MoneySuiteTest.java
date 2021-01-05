package org.formation;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.junit.runner.RunWith;


@RunWith(JUnitPlatform.class)
@SelectPackages("org.formation")
@SuiteDisplayName("Money Suite Demo")
public class MoneySuiteTest {
}
