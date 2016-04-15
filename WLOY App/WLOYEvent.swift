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
        var eventStore = EKEventStore()
        
        eventStore.requestAccessToEntityType(EKEntityType.Event, completion: {
            (granted, error) in
            
            
        })
    }
}