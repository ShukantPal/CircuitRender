/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scilca.circuit.parser;

import com.scilca.circuit.io.CharacterReader;
import com.scilca.circuit.sparks.SourceEOFException;
import com.scilca.circuit.sparks.StackFilledException;
import com.scilca.circuit.states.HTMLStates;
import com.scilca.circuit.tags.HTMLTags;
import com.scilca.dom.Comment;
import com.scilca.dom.TextNode;
import com.scilca.dom.html.HTMLBRElement;
import com.scilca.dom.html.HTMLDocument;
import com.scilca.dom.html.HTMLElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;

/**
 *
 * @author Sukant Pal
 */
public class HTMLParser extends AbstractParser{
    
    TreeBuilder<HTMLElement, HTMLDocument> DOMBuilder;
    ArrayList<HTMLElement> formattingEle;
    HTMLStateManager DOMState;
    
    public HTMLParser(CharacterReader inputSource) {
        super(inputSource);
        DOMBuilder = new TreeBuilder(HTMLDocument.getDocument(inputSource.toString()));
        DOMState = HTMLStateManager.getManager();
    }

    @Override
    public HTMLDocument parseDocument() throws UnknownError{
        while(true){
                try {
                    HTMLElement nextEle = constructElement();
                } catch (StackFilledException ex) {
                } catch (SourceEOFException ex) {
                    break;
                } catch (Exception ex) {
                    Logger.getLogger(HTMLParser.class.getName()).log(Level.WARNING, null, ex);
                }
        }
        return DOMBuilder.treeDoc;
    }

    @Override
    @SuppressWarnings("empty-statement")
    public HTMLElement constructElement() throws UnknownError, DOMException, StackFilledException, SourceEOFException, Exception{
        if(getClosed())
            throw new Exception(PARSER_CLOSED_MSG);
        
        String currentToken = inputTokensier.nextToken();
        if(currentToken == null)
            throw new SourceEOFException();
        
        switch(currentToken){
            case "<":
                if(DOMBuilder.stackOfOpenElements.size() > 10)
                    throw new StackFilledException();
                
                if(inputTokensier.nextToken() == null)
                    throw new DOMException(DOMException.SYNTAX_ERR, "UNKNOWN_ERR");
                
                HTMLElement foundElement = 
                        DOMBuilder.getDocument().getElement(inputTokensier.currentToken());
                
                if(foundElement == null)
                    throw new DOMException(DOMException.NOT_FOUND_ERR, "WRONG_TAG_ERR -> " + inputTokensier.currentToken());
                
                while(!inputTokensier.nextToken().equals(">")
                        && !inputTokensier.currentToken().equals("/>")){
                    String attrName = inputTokensier.currentToken();
                    
                    if(!foundElement.isValidAttribute(attrName))
                        continue;
                        
                    if(!inputTokensier.nextToken().equals("="))
                        throw new UnknownError("Attribute Name doesn't have equals sign next "
                        + "to it");
                    
                    if(!inputTokensier.nextToken().equals("\""))
                        throw new UnknownError("Quotes not found in attribute value");
                    
                    if(inputTokensier.nextToken() == null)
                        throw new UnknownError("EOF in element");
                    
                    Attr attr
                           = DOMBuilder.getDocument().createAttribute(attrName, inputTokensier.currentToken());
                    
                    foundElement.setAttributeNode(attr);
                    
                    if(!inputTokensier.nextToken().equals("\""))
                        throw new DOMException(DOMException.SYNTAX_ERR, "SYNTAX_ERR");
                }
                
                boolean autoClose = inputTokensier.currentToken().equals("/>");
                
                if(!autoClose)
                    for(String strTest : HTMLTags.SELF_CLOSING)
                        if(strTest.equals(foundElement.getTagName()))
                            autoClose = true;
                
                if(DOMState.getState() != HTMLStates.INITIAL 
                        && DOMState.getState() != HTMLStates.BEFORE_HTML
                        && DOMBuilder.getStack().get(DOMBuilder.getStack().size() - 1).isValidChild(foundElement.getTagName()))
                    DOMBuilder.addElement(foundElement, autoClose);
                else if(DOMState.getState() == HTMLStates.INITIAL
                        || DOMState.getState() == HTMLStates.BEFORE_HTML)
                    if(foundElement.getTagName().equals("html"))
                        DOMBuilder.addElement(foundElement, autoClose);
                
                DOMState.switchOnAddElement(foundElement.getTagName());
                
                return foundElement;
            case "</":
                if(inputTokensier.nextToken() == null)
                    throw new UnknownError("EOF at end tag");
                
                if(inputTokensier.currentToken().equals("br")){
                    HTMLBRElement lineBreak = 
                            (HTMLBRElement) DOMBuilder.getDocument().getElement("br");
                    DOMBuilder.addElement(lineBreak, true);
                }
                
                DOMBuilder.closeElementsTill(inputTokensier.currentToken());
                if(!inputTokensier.nextToken().equals(">"))
                    switch(inputTokensier.currentToken()){
                        case "<":
                            inputTokensier.backupMode();
                            break;
                        case "/>":
                            break;
                        default:
                            throw new UnknownError("Close Tag Not Closed");
                    }
                    
                return constructElement();
            case "<!":  
                if(inputTokensier.nextToken() != null){
                    if(inputTokensier.currentToken().toLowerCase().equals("doctype")){
                        // TODO
                        while(!inputTokensier.nextToken().equals(">"));
                    } else {
                        String intendedInfo = new String();
                        
                        intendedInfo += inputTokensier.currentToken();
                        
                        while(inputTokensier.nextToken() != null &&
                                !inputTokensier.currentToken().equals(">"))
                                intendedInfo += inputTokensier.currentToken();

                        intendedInfo = intendedInfo.substring(2, intendedInfo.length() - 2);
                        
                        Comment commentForm = (Comment) DOMBuilder.getDocument().createComment(intendedInfo);
                        DOMBuilder.addComment(commentForm);
                    }
                } else throw new SourceEOFException();
                return constructElement();
            default:
                String wholeText = inputTokensier.currentToken() + " ";
                while(inputTokensier.nextToken() != null
                        && !inputTokensier.currentToken().equals("<")
                        && !inputTokensier.currentToken().equals("</"))
                    wholeText += inputTokensier.currentToken() + " ";
                inputTokensier.backupMode();
                
                TextNode textForm = 
                        (TextNode) DOMBuilder.getDocument().createTextNode(wholeText);
                
                DOMBuilder.addText(textForm);
                
                return constructElement();
        }
    }

    @Override
    public String currentToken() {
        return inputTokensier.currentToken();
    }
    
}
