package org.formiz.core.expr.test1;

import org.formiz.core.expr.impl.ExpressionParserUtil;
import org.formiz.core.expr.impl.FrenchExpressionParser;
import org.formiz.core.expr.spel.ElExpressionParser;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExpressionParserUtilTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExpressionParserUtilTest.class);

	final FrenchExpressionParser parser = new FrenchExpressionParser(new ElExpressionParser());

	@Test
	public void parsingTest() {
		String exprText = "companyName sd est égal à \"formiz\" ";
		try {
			ExpressionParserUtil.parse("myid", "mygroup", parser, exprText);
			Assert.fail();
		} catch (IllegalArgumentException e) {
			LOGGER.info("", e);
			Assert.assertTrue(e.getMessage().contains("myid"));
			Assert.assertTrue(e.getMessage().contains("mygroup"));
		}

	}

}
