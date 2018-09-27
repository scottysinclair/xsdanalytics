package scott.xsdanalytics;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import scot.xsdanalytics.exception.XsdDefinitionException;

import static scott.xsdanalytics.SchemaElements.*;

import java.util.Collections;
import java.util.List;

/**
 *
 * The all element specifies that the child elements can appear in any order and that each child element can occur zero or one time.
 *
 * Parent elements: group, complexType, restriction (both simpleContent and complexContent), extension (both simpleContent and complexContent)
 *
 * <all
 * id=ID
 * maxOccurs=1
 * minOccurs=0|1
 * any attributes
 * >
 *
 * (annotation?,element*)
 *
 * </all>
 */
public class XsdAll implements XsdNode {

    private final XsdNode parent;
    private final Element domElement;

    public XsdAll(XsdNode parent, Element domElement) {
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
    public List<XsdAttribute> getAttributes() throws XsdDefinitionException {
        return Collections.emptyList();
    }

    @Override
    public List<XsdElement> getChildTargetElements() throws XsdDefinitionException {
        //we say across optional nodes since there can be multiple elements inside the all definition
        return Common.getChildElementsAcrossNodes(this, domElement, ELEMENT);
    }

    @Override
    public XsdNode getParent() {
        return parent;
    }
}
