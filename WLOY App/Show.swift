//
//  Show.swift
//  WLOY App
//
//  Created by William Robbins on 4/2/16.
//  Copyright © 2016 William Robbins. All rights reserved.
//

import Foundation

class Show: NSObject {
    
    // DATA MEMBERS
    var name: String
    var startHour: Int
    var startMinute: Int
    
    // METHODS
    init(n:String, h:Int, m:Int) {
        name = n
        startHour = h
        startMinute = m
    }
    
    // calcTimeToStart - return the number of minutes until the show begins
    func calcTimeToStart() -> Int {
        let currentTime = NSCalendar.currentCalendar().components([NSCalendarUnit.Hour, NSCalendarUnit.Minute], fromDate:NSDate())
        let currentHour = currentTime.hour
        let currentMinute = currentTime.minute
        
    }
}