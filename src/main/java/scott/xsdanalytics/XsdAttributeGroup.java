package scott.xsdanalytics;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import scot.xsdanalytics.exception.XsdDefinitionException;

import static scott.xsdanalytics.SchemaElements.*;

import java.util.Collections;
import java.util.List;

/**
 * <attributeGroup
 * id=ID
 * name=NCName
 * ref=QName
 * any attributes
 * >
 *
 * (annotation?),((attribute|attributeGroup)*,anyAttribute?))
 *
 *</attributeGroup>
 */
public class XsdAttributeGroup implements XsdNode {
    private final XsdNode parent;
    private final Element domElement;

    public XsdAttributeGroup(XsdNode parent, Element domElement) {
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
        return Collections.emptyList();
    }

    @Override
    public List<XsdAttribute> getAttributes() throws XsdDefinitionException {
        XsdAttributeGroup ref = resolveReference();
        if (ref != null) {
            return ref.getAttributes();
        }
        return Common.getAttributes(this, domElement, ATTRIBUTE, ATTRIBUTEGROUP);
    }

    /**
     * If we are an attribute group reference, then return the resolved attribute group
     * @return
     */
    private XsdAttributeGroup resolveReference() throws XsdDefinitionException {
        String ref = domElement.getAttribute("ref");
        if (ref.isEmpty()) {
            return null;
        }
        return getXsdDefinition().findAttributeGroup(new QualifiedName(getXsdDefinition(), ref));
    }

    @Override
    public XsdNode getParent() {
        return parent;
    }
}
