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
    static let ADDRESS = "144.126.12.128"
    static let ERR_CONNECTION = "ERROR: Could not send following message to backend server"
    static let FEEDBACK_POSITIVE = "POSITIVE"
    static let FEEDBACK_NEGATIVE = "NEGATIVE"
    static let HEADER_CONNECTION = "CONNECTION"
    static let HEADER_FEEDBACK = "FEEDBACK"
    static let HEADER_REQUEST = "REQUEST"
    static let PORT = 4444
    
    // DATA MEMBERS
    static var id:Int = 0
    static var timeConnected:Int = 0
    
    // METHODS
    // sendConnectionMessage - send a message to the server giving time connected
    static func sendConnectionMessage() {
        let message = HEADER_CONNECTION + "\n" + String(id) + "\n" + String(timeConnected)
        sendMessage(message)
    }
    
    // sendFeedbackMessage - send a feedback message to the backend server with current playback information
    static func sendFeedbackMessage(positive:Bool, songTitle:String, artist:String, show:String, dj:String) {
        var feedback = ""
        if(positive) {
            feedback = FEEDBACK_POSITIVE
        }
        else {
            feedback = FEEDBACK_NEGATIVE
        }
        
        let message = HEADER_FEEDBACK + "\n" + String(id) + "\n" + String(timeConnected) + "\n" + feedback + "\n" + songTitle
            + "\n" + artist + "\n" + show + "\n" + dj
        sendMessage(message)
    }
    
    // sendRequestMessage - send a request to the backend server with a given song name and artist
    static func sendRequestMessage(songTitle:String, artist:String) {
        let message = HEADER_REQUEST + "\n" + String(id) + "\n" + String(timeConnected) + "\n" + songTitle + "\n" + artist
        sendMessage(message)
    }
    
    // sendMessage - send message through socket
    static func sendMessage(message:String) {
        var input:NSInputStream?
        var output:NSOutputStream?
        NSStream.getStreamsToHostWithName(ADDRESS, port:PORT, inputStream:&input, outputStream:&output)
        
        let outputStream:NSOutputStream = output!
        outputStream.open()
        let buffer:[UInt8] = Array(message.utf8)
        let result = outputStream.write(buffer, maxLength:buffer.count)
        // outputStream.close()
        
        if(result == -1) {
            NSLog(ERR_CONNECTION)
            NSLog(message)
        }
    }
    
    // generateID - generate a 'unique' identifier to be sent with playback stats
    static func generateID() {
        id = 0 // TODO: Actually generate an ID
    }
}