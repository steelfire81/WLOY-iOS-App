//
//  WLOYSongParserDelegate.swift
//  WLOY App
//
//  Created by William Robbins on 3/31/16.
//  Copyright © 2016 William Robbins. All rights reserved.
//

import Foundation

class WLOYSongParserDelegate: NSObject, NSXMLParserDelegate {
    
    // CONSTANTS
    let DEFAULT_SONG = "SONG NOT FOUND"
    let DEFAULT_ARTIST = "ARTIST NOT FOUND"
    let XML_ITEM = "item"
    let XML_SONG_TITLE = "ra:track"
    let XML_SONG_ARTIST = "ra:artist"
    
    // DATA MEMBERS
    var currentSong: String
    var currentArtist: String
    var firstSongFound: Bool
    var parsingFirstSong: Bool
    var parsingTitle: Bool
    var parsingArtist: Bool
    var tempCurrentSong: String
    var tempCurrentArtist: String
    
    override init() {
        currentSong = DEFAULT_SONG
        currentArtist = DEFAULT_ARTIST
        tempCurrentSong = ""
        tempCurrentArtist = ""
        firstSongFound = false
        parsingFirstSong = false
        parsingTitle = false
        parsingArtist = false
    }
    
    // parserDidStartDocument - called when the parser begins reading the XML document
    func parserDidStartDocument(parser:NSXMLParser) {
        firstSongFound = false
        parsingFirstSong = false
        
        // Store temporary updated song and artist until parsing is complete
        tempCurrentSong = ""
        tempCurrentArtist = ""
    }
    
    // didStartElement - called when the parser begins parsing an XML element
    func parser(_ parser:NSXMLParser, didStartElement elementName:String, namespaceURI namespaceURI:String?, qualifiedName qName:String?, attributes attributeDict:[String : String]) {
            // Only parse this if the first song has not been found
            if(!firstSongFound) {
                if(elementName == XML_ITEM) {
                    parsingFirstSong = true
                }
                else if(parsingFirstSong) {
                    if(elementName == XML_SONG_TITLE) {
                        parsingTitle = true
                    }
                    else if(elementName == XML_SONG_ARTIST) {
                        parsingArtist = true
                    }
                }
            }
    }
    
    // didEndElement - called when the parser finishes parsing an XML element
    func parser(_ parser:NSXMLParser, didEndElement elementName:String, namespaceURI namespaceURI:String?, qualifiedName qName:String?) {
        if(parsingFirstSong) {
            if(elementName == XML_ITEM) {
                parsingFirstSong = false
                firstSongFound = true
                currentSong = tempCurrentSong
                currentArtist = tempCurrentArtist
            }
            else if(elementName == XML_SONG_TITLE) {
                parsingTitle = false
            }
            else if(elementName == XML_SONG_ARTIST) {
                parsingArtist = false
            }
        }
    }
    
    // foundCharacters - called when the parser retrieves new characters within an element
    func parser(_ parser:NSXMLParser, foundCharacters string:String) {
        if(parsingFirstSong) {
            if(parsingTitle) {
                tempCurrentSong = tempCurrentSong + string
            }
            else if(parsingArtist) {
                tempCurrentArtist = tempCurrentArtist + string
            }
        }
    }
    
}