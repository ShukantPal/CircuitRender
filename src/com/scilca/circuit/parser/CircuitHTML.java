/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scilca.circuit.parser;

import com.scilca.circuit.io.CharacterReader;
import com.scilca.circuit.io.FileCharacterReader;
import com.scilca.circuit.io.HTMLTokeniser;

import org.w3c.dom.*;
import javax.xml.parsers.*;

import java.io.*;
import java.util.*;

import com.scilca.circuit.states.*;
import com.scilca.circuit.tags.*;
import com.scilca.circuit.sparks.*;

import com.scilca.dom.html.*;

/**
 * CircuitHTML is the class that parses a HTML source from a HTMLTokeniser that 
 runs on a FileCharacterReader. This allows 
 * @author Sukant Pal
 * @deprecated 
 */
public final class CircuitHTML {
    /**
     * This file path is the default address for non-file inputs.
     */
    public static final String DEFAULT_FILE_PATH
            = "CircuitParser - CharacterReader(Source - Unknown)";
    
    // This message is given while an exception is thrown if the 
    // public key of a DOCTYPE is invalid.
    private static final String PUBLIC_KEY_PARSE_ERR_MSG = 
            "The public key of the doctype is invalid";
    
    // This message is given while an exception is thrown if the 
    // system key of a DOCTYPE is invalid
    private static final String SYS_KEY_PARSE_ERR_MSG = 
            "The system key of the doctype is invalid";
    
    // This message is given if the BODY tag closes prematurely
    // and there open tags in the stack which are not in 
    // HTMLTags.BODY_CLOSURE_TAGS
    private static final String BODY_CLOSURE_ERR_MSG = 
            "The tag was not closed before the "
                + "closing tag of BODY was found.";
    
    /**
     * The file path of the input for the HTML parser is given here. If the input 
     * source is not a file then DEFAULT_FILE_PATH will be used instead and this 
     * would be null.
     */
    String filePath;
    
    /**
     * This is the state of the HTML parser while iterating over tokens.
     */
    HTMLStates HtmlState = HTMLStates.INITIAL;
    
    /**
     * The token iterator of the current parser is stored in this variable. This allows 
     * the parsing to be paused.
     */
    HTMLTokeniser HtmlTokenIterator;
    
    /**
     * The list of open tags are stored in this. Once they are found to be closed then this 
     * is closed. After the end of the data structure is reached then it should become 
     * empty in a well-formed HTML document.
     */
    ArrayList<HTMLElement> openTags;
    
    /**
     * The list of open active formatting elements is stored. This list is used to correctly 
     * interpret wrongly nested formatting elements like h1 and p. This list starts to fill 
     * only in the IN_BODY state because formatting elements are not present in the head 
     * element.
     */
    ArrayList<HTMLElement> activeFormattingTags;
    
    boolean last = false;
    
    /**
     * This document is built-up using the parser.
     */
    HTMLDocument inferedDocument;
    
    
    // The W3C specification states to keep a reference 
    // to the HEAD element object.
    HTMLHeadElement headElement = null; // To be set when found
    
    HTMLBodyElement bodyElement = null;
    
    // The W3C specification states to keep a reference
    // to the last form element.
    HTMLFormElement formElement = null;
    
    boolean frameset_ok = false;
    
    boolean scriptingFlag = false; // Not Supported Yet!!!!!!!
    
    boolean isXHTML = false;
    
    private void initDocument() throws ParserConfigurationException{
        this.inferedDocument = 
                HTMLDocument.getDocument(null);
    }
    
    private void resetDocument() throws ParserConfigurationException, Exception{
        initDocument();
        openTags = new ArrayList<>();
        HtmlTokenIterator.reset();
        last = false;
        HtmlState = HTMLStates.INITIAL;
    }
    
    protected CircuitHTML(String filePath) throws FileNotFoundException,
            ParserConfigurationException{
        initDocument();
        this.filePath = filePath;
        this.HtmlTokenIterator = new HTMLTokeniser(
            new FileCharacterReader(new File(filePath))
        );
        this.openTags = new ArrayList<>();
        this.activeFormattingTags = new ArrayList<>();
    }
    
