//
//  WLOYScheduleParserDelegate.swift
//  WLOY App
//
//  Created by William Robbins on 4/4/16.
//  Copyright © 2016 William Robbins. All rights reserved.
//

import Foundation

class WLOYScheduleParserDelegate: NSObject, NSXMLParserDelegate {
    
    // CONSTANTS
    let XML_DAY = "day"
    let XML_DESCRIPTION = "description"
    let XML_HOUR = "hour"
    let XML_MINUTE = "minute"
    let XML_NAME = "name"
    let XML_SHOW = "show"
    
    // DATA MEMBERS
    var showsSunday = [Show]()
    var showsMonday = [Show]()
    var showsTuesday = [Show]()
    var showsWednesday = [Show]()
    var showsThursday = [Show]()
    var showsFriday = [Show]()
    var showsSaturday = [Show]()
    var currentShowName: String
    var currentShowDay: String
    var currentShowStartHour: String
    var currentShowStartMinute: String
    var currentShowDescription: String
    var parsingShow: Bool
    var parsingName: Bool
    var parsingDay: Bool
    var parsingHour: Bool
    var parsingMinute: Bool
    var parsingDescription: Bool
    
    // METHODS
    override init() {
        currentShowName = ""
        currentShowDay = ""
        currentShowStartHour = ""
        currentShowStartMinute = ""
        currentShowDescription = ""
        parsingShow = false
        parsingName = false
        parsingDay = false
        parsingHour = false
        parsingMinute = false
        parsingDescription = false
    }
    
    func parserDidStartDocument(parser:NSXMLParser) {
        
    }
    
    func parser(_ parser:NSXMLParser, didStartElement elementName:String, namespaceURI namespaceURI:String?, qualifiedName qName:String?, attributes attributeDict:[String : String]) {
        
    }
    
    func parser(_ parser:NSXMLParser, didStartElement elementName:String, namespaceURI namespaceURI:String?, qualifiedName qName:String?) {
        
    }
    
    func parser(_ parser:NSXMLParser, foundCharacters string:String) {
        
    }
}