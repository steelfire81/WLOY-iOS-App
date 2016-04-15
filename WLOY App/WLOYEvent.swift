//
//  WLOYEvent.swift
//  WLOY App
//
//  Created by William Robbins on 4/14/16.
//  Copyright © 2016 William Robbins. All rights reserved.
//

import EventKit
import Foundation

class WLOYEvent: NSObject {
    
    // DATA MEMBERS
    var name:String
    var month:Int
    var day:Int
    var year:Int
    var hour:Int
    var minute:Int
    
    // METHODS
    init(n:String, mo:Int, d:Int, y:Int, h:Int, mi:Int) {
        name = n
        month = mo
        day = d
        year = y
        hour = h
        minute = mi
    }
    
    // storeToCalendar - save this event to the system calendar
    func storeToCalendar() {
        let eventStore = EKEventStore()
        
        eventStore.requestAccessToEntityType(EKEntityType.Event, completion: {
            (permissionGiven, error) in
            
            if(permissionGiven && (error == nil)) {
                let event = EKEvent(eventStore:eventStore)
                event.title = self.name
                
                // Set start date
                let startDateComponents = NSDateComponents()
                startDateComponents.day = self.day
                startDateComponents.month = self.month
                startDateComponents.year = self.year
                startDateComponents.hour = self.hour
                startDateComponents.minute = self.minute
                event.startDate = NSCalendar.currentCalendar().dateFromComponents(startDateComponents)!
                
                // Set end date (for now, ends 1 hour after start)
                let endDateComponents = NSDateComponents()
                endDateComponents.day = self.day
                endDateComponents.month = self.month
                endDateComponents.year = self.year
                endDateComponents.hour = self.hour + 1
                endDateComponents.minute = self.minute
                event.endDate = NSCalendar.currentCalendar().dateFromComponents(endDateComponents)!
                
                // Store event
                event.calendar = eventStore.defaultCalendarForNewEvents
                
                do {
                    try eventStore.saveEvent(event, span:EKSpan.ThisEvent)
                }
                catch {
                    // TODO: Error Notification
                }
                // TODO: Display pop-up saying event was successfully stored
            }
            else {
                // TODO: Display pop-up notification saying what went wrong
            }
            
        })
    }
}