    protected CircuitHTML(CharacterReader sourceReader) throws ParserConfigurationException{
        initDocument();
        this.filePath = DEFAULT_FILE_PATH;
        this.HtmlTokenIterator = new HTMLTokeniser(
             sourceReader
        );
        this.openTags = new ArrayList<>();
        this.activeFormattingTags = new ArrayList<>();
    }
    
    /**
     * The file path of the HTML source is given by this. If the HTML did not 
     * come from a file then DEFAULT_FILE_PATH is returned.
     * @return 
     * @see CircuitHTML.DEFAULT_FILE_PATH
     */
    public String getFilePath(){
        return this.filePath == null ? DEFAULT_FILE_PATH : filePath;
    }
    
    // Here, the private functions are internal APIs that were once 
    // in the larger methodes. They have came out for readablitiy
    // and to reduced code duplicates.
    
    /**
     * This method is same as calling afterValidification(insertion, true).
     * @param insertion 
     */
    private void afterValidation(HTMLElement insertion){
        afterValidation(insertion, true);
    }
    
    /**
     * The nodeIteration() method uses this after a node has been 
     * validated for addition to the Document (inferedDocument).
     * @param insertion - The node that has been validated for being 
     * added to the Document
     * @param openTag - If the node is open then it is also added to the 
     * list of open nodes (openTags).
     */
    private void afterValidation(HTMLElement insertion, boolean openTag){
        if(openTag)
            openTags.add(insertion);
        
        if(openTag && openTags.size() > 1)
            openTags.get(openTags.size() - 2).appendChild(insertion);
        else if(!openTag && openTags.size() > 0)
            openTags.get(openTags.size() - 1).appendChild(insertion);
        else inferedDocument.appendChild(insertion);
    }
    
    // Used by the commentHandler() method to rip off the '--' tokens.
    private String commentHandler_Rip(String commentStr) throws Exception{
        if(commentStr.length() < 4)
            throw new Exception("The comment string was invalid");
        String commentData = new String();
        for(int i=2; i<commentStr.length()-3; i++)       
            commentData += commentStr.charAt(i);
        return commentData;
    }
    
    /**
     * The commentHandler() method handles the comment strings like the 
     * textHandler() method, but constructs a Comment object.
     * @throws Exception 
     * @see CircuitHTML.textHandler()
     */
    private void commentHandler() throws Exception{                         
        String totalComment = new String();
        
        do{
            totalComment += HtmlTokenIterator.currentToken() + " ";
        } while(!HtmlTokenIterator.nextToken().equals(">"));
        
        totalComment = commentHandler_Rip(totalComment);
        Comment HtmlComment = inferedDocument.createComment(totalComment);
        
        if(openTags.size() > 1)
            openTags.get(openTags.size() - 1).appendChild(HtmlComment);           
        else inferedDocument.appendChild(HtmlComment);
    }
    
    /**
     * The textHandler() method handles text nodes in the nodeIteration() 
     * method where the default case is met. The consequent text nodes are 
     * appended to the element.
     * 
     * <p>
     * For example, in a elements body, the String "THIS IS HTML" is put, then 
     * the whole string is added as a single text node.
     * </p>
     * @throws Exception 
     */
    private void textHandler() throws Exception{
        String totalText = new String();
        
        do{ // Appends all adjacent text nodes in the source
            totalText += HtmlTokenIterator.currentToken() + " ";
        } while(!HtmlTokenIterator.nextToken().equals("<")
                && !HtmlTokenIterator.currentToken().equals("</"));
        
        // As the do-while loop test for the opening tag, the 
        // opening tag will be read unfortunately. The nodeIteration()
        // method will expect the opening token. So the HTMLTokeniser is 
        // backed by one token.
        HtmlTokenIterator.backupMode();
        
        // All the active formatting elements will also be 
        // given this text according to W3C specs
        if(activeFormattingTags.size() > 0)
            for(HTMLElement formattingEle : activeFormattingTags)
                formattingEle.appendChild(inferedDocument.createTextNode(totalText));
        
        // If the state of the parser is not INITIAL then the 
        // last open element will nest the text node.
        if(HtmlState != HTMLStates.INITIAL)
            openTags.get(openTags.size() - 1)
                    .appendChild(inferedDocument.createTextNode(totalText));           

        // Else if the state of the parser is INITIAL then the 
        // document will hold the text node (throws Exception)
        else inferedDocument
                .appendChild(inferedDocument.createTextNode(totalText));
    }
    // TODO
    private void createDoctype_HTML(String publicKey, String sysKey){
        DOMImplementation domImpl = inferedDocument.getImplementation();
        domImpl.createDocumentType("HTML", publicKey, sysKey);
    }
    
