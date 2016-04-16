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
    var pm:Bool
    
    // METHODS
    init(n:String, mo:Int, d:Int, y:Int, h:Int, mi:Int, p:Bool) {
        name = n
        month = mo
        day = d
        year = y
        hour = h
        minute = mi
        pm = p
    }
    
    // storeToCalendar - save this event to the system calendar and returns whether or not it was successful
    func storeToCalendar() -> Bool {
        let eventStore = EKEventStore()
        var success = true
        
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
                if(self.pm) {
                    startDateComponents.hour = startDateComponents.hour + 12
                }
                startDateComponents.minute = self.minute
                event.startDate = NSCalendar.currentCalendar().dateFromComponents(startDateComponents)!
                
                // Set end date (for now, ends 1 hour after start)
                let endDateComponents = NSDateComponents()
                endDateComponents.day = self.day
                endDateComponents.month = self.month
                endDateComponents.year = self.year
                endDateComponents.hour = self.hour + 1
                if(self.pm) {
                    endDateComponents.hour = endDateComponents.hour + 12
                }
                endDateComponents.minute = self.minute
                event.endDate = NSCalendar.currentCalendar().dateFromComponents(endDateComponents)!
                
                // Store event
                event.calendar = eventStore.defaultCalendarForNewEvents
                
                do {
                    try eventStore.saveEvent(event, span:EKSpan.ThisEvent)
                    success = true
                }
                catch {
                    success = false
                }
            }
            else {
                success = false
            }
            
        })
        
        return success
    }
}