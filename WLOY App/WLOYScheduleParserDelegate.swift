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
    
    // CONSTANTS - XML Days
    let XML_SUNDAY = "Sunday"
    let XML_MONDAY = "Monday"
    let XML_TUESDAY = "Tuesday"
    let XML_WEDNESDAY = "Wednesday"
    let XML_THURSDAY = "Thursday"
    let XML_FRIDAY = "Friday"
    let XML_SATURDAY = "Saturday"
    
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
        parsingShow = false
        parsingName = false
        parsingDay = false
        parsingHour = false
        parsingMinute = false
        parsingDescription = false
        
        currentShowName = ""
        currentShowDay = ""
        currentShowStartHour = ""
        currentShowStartMinute = ""
        currentShowDescription = ""
        
        showsSunday.removeAll()
        showsMonday.removeAll()
        showsTuesday.removeAll()
        showsWednesday.removeAll()
        showsThursday.removeAll()
        showsFriday.removeAll()
        showsSaturday.removeAll()
    }
    
    func parser(_ parser:NSXMLParser, didStartElement elementName:String, namespaceURI namespaceURI:String?, qualifiedName qName:String?, attributes attributeDict:[String : String]) {
        if(elementName == XML_SHOW) {
            parsingShow = true
        } else if(elementName == XML_NAME) {
            parsingName = true
        } else if(elementName == XML_DAY) {
            parsingDay = true
        } else if(elementName == XML_HOUR) {
            parsingHour = true
        } else if(elementName == XML_MINUTE) {
            parsingMinute = true
        } else if(elementName == XML_DESCRIPTION) {
            parsingDescription = true
        }
    }
    
    func parser(_ parser:NSXMLParser, didEndElement elementName:String, namespaceURI namespaceURI:String?, qualifiedName qName:String?) {
        if(elementName == XML_SHOW) {
            // Finish show and add show to appropriate day array
            let newShow = Show(n:currentShowName, h:Int(currentShowStartHour)!, m:Int(currentShowStartMinute)!, d:currentShowDescription)
            
            if(currentShowDay == XML_SUNDAY) {
                showsSunday.append(newShow)
            } else if(currentShowDay == XML_MONDAY) {
                showsMonday.append(newShow)
            } else if(currentShowDay == XML_TUESDAY) {
                showsTuesday.append(newShow)
            } else if(currentShowDay == XML_WEDNESDAY) {
                showsWednesday.append(newShow)
            } else if(currentShowDay == XML_THURSDAY) {
                showsThursday.append(newShow)
            } else if(currentShowDay == XML_FRIDAY) {
                showsFriday.append(newShow)
            } else if(currentShowDay == XML_SATURDAY) {
                showsSaturday.append(newShow)
            }
            
            // Reset show read information
            currentShowName = ""
            currentShowDay = ""
            currentShowStartHour = ""
            currentShowStartMinute = ""
            currentShowDescription = ""
        } else if(elementName == XML_NAME) {
            parsingName = false
        } else if(elementName == XML_DAY) {
            parsingDay = false
        } else if(elementName == XML_HOUR) {
            parsingHour = false
        } else if(elementName == XML_MINUTE) {
            parsingMinute = false
        } else if(elementName == XML_DESCRIPTION) {
            parsingDescription = false
        }
    }
    
    func parser(_ parser:NSXMLParser, foundCharacters string:String) {
        if(parsingName) {
            currentShowName = currentShowName + string
        } else if(parsingDay) {
            currentShowDay = currentShowDay + string
        } else if(parsingHour) {
            currentShowStartHour = currentShowStartHour + string
        } else if(parsingMinute) {
            currentShowStartHour = currentShowStartHour + string
        } else if(parsingDescription) {
            currentShowDescription = currentShowDescription + string
        }
    }
}