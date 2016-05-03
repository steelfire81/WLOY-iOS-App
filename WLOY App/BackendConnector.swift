//
//  BackendConnector.swift
//  WLOY App
//
//  Created by William Robbins on 4/7/16.
//  Copyright © 2016 William Robbins. All rights reserved.
//

import CFNetwork
import Foundation

class BackendConnector: NSObject {
    
    // CONSTANTS
    static let ADDRESS = "lucy"
    static let ERR_CONNECTION = "ERROR: Could not send following message to backend server"
    static let FEEDBACK_POSITIVE = "POSITIVE"
    static let FEEDBACK_NEGATIVE = "NEGATIVE"
    static let HEADER_CONNECTION = "CONNECTION"
    static let HEADER_FEEDBACK = "FEEDBACK"
    static let HEADER_REQUEST = "REQUEST"
    static let HEADER_SCHEDULE_REQUEST = "SCHEDULE"
    static let MSG_NEW_STREAM = "Opened new output stream"
    static let PORT = 4444
    static let READ_BUFFSIZE = 50
    static let TIMER_INTERVAL = 60.0 // time in between server update sends (in seconds)
    
    // DATA MEMBERS
    static var id:Int = 0
    static var outputStream:NSOutputStream!
    static var timeConnected:Int = 0
    static var connectorDelegate:BackendConnectorDelegate!
    static var timer:NSTimer!
    
    // METHODS
    // generateScheduleRequest - create a message requesting the backend server's schedule
    static func generateScheduleRequest() -> String {
        return HEADER_SCHEDULE_REQUEST + "\n" + String(id) + "\n" + String(timeConnected) + "\n"
    }
    
    // sendConnectionMessage - send a message to the server giving time connected
    static func sendConnectionMessage() -> Bool {
        let message = HEADER_CONNECTION + "\n" + String(id) + "\n" + String(timeConnected) + "\n"
        return sendMessage(message)
    }
    
    // sendFeedbackMessage - send a feedback message to the backend server with current playback information
    static func sendFeedbackMessage(positive:Bool, songTitle:String, artist:String, show:String, dj:String) -> Bool {
        var feedback = ""
        if(positive) {
            feedback = FEEDBACK_POSITIVE
        }
        else {
            feedback = FEEDBACK_NEGATIVE
        }
        
        let message = HEADER_FEEDBACK + "\n" + String(id) + "\n" + String(timeConnected) + "\n" + feedback + "\n" + songTitle
            + "\n" + artist + "\n" + show + "\n" + dj + "\n"
        return sendMessage(message)
    }
    
    // sendRequestMessage - send a request to the backend server with a given song name and artist
    static func sendRequestMessage(songTitle:String, artist:String) -> Bool {
        let message = HEADER_REQUEST + "\n" + String(id) + "\n" + String(timeConnected) + "\n" + songTitle + "\n" + artist + "\n"
        return sendMessage(message)
    }
    
    // sendMessage - send message through socket
    static func sendMessage(message:String) -> Bool {
        // Ensure an output stream exists
        if(outputStream == nil) {
            NSLog(MSG_NEW_STREAM) // DEBUG
            var input:NSInputStream?
            var output:NSOutputStream?
            NSStream.getStreamsToHostWithName(ADDRESS, port:PORT, inputStream:&input, outputStream:&output)
            outputStream = output!
            
            // Ensure a BackendConnectorDelegate exists
            if(connectorDelegate == nil) {
                connectorDelegate = BackendConnectorDelegate()
            }
            
            // Verify that streams have opened
            outputStream.delegate = connectorDelegate
            outputStream.scheduleInRunLoop(.mainRunLoop(), forMode:NSDefaultRunLoopMode)
            outputStream.open()
        }
        
        // Send the message
        let buffer:[UInt8] = Array(message.utf8)
        let result = outputStream.write(buffer, maxLength:buffer.count)
        outputStream.close()
        outputStream = nil
        
        // Check if message send failed
        if(result == -1) {
            // Remove outputStream
            NSLog(ERR_CONNECTION)
            NSLog(message)
            outputStream = nil
            return false
        }
        
        return true
    }
    
    // generateID - generate a 'unique' identifier to be sent with playback stats
    static func generateID() {
        id = 0 // TODO: Actually generate an ID
    }
    
    // startTimer - begin keeping a timer for time connected that sends messages to server
    static func startTimer() {
        // Only start timer if timer doesn't exist already
        if(timer == nil) {
            timer = NSTimer.scheduledTimerWithTimeInterval(TIMER_INTERVAL, target:self, selector:"timerFired", userInfo:nil, repeats:true)
        }
    }
    
    // timerFired - indicate that the timer has reached an interval
    static func timerFired() {
        timeConnected++
        sendConnectionMessage()
    }
}