package scott.xsdanalytics;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import scot.xsdanalytics.exception.XsdDefinitionException;

import static scott.xsdanalytics.SchemaElements.*;

import java.util.*;

/**
 *
 * XML Schema choice element allows only one of the elements contained in the <choice> declaration to be present within the containing element.
 *
 *
 * Parent elements: group, choice, sequence, complexType, restriction (both simpleContent and complexContent), extension (both simpleContent and complexContent)
 *
 * <choice
 * id=ID
 * maxOccurs=nonNegativeInteger|unbounded
 * minOccurs=nonNegativeInteger
 * any attributes
 * >
 *
 * (annotation?,(element|group|choice|sequence|any)*)
 *
 * </choice>
 */
public class XsdChoice implements XsdNode {

    private final XsdNode parent;
    private final Element domElement;

    public XsdChoice(XsdNode parent, Element domElement) {
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
        //(element|group|choice|sequence|any)*

        List<XsdElement> allElementsAcrossAllChoices = Common.getChildElementsAcrossNodes(this, domElement, ELEMENT, GROUP, CHOICE, SEQUENCE, ANY);
       /*
        * Each choice branch can generate the same XsdElement, we have to make sure that we have no dups
        */
        filterOutDups(allElementsAcrossAllChoices);

        return  allElementsAcrossAllChoices;
    }

    private void filterOutDups(Collection<XsdElement> elements) throws XsdDefinitionException {
        Set<String> elementKeys = new HashSet<>();
        for (Iterator<XsdElement> i = elements.iterator(); i.hasNext();) {
            XsdElement xsdElement = i.next();
            final String key = String.valueOf(xsdElement.getNamespaceUri()) + "." + xsdElement.getElementName();
            if (!elementKeys.add(key)) {
                i.remove();
            }
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
}
