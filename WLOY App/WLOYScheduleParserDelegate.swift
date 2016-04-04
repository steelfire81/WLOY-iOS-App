//
//  WLOYScheduleParserDelegate.swift
//  WLOY App
//
//  Created by William Robbins on 4/4/16.
//  Copyright © 2016 William Robbins. All rights reserved.
//

import Foundation

class WLOYScheduleParserDelegate: NSObject, NSXMLParserDelegate {
    
    func parserDidStartDocument(parser:NSXMLParser) {
        NSLog("started document")
        NSLog("----------------")
    }
    
    func parser(_ parser:NSXMLParser, didStartElement elementName:String, namespaceURI namespaceURI:String?, qualifiedName qName:String?, attributes attributeDict:[String : String]) {
        NSLog(elementName)
    }
    
    func parser(_ parser:NSXMLParser, didStartElement elementName:String, namespaceURI namespaceURI:String?, qualifiedName qName:String?) {
        NSLog(elementName)
    }
    
    func parser(_ parser:NSXMLParser, foundCharacters string:String) {
        
    }
}