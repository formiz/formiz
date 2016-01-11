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

package org.formiz.input.fods;

import org.xmlfield.annotations.FieldXPath;
import org.xmlfield.annotations.Namespaces;
import org.xmlfield.annotations.ResourceXPath;

/**
 * style:style
 *
 * @author nricheto
 *
 */
@Namespaces({ "xmlns:office=urn:oasis:names:tc:opendocument:xmlns:office:1.0",
		"xmlns:style=urn:oasis:names:tc:opendocument:xmlns:style:1.0",
		"xmlns:text=urn:oasis:names:tc:opendocument:xmlns:text:1.0",
		"xmlns:table=urn:oasis:names:tc:opendocument:xmlns:table:1.0",
		"xmlns:draw=urn:oasis:names:tc:opendocument:xmlns:drawing:1.0",
		"xmlns:fo=urn:oasis:names:tc:opendocument:xmlns:xsl-fo-compatible:1.0",
		"xmlns:xlink=http://www.w3.org/1999/xlink", "xmlns:dc=http://purl.org/dc/elements/1.1/",
		"xmlns:meta=urn:oasis:names:tc:opendocument:xmlns:meta:1.0",
		"xmlns:number=urn:oasis:names:tc:opendocument:xmlns:datastyle:1.0",
		"xmlns:presentation=urn:oasis:names:tc:opendocument:xmlns:presentation:1.0",
		"xmlns:svg=urn:oasis:names:tc:opendocument:xmlns:svg-compatible:1.0",
		"xmlns:chart=urn:oasis:names:tc:opendocument:xmlns:chart:1.0",
		"xmlns:dr3d=urn:oasis:names:tc:opendocument:xmlns:dr3d:1.0", "xmlns:math=http://www.w3.org/1998/Math/MathML",
		"xmlns:form=urn:oasis:names:tc:opendocument:xmlns:form:1.0",
		"xmlns:script=urn:oasis:names:tc:opendocument:xmlns:script:1.0",
		"xmlns:config=urn:oasis:names:tc:opendocument:xmlns:config:1.0", "xmlns:ooo=http://openoffice.org/2004/office",
		"xmlns:ooow=http://openoffice.org/2004/writer", "xmlns:oooc=http://openoffice.org/2004/calc",
		"xmlns:dom=http://www.w3.org/2001/xml-events", "xmlns:xforms=http://www.w3.org/2002/xforms",
		"xmlns:xsd=http://www.w3.org/2001/XMLSchema", "xmlns:xsi=http://www.w3.org/2001/XMLSchema-instance",
		"xmlns:rpt=http://openoffice.org/2005/report", "xmlns:of=urn:oasis:names:tc:opendocument:xmlns:of:1.2",
		"xmlns:xhtml=http://www.w3.org/1999/xhtml", "xmlns:grddl=http://www.w3.org/2003/g/data-view#",
		"xmlns:tableooo=http://openoffice.org/2009/table", "xmlns:drawooo=http://openoffice.org/2010/draw",
		"xmlns:calcext=urn:org:documentfoundation:names:experimental:calc:xmlns:calcext:1.0",
		"xmlns:loext=urn:org:documentfoundation:names:experimental:office:xmlns:loext:1.0",
		"xmlns:field=urn:openoffice:names:experimental:ooo-ms-interop:xmlns:field:1.0",
		"xmlns:formx=urn:openoffice:names:experimental:ooxml-odf-interop:xmlns:form:1.0",
		"xmlns:css3t=http://www.w3.org/TR/css3-text/" })
@ResourceXPath("style:style")
public interface AutomaticStyle {

	@FieldXPath("@style:master-page-name")
	String getMasterPageName();

	@FieldXPath("@style:name")
	String getName();

	@FieldXPath("@style:parent-style-name")
	String getParentName();

}
