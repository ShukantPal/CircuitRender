/*
 * Copyright (C) 2016 Sukant Pal
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.scilca.circuit.tags;

/**
 * This class encapsulates the tags that are found in a HTML document. There 
 * are many field constants for specific purposes with there own special 
 * meanings. The CircuitHTML parser uses these fields to recognize valid HTML 
 * tags and excludes those tags that are not found in the specific context. 
 * 
 * <p>
 * This class is not meant to be instantiated and only contains static field 
 * constants. These constants are taken from w3c specifications only.
 * </p>
 * @author Sukant Pal
 */
public final class HTMLTags {
    private HTMLTags(){}

    /**
     * These tags are only found in the HEAD tag. They aren't found 
     * anywhere else in the HTML document. If they are found outside 
     * of the HEAD element then they are handled separately.
     */
    public static final String HEADER_ELEMENTS[] = {
        "base", "basefont", "bgsound", "command", 
        "meta", "noframes", "script", "style", "title"
    };
    
    // CLOSURE-RELATED TAGS
    
    /**
     * These are the tags that exist in a BODY tags. They have a special 
     * feature of closing a paragraph tag and not becoming its child. So, 
     * they never can be the direct child of a paragraph element.
     */
    public static final String PARAGRAPH_CLOSURE_TAGS[] = {
        // PORTION 1
        
        "address", "article", "aside", "blockquote", "center",
        "details", "dialog", "dir", "div", "dl", "fieldset",
        "figset", "figure", "header", "hgroup", "menu", "nav",
        "ol", "p", "section", "summary", "ul",
        
        // PORTION 2
        "h1", "h2", "h3", "h4", "h5", "h6",
            
        // PORTION 3
        "form"
    };
    
    /**
     * These tags may be mistaken for relation with PARAGRAPH_CLOSURE_TAGS 
     * but they actually are the tags that automatically close if the 
     * BODY element in scope is being closed. If the BODY tag closes nested 
     * with tags other than this then there is a parser error.
     */
    public static final String BODY_CLOSURE_TAGS[] = {
        "dd", "dt", "li", "optgroup", "option", "p", "rp", "rt",
        "tbody", "td", "tfoot", "th", "thead", "tr", "body", "html"
    };
    
    /**
     * This are the elements that are usually in a formatting scope. They 
     * format text nodes in them.
     */
    public static final String FORMATTING_SCOPES[] = {
        "b", "big", "code", "em", "font", "i", "nobr",
        "s", "small", "strike", "strong", "tt", "u"
    };
    
    public static final String SELF_CLOSING[] = {
        "br", "base", "basefont", "bgsound", "command", 
        "meta", "noframes", "script", "style", "title"
    };
    
}
