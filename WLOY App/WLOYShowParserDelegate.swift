//
//  WLOYSongParserDelegate.swift
//  WLOY App
//
//  Created by William Robbins on 3/31/16.
//  Copyright © 2016 William Robbins. All rights reserved.
//

import Foundation

class WLOYShowParserDelegate: NSObject, NSXMLParserDelegate {
    
    // CONSTANTS
    let DEFAULT_SHOW_NAME = "SHOW NOT FOUND"
    let DEFAULT_DJ = "DJ NOT FOUND"
    let XML_ITEM = "item"
    let XML_SHOW_NAME = "ra:showname"
    let XML_SHOW_DJ = "ra:showdj"
    
    // DATA MEMBERS
    var currentShowName: String
    var currentDJ: String
    var showFound: Bool
    var parsingShow: Bool
    var parsingShowName: Bool
    var parsingDJ: Bool
    var tempCurrentShowName: String
    var tempCurrentDJ: String
    
    override init() {
        currentShowName = DEFAULT_SHOW_NAME
        currentDJ = DEFAULT_DJ
        showFound = false
        parsingShow = false
        parsingShowName = false
        parsingDJ = false
        tempCurrentShowName = ""
        tempCurrentDJ = ""
    }
    
    // parserDidStartDocument - called when the parser begins reading the XML document
    func parserDidStartDocument(parser:NSXMLParser) {
        showFound = false
        parsingShow = false
        
        // Store temporary updated song and artist until parsing is complete
        tempCurrentShowName = ""
        tempCurrentDJ = ""
    }
    
    // didStartElement - called when the parser begins parsing an XML element
    func parser(_ parser:NSXMLParser, didStartElement elementName:String, namespaceURI namespaceURI:String?, qualifiedName qName:String?, attributes attributeDict:[String : String]) {
            // Only parse this if the first song has not been found
            if(!showFound) {
                if(elementName == XML_ITEM) {
                    parsingShow = true
                }
                else if(parsingShow) {
                    if(elementName == XML_SHOW_NAME) {
                        parsingShowName = true
                    }
                    else if(elementName == XML_SHOW_DJ) {
                        parsingDJ = true
                    }
                }
            }
    }
    
    // didEndElement - called when the parser finishes parsing an XML element
    func parser(_ parser:NSXMLParser, didEndElement elementName:String, namespaceURI namespaceURI:String?, qualifiedName qName:String?) {
        if(parsingShow) {
            if(elementName == XML_ITEM) {
                parsingShow = false
                showFound = true
                currentShowName = tempCurrentShowName
                currentDJ = tempCurrentDJ
            }
            else if(elementName == XML_SHOW_NAME) {
                parsingShowName = false
            }
            else if(elementName == XML_SHOW_DJ) {
                parsingDJ = false
            }
        }
    }
    
    // foundCharacters - called when the parser retrieves new characters within an element
    func parser(_ parser:NSXMLParser, foundCharacters string:String) {
        if(parsingShow) {
            if(parsingShowName) {
                tempCurrentShowName = tempCurrentShowName + string
            }
            else if(parsingDJ) {
                tempCurrentDJ = tempCurrentDJ + string
            }
        }
    }
    
}