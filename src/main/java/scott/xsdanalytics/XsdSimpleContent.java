package scott.xsdanalytics;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import scot.xsdanalytics.exception.XsdDefinitionException;

import static scott.xsdanalytics.SchemaElements.*;

import java.util.Collections;
import java.util.List;

/**
 *
 * The simpleContent element contains extensions or restrictions on a text-only complex type or on a simple type as content and contains no elements.
 *
 * Parent elements: complexType
 *
 * <simpleContent
 * id=ID
 * any attributes
 * >
 *
 * (annotation?,(restriction|extension))
 *
 * </simpleContent>
 */
public class XsdSimpleContent implements XsdNode {
    private final XsdNode parent;
    private final Element domElement;

    public XsdSimpleContent(XsdNode parent, Element domElement) {
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

    /**
     * Simple content has no elements
     * @return
     * @throws XsdDefinitionException
     */
    @Override
    public List<XsdElement> getChildTargetElements() throws XsdDefinitionException {
        return Collections.emptyList();
    }

    @Override
    public List<XsdAttribute> getAttributes() throws XsdDefinitionException {
        return Common.getAttributes(this, domElement, RESTRICTION, EXTENSION);
    }

    @Override
    public XsdNode getParent() {
        return parent;
    }
}
