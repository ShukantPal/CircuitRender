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
package com.scilca.dom;

import org.w3c.dom.DOMException;
import org.w3c.dom.Text;

/**
 *
 * @author Sukant Pal
 */
public class CDATASection extends CharacterData implements org.w3c.dom.CDATASection{

    public CDATASection(String nodeValue, Document ownerDocument) {
        super(null, getConvertedString(nodeValue), Node.CDATA_SECTION_NODE, ownerDocument);
    }

    @Override
    public Text splitText(int offset) throws DOMException {
        if(offset < 0 || offset > getNodeValue().length())
            throw new DOMException(DOMException.INDEX_SIZE_ERR, "INDEX_SIZE_ERR");
        String newValue = new String();
        String cutValue = new String();
        
        for(int charIndex = 0; charIndex <= offset; charIndex++){
            newValue += getNodeValue().charAt(charIndex);
        }
        
        for(int charIndex = offset + 1; charIndex < getNodeValue().length(); charIndex++){
            cutValue += getNodeValue().charAt(charIndex);
        }
        
        this.nodeValue = newValue;
        return new CDATASection(cutValue, ownerDocument);
    }

    @Override
    public boolean isElementContentWhitespace() {
        for(char ct : nodeValue.toCharArray()){
            if(ct != ' ' && ct != '\t')
                return false;
        }
        return true;
    }

    @Override
    @SuppressWarnings("empty-statement")
    public String getWholeText() {
        if(getParentNode() != null){
            NodeList siblingNodes = getParentNode().getChildNodes();
            String wholeText = new String();
            
            int selfIndex;
            for(selfIndex = 0; selfIndex < siblingNodes.getLength(); selfIndex++){
                if(siblingNodes.item(selfIndex) == this)
                    break;
            }
            
            org.w3c.dom.Node currentNode;
            while(siblingNodes.item(selfIndex--).getNodeType() != Node.CDATA_SECTION_NODE);
            System.out.println(selfIndex);
            
            selfIndex += 2;
            
            for( ; selfIndex < siblingNodes.getLength(); selfIndex++){
                currentNode = siblingNodes.item(selfIndex);
                if(currentNode.getNodeType() != Node.CDATA_SECTION_NODE)
                    break;
                else wholeText += currentNode.getNodeValue();
            }
            
            return wholeText;
        }
        else return getNodeValue();
    }

    @Override
    public Text replaceWholeText(String content) throws DOMException {
        int selfIndex;
        NodeList siblingNodes = getParentNode().getChildNodes();
        for(selfIndex = 0; selfIndex < siblingNodes.getLength(); selfIndex++){
            if(siblingNodes.item(selfIndex) == this)
                break;
        }
            
        for(int replacementIndex = selfIndex - 1; replacementIndex > 0; replacementIndex--){
            if(siblingNodes.item(replacementIndex).getNodeType() != Node.CDATA_SECTION_NODE)
                break;
            else siblingNodes.remove(replacementIndex);
        }
        
        for(int replacementIndex = selfIndex + 1; ; ){
            if(replacementIndex == siblingNodes.getLength())
                break;
            else if(siblingNodes.item(replacementIndex).getNodeType() != Node.CDATA_SECTION_NODE)
                break;
            else siblingNodes.remove(replacementIndex);
        }
        
        setNodeValue(content);
        return this;
    }

    
}
