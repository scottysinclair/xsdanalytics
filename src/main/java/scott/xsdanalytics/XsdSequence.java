package scott.xsdanalytics;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import scot.xsdanalytics.exception.XsdDefinitionException;

import static scott.xsdanalytics.SchemaElements.*;

import java.util.Collections;
import java.util.List;

/**
 * An Xsd sequence element
 *
 * <sequence
 * id=ID
 * maxOccurs=nonNegativeInteger|unbounded
 * minOccurs=nonNegativeInteger
 * any attributes
 * >
 *
 *(annotation?,(element|group|choice|sequence|any)*)
 *
 *</sequence>
 */
public class XsdSequence implements XsdNode {

    private final XsdNode parent;
    private final Element domElement;

    public XsdSequence(XsdNode parent, Element domElement) {
        this.parent = parent;
        this.domElement = domElement;
    }

    @Override
    public Node getDomNode() {
      return domElement;
    }

    @Override
    public XsdDefinition getXsdDefinition() {
        return parent.getXsdDefinition();
    }

    @Override
    public List<XsdDocumentation> getDocumentation() throws XsdDefinitionException {
        return Collections.emptyList();
    }

    @Override
    public List<XsdElement> getChildTargetElements() throws XsdDefinitionException {
        //(element|group|choice|sequence|any)* can contains all of the following elements at once
        return Common.getChildElementsAcrossNodes(this, domElement, ELEMENT, GROUP, CHOICE, SEQUENCE, ANY);
    }

    @Override
    public List<XsdAttribute> getAttributes() throws XsdDefinitionException {
        return Collections.emptyList();
    }

    @Override
    public XsdNode getParent() {
        return parent;
    }

    /**
     * @return the XsdAny child if it exists, null otherwise.
     */
    public XsdAny getAny() throws XsdDefinitionException {
        return (XsdAny) Common.getXsdNode(this, domElement, ANY);
    }
}
