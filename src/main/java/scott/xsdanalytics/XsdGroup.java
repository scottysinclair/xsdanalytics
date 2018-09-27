package scott.xsdanalytics;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import scot.xsdanalytics.exception.XsdDefinitionException;

import static scott.xsdanalytics.SchemaElements.*;

import java.util.Collections;
import java.util.List;

/**
 *
 * The group element is used to define a group of elements to be used in complex type definitions
 *
 * This can actually be a group definition or a group reference depending on whether the name or ref attribute is used
 *
 * Parent elements: schema, choice, sequence, complexType, restriction (both simpleContent and complexContent), extension (both simpleContent and complexContent)
 *
 * <group
 * id=ID
 * name=NCName
 * ref=QName
 * maxOccurs=nonNegativeInteger|unbounded
 * minOccurs=nonNegativeInteger
 * any attributes
 * >
 *
 * (annotation?,(all|choice|sequence)?)
 *
 * </group>
 */
public class XsdGroup implements XsdNode {

    private final XsdNode parent;
    private final Element domElement;

    public XsdGroup(XsdNode parent, Element domElement) {
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
        String groupRef = domElement.getAttribute("ref");
        if (groupRef.isEmpty()) {
            //we are a group definition
            return Common.getChildElementsFromOneOf(this, domElement, ALL, CHOICE, SEQUENCE);
        }
        else {
            //lookup the ref
            XsdGroup xsdGroup = getXsdDefinition().findGroup(new QualifiedName(getXsdDefinition(), groupRef, true));
            return xsdGroup.getChildTargetElements();
        }
    }

    @Override
    public List<XsdAttribute> getAttributes() throws XsdDefinitionException {
        return Collections.emptyList();
    }

    @Override
    public XsdNode getParent() {
        return parent;
    }

    public XsdElement getElement(QualifiedName qualifiedName) throws XsdDefinitionException {
        for (XsdElement element: getChildTargetElements()) {
            if (element.matches(qualifiedName)) {
                return element;
            }
        }
        return null;
    }
}
