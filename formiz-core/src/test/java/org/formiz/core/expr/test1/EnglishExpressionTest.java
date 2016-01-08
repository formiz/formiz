package org.formiz.core.expr.test1;

import java.util.HashMap;
import java.util.Map;

/**
 *  Copyright SCN Guichet Entreprises, Capgemini and contributors (2014-2016)
 *
 * This software is a computer program whose purpose is to [describe
 * functionalities and technical features of your software].
 *
 * This software is governed by the CeCILL  license under French law and
 * abiding by the rules of distribution of free software.  You can  use,
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability.
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or
 * data to be ensured and,  more generally, to use and operate it in the
 * same conditions as regards security.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 */

import org.formiz.core.expr.IContext;
import org.formiz.core.expr.IExpression;
import org.formiz.core.expr.impl.ContextBuilder;
import org.formiz.core.expr.impl.ElContext;
import org.formiz.core.expr.impl.ElExpressionParser;
import org.formiz.core.expr.impl.EnglishExpressionParser;
import org.formiz.core.expr.impl.ParseException;
import org.formiz.core.expr.test1.model.Address;
import org.formiz.core.expr.test1.model.Functions;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnglishExpressionTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(EnglishExpressionTest.class);

	EnglishExpressionParser parser = new EnglishExpressionParser(new ElExpressionParser());

	@Test
	public void companyValidTest() {
		String exprText = "(company is FALSE )  OR (company is TRUE AND companyName is not empty)";
		IExpression e = parser.parseExpression(exprText);

		// Context
		Address a = new Address();
		IContext context = new ElContext();
		context.setObject(a, null);

		a.setCompany(false);
		Assert.assertTrue((Boolean) e.getValue(context));

		a.setCompany(true);
		Assert.assertFalse((Boolean) e.getValue(context));

		a.setCompany(true);
		a.setCompanyName("Formiz");
		Assert.assertTrue((Boolean) e.getValue(context));

	}

	@Test
	public void egalTest() {
		String exprText = "companyName is equal to \"formiz\"";
		String exprText2 = "companyName is not equal to \"formiz\"";
		IExpression e1 = parser.parseExpression(exprText);
		IExpression e2 = parser.parseExpression(exprText2);

		// Context
		Address a = new Address();
		IContext context = new ElContext();
		context.setObject(a, null);

		a.setCompanyName("formiz");
		Assert.assertTrue((Boolean) e1.getValue(context));
		Assert.assertFalse((Boolean) e2.getValue(context));

		a.setCompanyName("formiz2");
		Assert.assertFalse((Boolean) e1.getValue(context));
		Assert.assertTrue((Boolean) e2.getValue(context));

	}

	@Test
	public void functionTest() throws ClassNotFoundException {
		String exprText = "#sum( 1, 2) is equal to 3";
		IExpression e1 = parser.parseExpression(exprText);
		IContext context = new ElContext();

		ContextBuilder cb = new ContextBuilder();
		Map<String, String> functions = new HashMap<String, String>();
		functions.put("sum", Functions.class.getName() + ".sum");
		cb.setFunctions(functions);
		cb.init();
		cb.build(context);

		Assert.assertTrue((Boolean) e1.getValue(context));
	}

	@Test
	public void inferieurSuperieurTest() {

		String exprText = "#this is less than 10";
		String exprText2 = "#this is greater than 10";
		IExpression e1 = parser.parseExpression(exprText);
		IExpression e2 = parser.parseExpression(exprText2);

		// Context
		IContext context = new ElContext();

		context.setObject(new Integer(5), null);
		Assert.assertTrue((Boolean) e1.getValue(context));
		Assert.assertFalse((Boolean) e2.getValue(context));

		context.setObject(new Integer(15), null);
		Assert.assertFalse((Boolean) e1.getValue(context));
		Assert.assertTrue((Boolean) e2.getValue(context));

	}

	@Test
	public void parsingTest() {
		String exprText = "companyName sd is equal to \"formiz\" ";
		try {
			parser.parseExpression(exprText);
			Assert.fail();
		} catch (ParseException e) {
			LOGGER.info("", e);
			Assert.assertEquals(exprText, e.getOriginalExpression());
			Assert.assertNotEquals(exprText, e.getExpression());
		}

	}

	@Test
	public void variableTest() throws ClassNotFoundException {
		String exprText = "#id is equal to 3";
		IExpression e1 = parser.parseExpression(exprText);
		IContext context = new ElContext();

		ContextBuilder cb = new ContextBuilder();
		Map<String, String> variables = new HashMap<String, String>();
		variables.put("id", Functions.class.getName() + ".id");
		cb.setVariableProviders(variables);
		cb.init();
		cb.build(context);

		Assert.assertTrue((Boolean) e1.getValue(context));
	}

	@Test
	public void videTest() {
		String exprText = "companyName is empty";
		String exprText2 = "companyName is not empty";
		IExpression e1 = parser.parseExpression(exprText);
		IExpression e2 = parser.parseExpression(exprText2);

		// Context
		Address a = new Address();
		IContext context = new ElContext();
		context.setObject(a, null);

		a.setCompanyName(null);
		Assert.assertTrue((Boolean) e1.getValue(context));
		Assert.assertFalse((Boolean) e2.getValue(context));

		a.setCompanyName("Formiz");
		Assert.assertFalse((Boolean) e1.getValue(context));
		Assert.assertTrue((Boolean) e2.getValue(context));

	}

	@Test
	public void vraiTest() {
		String exprText = "company is TRUE";
		IExpression e = parser.parseExpression(exprText);

		// Context
		Address a = new Address();
		IContext context = new ElContext();
		context.setObject(a, null);

		a.setCompany(false);
		Assert.assertFalse((Boolean) e.getValue(context));

		a.setCompany(true);
		Assert.assertTrue((Boolean) e.getValue(context));

		// Test getText
		Assert.assertEquals(exprText, e.getText());
	}

}
