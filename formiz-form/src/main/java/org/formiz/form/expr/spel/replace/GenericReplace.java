/**
 *  Copyright SCN Guichet Entreprises, Capgemini and contributors (2014-2016)
 * <p>
 * This software is a computer program whose purpose is to [describe
 * functionalities and technical features of your software].
 * <p>
 * This software is governed by the CeCILL  license under French law and
 * abiding by the rules of distribution of free software.  You can  use,
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 * <p>
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability.
 * <p>
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
 * <p>
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 */

package org.formiz.form.expr.spel.replace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Remplace des occurences dans une chaine de caractère.
 * <p>
 * Conserve la liste des éléments remplacés peut être récupérée avec
 * {@link #getMatchedItems()}.
 *
 * <p>
 * Il est possible de surcharger la méthode {@link #perform(String)} pour
 * retraiter les éléments avant ajout dans la liste des références.
 * <p>
 *
 * @author Nicolas Richeton
 *
 */
public class GenericReplace {

	/**
	 * Map indiquant le remplacement pour une chaine.
	 */
	private Map<String, String> items;

	/**
	 * Chaine pour lesquelles il a été effectué un remplacement.
	 */
	private List<String> matchedItems;

	/**
	 * Le pattern à remplacer.
	 */
	private final Pattern pattern;

	/**
	 * Chaine qui a remplacer le pattern.
	 */
	private List<String> replacedItems;

	/**
	 * Chaine de remplacement
	 */
	private final String replacement;

	/**
	 * Constructeur de classe.
	 * 
	 * @param pattern
	 *            pattern qui doit être remplacé
	 * @param replacement
	 *            la chaine de carractère qui doit remplacer le pattern
	 */
	public GenericReplace(Pattern pattern, String replacement) {
		this.pattern = pattern;
		this.replacement = replacement;
	}

	/**
	 * Getter de l'attribut items.
	 * 
	 * @return la valeur de items
	 */
	public Map<String, String> getItems() {
		return items;
	}

	/**
	 * Getter de l'attribut matchedItems.
	 * 
	 * @return la valeur de matchedItems
	 */
	public List<String> getMatchedItems() {
		return matchedItems;
	}

	/**
	 * Getter de l'attribut replacedItems.
	 * 
	 * @return la valeur de replacedItems
	 */
	public List<String> getReplacedItems() {
		return replacedItems;
	}

	/**
	 * Execute le remplacement.
	 * 
	 * @param expr
	 *            expression a transformer
	 * @return la nouvelle expression
	 */
	public String perform(String expr) {
		if (expr == null) {
			return null;
		}

		matchedItems = new ArrayList<>();
		replacedItems = new ArrayList<>();
		items = new HashMap<>();

		StringBuffer newValuesBuffer = new StringBuffer();
		Matcher m = pattern.matcher(expr);
		while (m.find()) {
			String grp = m.group(1);
			String newGrp = replacement.replace("$1", grp);
			matchedItems.add(grp);
			replacedItems.add(newGrp);
			items.put(grp, newGrp);
			m.appendReplacement(newValuesBuffer, replacement);
		}
		m.appendTail(newValuesBuffer);
		return newValuesBuffer.toString();
	}
}