    /**
     * If the tag name specified is in the array of 
     * tag names that are supposed to be in the HEAD 
     * element, true is returned.
     * @param tagName
     * <p>
     * The tag's name is given by this argument. If it is 
     * null or invalid then the exception will be thrown 
     * directly. There is not exception-handling mechanism
     * in the method.
     * </p>
     * @return - If the method's condition is met
     */
    private boolean isHeadSubElement(String tagName){
        for(String Hse : HTMLTags.HEADER_ELEMENTS)
            if(Hse.equals(tagName))
                return true;
        return false;
    }
    
    /**
     * If the tag name specified is in the array of tag 
     * names that close the paragraph tag automatically,
     * true is returned.
     * @param tagName - 
     * <p>
     * The tag's name is given by this argument. If it is 
     * null or invalid then the exception will be thrown 
     * directly. There is not exception-handling mechanism
     * in the method.
     * </p>
     * @return - If the methods condition is true.
     * @see - HTMLTags.PARAGRAPH_CLOSURE_TAGS
     */
    private boolean isParagraphClosureTag(String tagName){
        for(String Pct : HTMLTags.PARAGRAPH_CLOSURE_TAGS)
            if(Pct.equals(tagName))
                return true;
        return false;
    }
    
    /**
     * If the tag name specified is in the array of tag 
     * names that format the text node contained in them.
     * @param tagName
     * @return 
     */
    private boolean isFormattingElement(String tagName){
        for(String Fct : HTMLTags.FORMATTING_SCOPES)
            if(Fct.equals(tagName))
                return true;
        return false;
    }
    
    /**
     * If the tag specified is in the array of tag names 
     * that can close if the body tag closes prematurely,
     * true is returned. 
     * 
     * <p>
     * But if it is not found then a message is logged in 
     * the current ShortCircuit Logger and false is 
     * returned.
     * </p>
     * @param tagName
     * @return 
     */
    private boolean isBodyClosureTag(String tagName){
        for(String Bct : HTMLTags.BODY_CLOSURE_TAGS)
            if(Bct.equals(tagName))
                return true;
        
        // Logs the message if the tag is not found.
        ShortCircuit.getInstance().Log(BODY_CLOSURE_ERR_MSG
                ,ShortCircuit.ERR_SEVERITY.LOW, null);
        return false;
    }
    
    /**
     * This method is used by the nodeIteration() method. If the tag 
     * name is in the list of open tags (openTags) then it is removed 
     * with all its right-side elements.
     * 
     * <p>
     * For example, if a HTML fragment is mis-nested then all the child 
     * elements with the parent element will be closed with its closing 
     * tag.
     * </p>
     * @param removing 
     */
    private void removeIfAnyMatch(String removing){
        if(openTags.stream().anyMatch(
                (Node closableTag) -> { // Checks if the tag is contained 
                    // in the stack of open tags.
                    return removing.equals(closableTag.getNodeName()); 
                }
        )){
            for(int r=openTags.size() - 1; r >= 0; r--)
                if(openTags.remove(r).getNodeName().equals(removing))

                    break;
        } 
    }
    
    private void removeFormmattingIfAnyMatch(String removing){
        for(int r=activeFormattingTags.size() - 1; r >= 0; r--){
            if(activeFormattingTags.get(r).getNodeName().equals(removing)){
                activeFormattingTags.remove(r);
                break;
            }
        }
    }
    
