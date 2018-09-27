package scott.xsdanalytics;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import scot.xsdanalytics.exception.InvalidXsdException;
import scot.xsdanalytics.exception.XsdDefinitionException;

import static scott.xsdanalytics.SchemaElements.*;

import java.util.Collections;
import java.util.List;

/**
 * <restriction
 * id=ID
 * base=QName
 * any attributes
 * >
 *
 *Content for simpleType:
 *(annotation?,(simpleType?,(minExclusive|minInclusive|
 *maxExclusive|maxInclusive|totalDigits|fractionDigits|
 *length|minLength|maxLength|enumeration|whiteSpace|pattern)*))
 *
 *Content for simpleContent:
 *(annotation?,(simpleType?,(minExclusive |minInclusive|
 *maxExclusive|maxInclusive|totalDigits|fractionDigits|
 *length|minLength|maxLength|enumeration|whiteSpace|pattern)*)?,
 *((attribute|attributeGroup)*,anyAttribute?))
 *
 *Content for complexContent:
 *(annotation?,(group|all|choice|sequence)?,
 *((attribute|attributeGroup)*,anyAttribute?))
 * </restriction>
 */
public final class XsdRestriction implements XsdNode {
    private final XsdNode parent;
    private final Element domElement;

    public XsdRestriction(XsdNode parent, Element domElement) {
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
        return Common.getDocumentation(domElement);
    }

    @Override
    public List<XsdAttribute> getAttributes() throws XsdDefinitionException {
        if (parent instanceof XsdComplexContent || parent instanceof XsdSimpleContent) {
            //only attribute and attribute group are relevant for attributes
            return Common.getAttributes(this, domElement, ATTRIBUTE, ATTRIBUTEGROUP);
        }
        return Collections.emptyList();
    }


    /**
     * A restiction defines the child target elements explicitly
     * @return
     * @throws InvalidXsdException
     */
    public List<XsdElement> getChildTargetElements() throws XsdDefinitionException {
        if (parent instanceof XsdComplexContent) {
            return Common.getChildElementsFromOneOf(this, domElement, GROUP, ALL, CHOICE, SEQUENCE);
        }
        return Collections.emptyList();
    }

    @Override
    public XsdNode getParent() {
        return parent;
    }

}