    /**
     * <p>
     * This method is used for parsing single segments of the HTML input. This 
     * allows all major types of elements of HTML documents to be parsed in a 
     * loop. It parses Element, Comment, CDTASection and much more!
     * </p>
     * 
     * 
     * 
     * <p>
     * The CircuitHTML class does not use a tree builder for constructing a HTML 
     * DOM. The node iteration method fulfills this method. This is why CircuitHTML
     * can work in relatively lower memory.
     * </p>
     * @return - The node next in the HTMLTokeniser that was parsed
     * @throws Exception - If the HTMLTokeniser fails to read tokens due to a 
        CharacterReader failure then it is passed directly.
     */
    boolean nodeIteration(boolean readCurrentToken) throws DOMException, Exception{
        String tokenIteration = readCurrentToken ? HtmlTokenIterator.currentToken() 
                : HtmlTokenIterator.nextToken();
        if(tokenIteration == null)
            return false;
        
        switch (tokenIteration) {
            case "<": // This token indicates a starting tag for an element
                // The element after the '<' token will built into this object 
                // variable.
                
                if(openTags.size() > 10) // Some sites have a too deep nesting 
                    return true; // so after more than 10 tags the elements 
                // will not be added
                com.scilca.dom.html.HTMLElement foundElement
                        = inferedDocument.getElement(HtmlTokenIterator.nextToken().toLowerCase()); // Get a valid HTML Element
                
                if(foundElement == null)
                    throw new DOMException(DOMException.NOT_FOUND_ERR, "HTML TAG NOT FOUND - ERROR 404 (NOT_FOUND_ERR) -  " 
                            + HtmlTokenIterator.currentToken());
                
                // Iterate over consequent tokens till a tag closure token doesn't
                // come. It will add attributes to foundElment.
                while(!this.HtmlTokenIterator.nextToken().equals(">")
                        && !this.HtmlTokenIterator.currentToken().equals("/>")){
                    Attr elementAttribute =
                            inferedDocument.createAttribute(HtmlTokenIterator.currentToken());
                    
                    if(!HtmlTokenIterator.nextToken().equals("="))
                        throw new DOMException(DOMException.SYNTAX_ERR, "SYNTAX_ERR(ATTRIBUTE NOT HAVING EQUAL_SIQN AS NEXT TOKEN) - "
                         + foundElement.getTagName()); // Equal Sign
                    
                    if(!HtmlTokenIterator.nextToken().equals("\""))
                        throw new DOMException(DOMException.SYNTAX_ERR, "SYNTAX_ERR(ATTRIBUTE NOT HAVING OPENING QUOTE AFTER EQUAL SIGN) - "
                        + foundElement.getTagName()); // Quote
                    
                    elementAttribute.setNodeValue(HtmlTokenIterator.nextToken());
                    
                    if(foundElement.isValidAttribute(elementAttribute.getName()))
                        foundElement.getAttributes().setNamedItem(elementAttribute);
                    
                    // TODO - GET SOLUTIONS
                    if(!HtmlTokenIterator.nextToken().equals("\""))
                            throw new DOMException(DOMException.SYNTAX_ERR, "SYNTAX_ERR(ATTRIBUTE NOT HAVING CLOSING QUOTE AFTER EQUAL SIGN)"); // Quote
                }
                // If the element has a self-closing flag or contains child element
                // according to the written context (not to HTML specs)
                boolean selfClosing = HtmlTokenIterator.currentToken().equals("/>");
                
                // Based on the parser's current state the action for the pending 
                // element will occur and, if needed, the current state will change 
                // accordingly
                switch(HtmlState){
                    case INITIAL:
                    case BEFORE_HTML:
                        // The only element to use while in this state is the 
                        // root HTML tag. So only a HTML element will be added
                        if(foundElement.getNodeName().equals("html")){
                            afterValidation(foundElement);
                            
                            // Now the current state will change to BEFORE_HEAD as 
                            // the parser will wait until a HEAD element is found 
                            // and will exclude other elements.
                            HtmlState = HTMLStates.BEFORE_HEAD; 
                        }
                        break;
                    case BEFORE_HEAD:
                        // The element that is awaited in BEFORE_HEAD state is the 
                        // HEAD tag so if it is validated then it will be added.
                        if(foundElement.getNodeName().equals("head")){
                            afterValidation(foundElement);
                            try{ // The head element should be casted to a HTMLHeadElement
                                headElement = (HTMLHeadElement) foundElement;
                            } catch(ClassCastException e){ // If a ClassCastException is thrown, that means there is a unmanageable error
                                throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, "NO_MODIFICATION_ALLOWED(NOT POSSIBLE)_ERR");
                            }
                            HtmlState = HTMLStates.IN_HEAD;
                        // Some short-HTML documents, such as testing webpages, do 
                        // not contains a HEAD element and contain BODY tags.
                        // Thus, the state will directly change to IN_BODY.
                        } else if(foundElement.getNodeName().equals("body")){
                            afterValidation(foundElement);
                            try{ // The body element's reference is set here
                                bodyElement = (HTMLBodyElement) foundElement;
                            } catch(ClassCastException e){ // This exception shouldn't be thrown but....
                                throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, "NO_MODIFICATION_ALLOWED(NOT POSSIBLE)_ERR");
                            }
                            HtmlState = HTMLStates.IN_BODY;
                            frameset_ok = false;
                        }
                        break;
                    case IN_HEAD:
                        // These are the normal tags found in a HEAD tag. All the 
                        // elements in the HEAD_TAGS array in HTMLTags class are 
                        // not used as some need special attention. Even if these 
                        // element are not self-closing in the context they will 
                        // not be left closed.
                        assert headElement != null;
                        if(headElement.isValidChild(foundElement.getTagName()))
                            afterValidation(foundElement, false);
                        // Unlike, the above elements the TITLE tag needs special 
                        // attention as it doesn't contain attributes and is not 
                        // a self-closing tag in HTML.
                        else if(foundElement.getNodeName().equals("title")
                                && !selfClosing)
                            afterValidation(foundElement, true);
                        // TODO
                        else if(foundElement.getNodeName().equals("noscript")
                                && !selfClosing && scriptingFlag){
                            afterValidation(foundElement);
                            HtmlState = HTMLStates.IN_HEAD_NOSCRIPT;
                        }
                        
                        break;
                    case AFTER_HEAD:
                        // The BODY tag is awaited after the HEAD tag. If found, 
                        // then parser's state will change to IN_BODY. By this 
                        // time, the head tag should be closed and body will 
                        // be the second open tag in the stack (openTags).
                        if(foundElement.getNodeName().equals("body")){
                            afterValidation(foundElement);
                            HtmlState = HTMLStates.IN_BODY;
                            this.frameset_ok = false;
                            
                            try{
                                bodyElement = (HTMLBodyElement) foundElement;
                            } catch(ClassCastException e){
                                throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, "");
                            }
                            
                        // The Frameset elements are found in a HTML document after 
                        // the HEAD element. This scenario is shown here. The parser's 
                        // state will change to IN_FRAMESET.
                        } else if(foundElement.getNodeName().equals("frameset")){
                            afterValidation(foundElement);
                            HtmlState = HTMLStates.IN_FRAMESET;
                        } else if(isHeadSubElement(foundElement.getNodeName())){
                            assert headElement != null;
                            headElement.appendChild(foundElement);
                            if(!selfClosing)
                                openTags.add(foundElement);
                        }
                        break;
                    case IN_BODY:
                        // The W3C specifications state that if a HTML element is 
                        // found in a BODY element then the parser should add the 
                        // extra attributes in this HTML tag to the root HTML 
                        // element if they exist. Otherwise, the element will be 
                        // ignored and automatically closed.
                        if(foundElement.getNodeName().equals("html")){
                            Node requiredNode = openTags.get(0);
                            for(int i=0; i<foundElement.getAttributes().getLength(); i++){
                                requiredNode.getAttributes()
                                        .setNamedItem(foundElement.getAttributes().item(i));
                            }
                        // If a tag that is supposed to be in a HEAD element is 
                        // found in the BODY element then it will not be added 
                        // directly. This tag and its children will be appended 
                        // to the list of children of the HEAD node pointer (headElement).
                        } else if(isHeadSubElement(foundElement.getNodeName())){
                            assert headElement != null;
                            if(headElement == null)
                                headElement = (HTMLHeadElement) inferedDocument.getElement("head");
                            
                            headElement.appendChild(foundElement);
                            if(!selfClosing)
                                openTags.add(foundElement);
                        } else if(foundElement.getNodeName().equals("frameset")){
                        // There are tags that cannot be nested in paragraphs 
                        // so they automatically close the paragraph before opening.
                        // That's why a new paragraph can be created by a opening 
                        // before the closing tag of a paragraph. This portion handles
                        // those tags.
                        } else if(this.isParagraphClosureTag(foundElement.getNodeName())){
                            assert openTags.size() > 2;
                            if(openTags.get(openTags.size() - 1).getNodeName().equals("p")){
                                openTags.remove(openTags.size() - 1);
                            } 
                            afterValidation(foundElement);
                        } else if(isFormattingElement(HtmlTokenIterator.currentToken())){
                            afterValidation(foundElement);
                            activeFormattingTags.add(foundElement);
                        } else if(bodyElement.isValidChild(HtmlTokenIterator.currentToken().toLowerCase())){
                            afterValidation(foundElement);
                        }
                        break;
                    // If the above states are not the current state of the parser 
                    // the parser will just add the found element to the list of 
                    // open elements and proceed.
                    default:
                        openTags.add(foundElement);
                        break;
                }
                // The element has been added, so it is inferred that it will be 
                // closed, so true is returned to continue the cycle.
                return true;
            case "</":
                // The name of the closing tag is found here. If the it is 
                // empty then it should be ignored.
                String closingTag = HtmlTokenIterator.nextToken();
                
                switch(HtmlState){
                    // If the state is IN_HEAD then, if the closed tag 
                    // is HEAD the state will change to AFTER_HEAD.
                    case IN_HEAD:
                        if(closingTag.equals("head"))
                            HtmlState = HTMLStates.AFTER_HEAD;
                        break;
                    // If the state is IN_BODY then, if the closed tag 
                    // is BODY the state will change to AFTER_BODY. Here,
                    // W3C specifications say if tags other than HTMLTags.
                    // BODY_CLOSURE_TAGS are in scope there is a parse error.
                    case IN_BODY:
                        if(closingTag.equals("body")) {
                            HtmlState = HTMLStates.AFTER_BODY;
                            // Log a parse error if the tag is not a
                            // auto closure tag.
                            isBodyClosureTag(closingTag);
                        } else if(closingTag.equals("br")){
                            afterValidation(inferedDocument.getElement("br"));
                            return true;
                        }
                        break;
                    // If the state is AFTER_BODY and the parser gets 
                    // a html closing tag, then the parser will send 
                    // a end signal by returning false.
                    case AFTER_BODY:
                        if(closingTag.equals("html")){
                            removeIfAnyMatch(closingTag);
                            return false;
                        }
                        break;
                    
                }
                
                // This closes the element from the open tags. If the 
                // nested elements are not closed by the tags before this 
                // tag then they are also closed.
                removeIfAnyMatch(closingTag);
                if(HtmlState == HTMLStates.IN_BODY && isFormattingElement(HtmlTokenIterator.currentToken()))
                    removeFormmattingIfAnyMatch(HtmlTokenIterator.currentToken());
                HtmlTokenIterator.nextToken(); // > char
                break;
            case "<!": 
                // The case infers a comment or a DOCTYPE declaration. 
                // The value of the node is retrived and later checked 
                // if it is a comment or DOCTYPE
                String commentStr = HtmlTokenIterator.nextToken();
                switch(HtmlState){
                    case INITIAL:
                    case BEFORE_HTML:
                        // If the comment value is actually a DOCTYPE (infered as the next token 
                        // is not a '--' value (comment are declared as <!--STRING-->.
                        if(commentStr.equals("DOCTYPE") && HtmlState == HTMLStates.INITIAL){
                            // The DOCTYPE declaration occures as <!DOCTYPE HTML PUBLIC ... ...> 
                            // or as <!DOCTYPE XHTML PUBLIC ... ... > or as <!DOCTYPE html> (HTML5)
                            String[] HtmlDoctypeStrings = new String[4];
                            for(int i=0; i<4; i++)
                                if(HtmlTokenIterator.nextToken().equals("\"")){
                                    i--;
                                }
                                else if(!HtmlTokenIterator.currentToken().equals(">"))
                                    HtmlDoctypeStrings[i] = HtmlTokenIterator.currentToken();
                                else break;
                            
                            // The HTML4 documents use HTML PUBLIC
                            if(HtmlDoctypeStrings[0].equals("HTML")
                                    && HtmlDoctypeStrings[1].equals("PUBLIC")){
                                switch(HtmlDoctypeStrings[2]){
                                    case "-//W3C//DTD HTML 4.01//EN":
                                        if(HtmlDoctypeStrings[3].equals("http://www.w3.org/TR/html4/strict.dtd"))
                                            this.createDoctype_HTML(HtmlDoctypeStrings[2], HtmlDoctypeStrings[3]);
                                        else throw new HTMLDoctypeNotParsedException(CircuitHTML.SYS_KEY_PARSE_ERR_MSG);
                                        break;
                                    case "-//W3C//DTD HTML 4.01 Transitional//EN":
                                        if(HtmlDoctypeStrings[3].equals("http://www.w3.org/TR/html4/loose.dtd"))
                                            this.createDoctype_HTML(HtmlDoctypeStrings[2], HtmlDoctypeStrings[3]);
                                        else throw new HTMLDoctypeNotParsedException(CircuitHTML.SYS_KEY_PARSE_ERR_MSG);
                                        break;
                                    case "-//W3C//DTD HTML 4.01 Frameset//EN":
                                        if(HtmlDoctypeStrings[3].equals("http://www.w3.org/TR/html4/frameset.dtd"))
                                            this.createDoctype_HTML(HtmlDoctypeStrings[2], HtmlDoctypeStrings[3]);
                                        else throw new HTMLDoctypeNotParsedException(CircuitHTML.SYS_KEY_PARSE_ERR_MSG);
                                        break;
                                    default: 
                                        throw new HTMLDoctypeNotParsedException(CircuitHTML.PUBLIC_KEY_PARSE_ERR_MSG);
                                }
                            // HTML5 uses a simple token html.
                            } else if(HtmlDoctypeStrings[0].equals("html")){
                                switch(HtmlDoctypeStrings[0]){
                                    default:
                                        this.createDoctype_HTML(null, null);
                                        break;
                                }
                            }
                            
                            this.HtmlState = HTMLStates.BEFORE_HTML;
                        } else commentHandler();
                        break;
                    default:
                        if(!commentStr.equals("DOCTYPE"))
                            commentHandler();
                }
                break;
            default:
                // After all the cases tried, it is infered that the token must be 
                // a text node. The textHandler() method handles this case specificly
                textHandler();
                break;
        }
        
        return true;
    }
    
    boolean isClosed = false;
     
    @SuppressWarnings("empty-statement")
    public HTMLDocument parseHTMLDocument() throws Exception{
        if(isClosed)
            throw new Exception("Closed");
        
        while(true){
            boolean goNext = false;
            try{
                if(nodeIteration(false))
                    goNext = true;
            } catch(DOMException e){
                ShortCircuit.getInstance().Log("DOMException", ShortCircuit.ERR_SEVERITY.INTERMEDIATE, e);
            }
            
            if(!goNext)
                break;
        }        
        
        isClosed = true;
        return inferedDocument;
    }
    
    public Node getHeadElement(){
        return headElement;
    }
    
}
